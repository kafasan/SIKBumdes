package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sikbumdes.bumdes.adapters.Akun_AkunAdapter;
import com.sikbumdes.bumdes.adapters.Akun_KlasifikasiAdapter;
import com.sikbumdes.bumdes.adapters.Akun_ParentAdapter;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunClass;
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
    ProgressDialog loading;
    Context context;

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
}
