package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.adapters.Akun_AkunAdapter;
import com.sikbumdes.bumdes.adapters.Akun_KlasifikasiAdapter;
import com.sikbumdes.bumdes.adapters.Akun_ParentAdapter;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.AkunClassResponse;
import com.sikbumdes.bumdes.model.AkunParent;
import com.sikbumdes.bumdes.model.AkunParentResponse;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataAkunActivity extends AppCompatActivity {

    private ArrayList<AkunParent> akunParentArrayList;
    private RecyclerView rv_akun_parent;
    private Akun_ParentAdapter akun_parentAdapter;
    private SwipeRefreshLayout refreshLayout;
    private Dialog classDialog, akunDialog;
    private ArrayAdapter<AkunParent> arrayAdapter;
    ProgressDialog loading;
    Context context;

    TextView id_parentAkun;
    EditText nama_klasifikasi, kode_klasifikasi;

    TextView id_klasifikasi;
    EditText nama_akun, kode_akun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_akun);

        context = this;

        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView iv_add = findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DataAkunActivity.this, iv_add);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.add_classification:
                                addClassification();
                                return true;
                            case  R.id.add_akun:
                                //dialog akun
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.data_akun_menu);
                popupMenu.show();
            }
        });

        rv_akun_parent = findViewById(R.id.rv_akun_parent);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (akun_parentAdapter != null) {
                    akun_parentAdapter.refreshEvents(akunParentArrayList);
                }
                getAkunParent();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAkunParent();
    }

    public void getAkunParent() {
        loading = ProgressDialog.show(context, null, "Mohon tunggu sebentar...", true, false);

        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

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
                        akun_parentAdapter = new Akun_ParentAdapter(context, akunParentArrayList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_akun_parent.setLayoutManager(eLayoutManager);
                        rv_akun_parent.setItemAnimator(new DefaultItemAnimator());
                        rv_akun_parent.setAdapter(akun_parentAdapter);
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
                refreshLayout.setRefreshing(false);
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<AkunParentResponse> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                loading.dismiss();
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                Log.i("WelcomeToFacebook", t.toString());
            }
        });
    }

    public void addClassification(){
        classDialog = new Dialog(DataAkunActivity.this);
        classDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        classDialog.setCancelable(false);
        classDialog.setContentView(R.layout.dialog_add_klasifikasi);
        classDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Spinner parentSpinner = classDialog.findViewById(R.id.parentAcc_spinner);
        kode_klasifikasi = classDialog.findViewById(R.id.kode_klasifikasi);
        nama_klasifikasi = classDialog.findViewById(R.id.nama_klasifikasi);

        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

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
                id_parentAkun = classDialog.findViewById(R.id.id_parent);
                id_parentAkun.setText(String.valueOf(akunParent.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                createClassification();
            }
        });
        classDialog.show();
    }

    public void createClassification(){
        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        String id_parent = id_parentAkun.getText().toString();
        String name = nama_klasifikasi.getText().toString();
        String code = kode_klasifikasi.getText().toString();

        Call<AkunClassResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .storeClass(token, id_parent, name, code);

        call.enqueue(new Callback<AkunClassResponse>() {
            @Override
            public void onResponse(Call<AkunClassResponse> call, Response<AkunClassResponse> response) {
                AkunClassResponse classResponse = response.body();
                if (response.isSuccessful()){
                    if (classResponse.isSuccess()){
                        Log.i("CLASSIFICATION", "store classification is SUCCESSFUL");
                        Toast.makeText(context, "Klasifikasi Akun berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        classDialog.dismiss();
                    }
                } else {
                    Log.i("CLASSIFICATION", classResponse.toString());
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AkunClassResponse> call, Throwable t) {
                Log.d("CLASSIFICATION CHECK", t.toString());
                Toast.makeText(context, "Kesalahan terjadi, coba beberapa saat lagi.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
