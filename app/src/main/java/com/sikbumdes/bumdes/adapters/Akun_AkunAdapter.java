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

import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.DataAkunActivity;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunUpdateResponse;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.AkunClassResponse;
import com.sikbumdes.bumdes.model.AkunClassUpdateResponse;
import com.sikbumdes.bumdes.model.AkunDeleteResponse;
import com.sikbumdes.bumdes.model.AkunParent;
import com.sikbumdes.bumdes.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Akun_AkunAdapter extends RecyclerView.Adapter<Akun_AkunAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<AkunAkun> akunAkuns;
    private List<Integer> colors;

    public Akun_AkunAdapter(Context context, ArrayList<AkunAkun> akunAkuns) {
        this.context = context;
        this.akunAkuns = akunAkuns;
    }

    @Override
    public Akun_AkunAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_akun_akun, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Akun_AkunAdapter.CustomViewHolder holder, int position) {
        holder.tv_akun.setText(akunAkuns.get(position).getAccount_name() + " (" + akunAkuns.get(position).getAccount_code() + ")");
        holder.name = akunAkuns.get(position).getAccount_name();
        holder.code = akunAkuns.get(position).getAccount_code();
        colors = new ArrayList<Integer>();

        colors.add(context.getResources().getColor(R.color.colorPastel_1));
        colors.add(context.getResources().getColor(R.color.colorPastel_2));
        colors.add(context.getResources().getColor(R.color.colorPastel_3));
        colors.add(context.getResources().getColor(R.color.colorPastel_4));
        colors.add(context.getResources().getColor(R.color.colorPastel_5));
        colors.add(context.getResources().getColor(R.color.colorPastel_6));
        colors.add(context.getResources().getColor(R.color.colorPastel_7));
        colors.add(context.getResources().getColor(R.color.colorPastel_8));
        colors.add(context.getResources().getColor(R.color.colorPastel_9));

        Random r = new Random();
        int i = r.nextInt(colors.size());

        holder.iv_circle.setColorFilter(colors.get(i));
    }

    @Override
    public int getItemCount() {
        return akunAkuns.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int id;
        private String name, code;
        private TextView tv_akun, id_klasifikasiNew, id_position;;
        private ImageView iv_edit, iv_circle;
        private EditText nama_akun, kode_akun;
        private LinearLayout ll_akun;
        Context context;
        private ArrayList<AkunClass> akunClassArrayList;
        private ArrayAdapter<AkunClass> classArrayAdapter;
        private String[] posisi = {"Debit", "Kredit"};
        Dialog akunDialog;

        public CustomViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            ll_akun = itemView.findViewById(R.id.ll_akun);
            tv_akun = itemView.findViewById(R.id.tv_akun);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_circle = itemView.findViewById(R.id.iv_circle);
            iv_edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ll_akun:
                    //show details
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
                                    deleteAccount();
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

        public void showDialogEdit(){
            akunDialog = new Dialog(context);
            akunDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            akunDialog.setCancelable(false);
            akunDialog.setContentView(R.layout.dialog_add_akun);
            akunDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Spinner classSpinner = akunDialog.findViewById(R.id.classAcc_spinner);
            kode_akun = akunDialog.findViewById(R.id.kode_akun);
            nama_akun = akunDialog.findViewById(R.id.nama_akun);
            Spinner posSpinner = akunDialog.findViewById(R.id.positionSpinner);

            User user = SharedPrefManager.getInstance(context).getUser();
            String token = "Bearer " + user.getToken();

            Call<AkunClassResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getAkunClassAll(token);

            call.enqueue(new Callback<AkunClassResponse>() {
                @Override
                public void onResponse(Call<AkunClassResponse> call, Response<AkunClassResponse> response) {
                    AkunClassResponse akunClassResponse = response.body();
                    if (response.isSuccessful()) {
                        if (akunClassResponse.isSuccess()) {
                            akunClassArrayList = akunClassResponse.getAkunClasses();
                            classArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, akunClassArrayList);
                            classArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            classSpinner.setAdapter(classArrayAdapter);
                        }
                    } else {
                        Toast.makeText(context, "Gagal mengambil data klasifikasi akun", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<AkunClassResponse> call, Throwable t) {
                    Toast.makeText(context, "Kesalahan terjadi, coba beberapa saat lagi.", Toast.LENGTH_SHORT).show();
                }
            });

            classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    AkunClass akunClass = (AkunClass) adapterView.getSelectedItem();
                    id_klasifikasiNew = akunDialog.findViewById(R.id.id_klasifikasi);
                    id_klasifikasiNew.setText(String.valueOf(akunClass.getId()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, posisi);
            posSpinner.setAdapter(adapter);
            posSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    id_position = akunDialog.findViewById(R.id.id_position);
                    id_position.setText(String.valueOf(adapter.getItemId(i)));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            nama_akun.setText(name);
            kode_akun.setText(code);

            MaterialButton cancel = akunDialog.findViewById(R.id.btn_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    akunDialog.dismiss();
                }
            });

            MaterialButton save = akunDialog.findViewById(R.id.btn_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateAkun();
                }
            });
            akunDialog.show();
        }

        public void updateAkun(){
            User user = SharedPrefManager.getInstance(context).getUser();
            String token = "Bearer " + user.getToken();

            int id_classification = Integer.parseInt(id_klasifikasiNew.getText().toString());
            name = nama_akun.getText().toString().trim();
            code = kode_akun.getText().toString().trim();
            String position = id_position.getText().toString();

            Call<AkunAkunUpdateResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .updateAkun(token, id, id_classification, name, code, position);

            call.enqueue(new Callback<AkunAkunUpdateResponse>() {
                @Override
                public void onResponse(Call<AkunAkunUpdateResponse> call, Response<AkunAkunUpdateResponse> response) {
                    AkunAkunUpdateResponse akunAkunUpdateResponse = response.body();
                    if (response.isSuccessful()){
                        if (akunAkunUpdateResponse.isSuccess()){
                            tv_akun.setText(akunAkunUpdateResponse.getAkunAkun().getAccount_name() +
                                    " (" + akunAkunUpdateResponse.getAkunAkun().getAccount_code() + ")");
                            akunDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AkunAkunUpdateResponse> call, Throwable t) {
                    Toast.makeText(context, "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void deleteAccount(){
            User user = SharedPrefManager.getInstance(context).getUser();
            String token = "Bearer " + user.getToken();

            Call<AkunDeleteResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .deleteAccount(token, id);

            call.enqueue(new Callback<AkunDeleteResponse>() {
                @Override
                public void onResponse(Call<AkunDeleteResponse> call, Response<AkunDeleteResponse> response) {
                    AkunDeleteResponse akunDeleteResponse = response.body();
                    if (response.isSuccessful()){
                        if (akunDeleteResponse.isSuccess()){
                            Log.i("DELETE ACCOUNT", "Account deleted");
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