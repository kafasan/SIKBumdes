package com.sikbumdes.bumdes.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunResponse;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.AkunClassUpdateResponse;
import com.sikbumdes.bumdes.model.AkunDeleteResponse;
import com.sikbumdes.bumdes.model.AkunParent;
import com.sikbumdes.bumdes.model.AkunParentResponse;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Akun_KlasifikasiAdapter extends RecyclerView.Adapter<Akun_KlasifikasiAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<AkunClass> akunClasses;

    public Akun_KlasifikasiAdapter(Context context, ArrayList<AkunClass> akunClasses) {
        this.context = context;
        this.akunClasses = akunClasses;
    }

    @Override
    public Akun_KlasifikasiAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_akun_class, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Akun_KlasifikasiAdapter.CustomViewHolder holder, int position) {
        AkunClass akunClass = akunClasses.get(position);
        holder.tv_class.setText(akunClass.getClassification_name() + " (" + akunClass.getClassification_code() + ")");
        holder.id = akunClasses.get(position).getId();
        holder.name = akunClass.getClassification_name();
        holder.code = akunClass.getClassification_code();
    }

    @Override
    public int getItemCount() {
        return akunClasses.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int id;
        private String name, code;
        private TextView tv_class, id_parentAkunNew;
        private ImageView iv_edit;
        private EditText nama_klasifikasi, kode_klasifikasi;
        private LinearLayout ll_akun, ll_akun_class;
        private RecyclerView rv_akun_akun;
        private ArrayList<AkunAkun> akunAkunArrayList;
        private ArrayList<AkunParent> akunParentArrayList;
        private Akun_AkunAdapter akun_akunAdapter;
        private ArrayAdapter<AkunParent> arrayAdapter;
        RecyclerView.LayoutManager eLayoutManager;
        Dialog classDialog;
        User user = SharedPrefManager.getInstance(context).getUser();
        String token = "Bearer " + user.getToken();

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_class = itemView.findViewById(R.id.tv_class);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            ll_akun = itemView.findViewById(R.id.ll_akun);
            ll_akun_class = itemView.findViewById(R.id.ll_akun_class);
            rv_akun_akun = itemView.findViewById(R.id.rv_akun_akun);
            ll_akun_class.setOnClickListener(this);
            iv_edit.setOnClickListener(this);

//            if (eLayoutManager.getItemCount() == 0) {
//                ll_akun_class.setOnClickListener(null);
//            } else {
//                ll_akun_class.setOnClickListener(this);
//            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ll_akun_class:
                    int id = getLayoutPosition();

                    AkunClass akunClass = akunClasses.get(id);

                    if (akunClass.isChildrenVisible()) {
                        akunClass.setChildrenVisible(false);
                        ll_akun.setVisibility(View.GONE);
                    } else {
                        akunClass.setChildrenVisible(true);
                        ll_akun.setVisibility(View.VISIBLE);
                        getAkunAkun();
                    }
                    break;
                case R.id.iv_edit:
                    PopupMenu popupMenu = new PopupMenu(context, iv_edit);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.menu_edit:
                                    showDialogEdit();
                                    return true;
                                case  R.id.menu_delete:
                                    deleteClassification();
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.inflate(R.menu.akun_edit_menu);
                    popupMenu.show();
                    break;
                default:
                    break;
            }

        }

        public void getAkunAkun() {
            Call<AkunAkunResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getAkunAkun(token, id);

            call.enqueue(new Callback<AkunAkunResponse>() {
                @Override
                public void onResponse(Call<AkunAkunResponse> call, Response<AkunAkunResponse> response) {
                    AkunAkunResponse akunAkunResponse = response.body();
                    if (response.isSuccessful()) {
                        if (akunAkunResponse.isSuccess()) {
                            akunAkunArrayList = akunAkunResponse.getAkunClasses();
                            akun_akunAdapter = new Akun_AkunAdapter(context, akunAkunArrayList);
                            RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(itemView.getContext());
                            rv_akun_akun.setLayoutManager(eLayoutManager);
                            rv_akun_akun.setItemAnimator(new DefaultItemAnimator());
                            rv_akun_akun.setAdapter(akun_akunAdapter);
                            akun_akunAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AkunAkunResponse> call, Throwable t) {
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void showDialogEdit(){
            classDialog = new Dialog(context);
            classDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            classDialog.setCancelable(false);
            classDialog.setContentView(R.layout.dialog_add_klasifikasi);
            classDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Spinner parentSpinner = classDialog.findViewById(R.id.parentAcc_spinner);
            kode_klasifikasi = classDialog.findViewById(R.id.kode_klasifikasi);
            nama_klasifikasi = classDialog.findViewById(R.id.nama_klasifikasi);

            Call<AkunParentResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getAkunParent(token);

            call.enqueue(new Callback<AkunParentResponse>() {
                @Override
                public void onResponse(Call<AkunParentResponse> call, Response<AkunParentResponse> response) {
                    AkunParentResponse akunParentResponse = response.body();
                    if (response.isSuccessful()) {
                        if (akunParentResponse.isSuccess()) {
                            akunParentArrayList = akunParentResponse.getAkunParents();
                            arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, akunParentArrayList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            parentSpinner.setAdapter(arrayAdapter);
                        }
                    } else {
                        Toast.makeText(context, "Gagal mengambil data parent akun", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<AkunParentResponse> call, Throwable t) {
                    Toast.makeText(context, "Kesalahan terjadi, coba beberapa saat lagi.", Toast.LENGTH_SHORT).show();
                }
            });

            parentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    AkunParent akunParent = (AkunParent) adapterView.getSelectedItem();
                    id_parentAkunNew = classDialog.findViewById(R.id.id_parent);
                    id_parentAkunNew.setText(String.valueOf(akunParent.getId()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            nama_klasifikasi.setText(name);
            kode_klasifikasi.setText(code);

            MaterialButton cancel = classDialog.findViewById(R.id.btn_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    classDialog.dismiss();
                }
            });

            MaterialButton save = classDialog.findViewById(R.id.btn_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateClassification();
                }
            });
            classDialog.show();
        }

        public void updateClassification(){
            int id_parent = Integer.parseInt(id_parentAkunNew.getText().toString());
            name = nama_klasifikasi.getText().toString().trim();
            code = kode_klasifikasi.getText().toString().trim();

            Call<AkunClassUpdateResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .updateClass(token, id, id_parent, name, code);

            call.enqueue(new Callback<AkunClassUpdateResponse>() {
                @Override
                public void onResponse(Call<AkunClassUpdateResponse> call, Response<AkunClassUpdateResponse> response) {
                    AkunClassUpdateResponse akunClassUpdateResponse = response.body();
                    if (response.isSuccessful()){
                        if (akunClassUpdateResponse.isSuccess()){
                            tv_class.setText(akunClassUpdateResponse.getAkunClass().getClassification_name() +
                                    " (" + akunClassUpdateResponse.getAkunClass().getClassification_code() + ")");
                            classDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AkunClassUpdateResponse> call, Throwable t) {
                    Toast.makeText(context, "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void deleteClassification(){
            Call<AkunDeleteResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .deleteClass(token, id);

            call.enqueue(new Callback<AkunDeleteResponse>() {
                @Override
                public void onResponse(Call<AkunDeleteResponse> call, Response<AkunDeleteResponse> response) {
                    AkunDeleteResponse akunDeleteResponse = response.body();
                    if (response.isSuccessful()){
                        if (akunDeleteResponse.isSuccess()){
                            Log.i("DELETE CLASSIFICATION", "Classification deleted");
                            Toast.makeText(context, akunDeleteResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AkunDeleteResponse> call, Throwable t) {
                    Toast.makeText(context, "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
