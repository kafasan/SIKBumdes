package com.sikbumdes.bumdes.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.DataAkunActivity;
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
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Akun_KlasifikasiAdapter.CustomViewHolder holder, int position) {
        AkunClass akunClass = akunClasses.get(position);
        holder.tv_class.setText(String.format("%s (%s)", akunClass.getClassification_name(), akunClass.getClassification_code()));
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
        private TextView tv_class, tv_warn, id_parentAkunNew;
        private EditText et_nama_klasifikasi, et_kode_klasifikasi;
        private LinearLayout ll_akun, ll_akun_class, ll_edit, ll_button;
        private LottieAnimationView av_loading_dialog;
        private RecyclerView rv_akun_akun;
        private ArrayList<AkunAkun> akunAkunArrayList;
        private ArrayList<AkunParent> akunParentArrayList;
        private Akun_AkunAdapter akun_akunAdapter;
        private ArrayAdapter<AkunParent> arrayAdapter;
        RecyclerView.LayoutManager eLayoutManager;
        Dialog classDialog;
        User user = SharedPrefManager.getInstance(itemView.getContext()).getUser();
        String token = "Bearer " + user.getToken();

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_class = itemView.findViewById(R.id.tv_class);
            ll_edit = itemView.findViewById(R.id.ll_edit);
            ll_akun = itemView.findViewById(R.id.ll_akun);
            ll_akun_class = itemView.findViewById(R.id.ll_akun_class);
            rv_akun_akun = itemView.findViewById(R.id.rv_akun_akun);
            ll_akun_class.setOnClickListener(this);
            ll_edit.setOnClickListener(this);

            /*
            if (eLayoutManager.getItemCount() == 0) {
                ll_akun_class.setOnClickListener(null);
            } else {
                ll_akun_class.setOnClickListener(this);
            }
             */
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
                case R.id.ll_edit:
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), ll_edit);
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

        void getAkunAkun() {
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
                            akunAkunArrayList = akunAkunResponse.getAkunAkuns();
                            akun_akunAdapter = new Akun_AkunAdapter(itemView.getContext(), akunAkunArrayList);
                            RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(itemView.getContext());
                            rv_akun_akun.setLayoutManager(eLayoutManager);
                            rv_akun_akun.setItemAnimator(new DefaultItemAnimator());
                            rv_akun_akun.setAdapter(akun_akunAdapter);
                            akun_akunAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(itemView.getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(itemView.getContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(itemView.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AkunAkunResponse> call, Throwable t) {
                    Toast.makeText(itemView.getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void showDialogEdit(){
            classDialog = new Dialog(itemView.getContext());
            classDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            classDialog.setCancelable(false);
            classDialog.setCanceledOnTouchOutside(false);
            classDialog.setContentView(R.layout.dialog_add_klasifikasi);
            classDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            DisplayMetrics metrics = itemView.getContext().getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            classDialog.getWindow().setLayout(width, height);

            Spinner parentSpinner = classDialog.findViewById(R.id.sp_parent);
            et_kode_klasifikasi = classDialog.findViewById(R.id.et_kode_klasifikasi);
            et_nama_klasifikasi = classDialog.findViewById(R.id.et_nama_klasifikasi);
            tv_warn = classDialog.findViewById(R.id.tv_warn);
            ll_button = classDialog.findViewById(R.id.ll_button);
            av_loading_dialog = classDialog.findViewById(R.id.av_loading_dialog);

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
                            arrayAdapter = new ArrayAdapter<>(itemView.getContext(), R.layout.layout_spinner, akunParentArrayList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            parentSpinner.setAdapter(arrayAdapter);
                        }
                    } else {
                        Toast.makeText(itemView.getContext(), "Gagal mengambil data parent akun", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<AkunParentResponse> call, Throwable t) {
                    Toast.makeText(itemView.getContext(), "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
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

            et_nama_klasifikasi.setText(name);
            et_kode_klasifikasi.setText(code);

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

        void updateClassification(){
            tv_warn.setVisibility(View.GONE);
            ll_button.setVisibility(View.GONE);
            av_loading_dialog.setVisibility(View.VISIBLE);
            av_loading_dialog.playAnimation();
            int id_parent = Integer.parseInt(id_parentAkunNew.getText().toString());
            name = et_nama_klasifikasi.getText().toString().trim();
            code = et_kode_klasifikasi.getText().toString().trim();

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
                            /*
                            tv_class.setText(String.format("%s (%s)", akunClassUpdateResponse.getAkunClass().getClassification_name(), akunClassUpdateResponse.getAkunClass().getClassification_code()));
                            */
                            Toast.makeText(itemView.getContext(), "Klasifikasi berhasil diubah", Toast.LENGTH_LONG).show();
                            av_loading_dialog.setVisibility(View.GONE);
                            av_loading_dialog.cancelAnimation();
                            classDialog.dismiss();
                            if (itemView.getContext() instanceof DataAkunActivity) {
                                ((DataAkunActivity)itemView.getContext()).getAkunParent();
                            }
                        }
                    } else {
                        av_loading_dialog.setVisibility(View.GONE);
                        av_loading_dialog.cancelAnimation();
                        tv_warn.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.VISIBLE);
                        Toast.makeText(itemView.getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AkunClassUpdateResponse> call, Throwable t) {
                    av_loading_dialog.setVisibility(View.GONE);
                    av_loading_dialog.cancelAnimation();
                    tv_warn.setVisibility(View.VISIBLE);
                    ll_button.setVisibility(View.VISIBLE);
                    Toast.makeText(itemView.getContext(), "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void deleteClassification(){
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
                            Toast.makeText(itemView.getContext(), "Klasifikasi berhasil dihapus", Toast.LENGTH_LONG).show();
                            if (itemView.getContext() instanceof DataAkunActivity) {
                                ((DataAkunActivity)itemView.getContext()).getAkunParent();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<AkunDeleteResponse> call, Throwable t) {
                    Toast.makeText(itemView.getContext(), "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
