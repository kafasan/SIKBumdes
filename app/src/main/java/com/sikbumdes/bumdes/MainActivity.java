package com.sikbumdes.bumdes;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.sikbumdes.bumdes.adapters.BusinessAdapter;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.Business;
import com.sikbumdes.bumdes.model.BusinessResponse;
import com.sikbumdes.bumdes.model.User;
import com.sikbumdes.bumdes.model.UserDetailResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView company_name, company_email, tv_business_name;
    CardView cv_data_akun, cv_neraca_awal, cv_rencana_anggaran,
            cv_jurnal, cv_buku_besar, cv_perubahan_ekuitas, cv_realisasi_anggaran, cv_neraca, cv_laba_rugi;
    Context context;
    SwipeRefreshLayout refreshLayout;
    Dialog dialog_choose_business;
    LinearLayout ll_business, ll_data;
    ShimmerFrameLayout sf_loading;
    private ArrayList<Business> businessArrayList;
    private RecyclerView rv_business;
    private BusinessAdapter businessAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkConnection();
            }
        });

        company_name = findViewById(R.id.tv_name);
        company_email = findViewById(R.id.tv_mail);
        tv_business_name = findViewById(R.id.tv_business_name);
        cv_data_akun = findViewById(R.id.cv_data_akun);
        cv_neraca_awal = findViewById(R.id.cv_neraca_awal);
        cv_rencana_anggaran = findViewById(R.id.cv_rencana_anggaran);
        cv_jurnal = findViewById(R.id.cv_jurnal);
        cv_buku_besar = findViewById(R.id.cv_buku_besar);
        cv_perubahan_ekuitas = findViewById(R.id.cv_perubahan_ekuitas);
        cv_realisasi_anggaran = findViewById(R.id.cv_realisasi_anggaran);
        cv_neraca = findViewById(R.id.cv_neraca);
        cv_laba_rugi = findViewById(R.id.cv_laba_rugi);
        ll_business = findViewById(R.id.ll_business);
        ll_data = findViewById(R.id.ll_data);
        sf_loading = findViewById(R.id.sf_loading);

        cv_data_akun.setOnClickListener(this);
        cv_neraca_awal.setOnClickListener(this);
        cv_rencana_anggaran.setOnClickListener(this);
        cv_jurnal.setOnClickListener(this);
        cv_buku_besar.setOnClickListener(this);
        cv_perubahan_ekuitas.setOnClickListener(this);
        cv_realisasi_anggaran.setOnClickListener(this);
        cv_neraca.setOnClickListener(this);
        cv_laba_rugi.setOnClickListener(this);
        ll_business.setOnClickListener(this);

        ImageView iv_settings = findViewById(R.id.iv_settings);
        iv_settings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_data_akun:
                startActivity(new Intent(MainActivity.this, DataAkunActivity.class));
                break;
            case R.id.cv_neraca_awal:
                startActivity(new Intent(MainActivity.this, NeracaAwalActivity.class));
                break;
            case R.id.cv_rencana_anggaran:
                startActivity(new Intent(MainActivity.this, RencanaAnggaranActivity.class));
                break;
            case R.id.cv_jurnal:
                startActivity(new Intent(MainActivity.this, JurnalActivity.class));
                break;
            case R.id.cv_buku_besar:
                startActivity(new Intent(MainActivity.this, BukuBesarActivity.class));
                break;
            case R.id.cv_perubahan_ekuitas:
                startActivity(new Intent(MainActivity.this, LaporanPerubahanEkuitasActivity.class));
                break;
            case R.id.cv_realisasi_anggaran:
                startActivity(new Intent(MainActivity.this, LaporanRealisasiAnggaranActivity.class));
                break;
            case R.id.cv_neraca:
                startActivity(new Intent(MainActivity.this, NeracaActivity.class));
                break;
            case R.id.cv_laba_rugi:
                startActivity(new Intent(MainActivity.this, LaporanLabaRugiActivity.class));
                break;
            case R.id.ll_business:
                showDialogBusiness();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
        if (SharedPrefManager.getInstance(context).isSaveBusiness()) {
            getBusinessName();
        } else {
            showDialogBusiness();
        }
    }

    // 0 = no internet, 1 = cellular data, 2 = wifi
    @IntRange(from = 0, to = 2)
    public static int getConnectionType(Context context) {
        int result = 0; // Returns connection type. 0: none; 1: mobile data; 2: wifi
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1;
                    }
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = 2;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = 1;
                    }
                }
            }
        }
        return result;
    }

    private void checkConnection() {
        int type = getConnectionType(getApplicationContext());
        final Dialog dialog = new Dialog(context);
        if (type == 0) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_no_internet);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            dialog.getWindow().setLayout(width, height);

            Button btnRetry = dialog.findViewById(R.id.btn_retry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    checkConnection();
                }
            });
            dialog.show();
        } else {
            getUserDetail();
        }
    }

    public void getUserDetail() {
        sf_loading.setVisibility(View.VISIBLE);
        sf_loading.startShimmer();
        ll_data.setVisibility(View.GONE);
        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        Call<UserDetailResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserDetail(token);

        call.enqueue(new Callback<UserDetailResponse>() {
            @Override
            public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response) {
                UserDetailResponse userDetailResponse = response.body();
                if (response.isSuccessful()) {
                    if (userDetailResponse.isSuccess()) {
                        company_name.setText(userDetailResponse.getUserDetail().getName());
                        company_email.setText(userDetailResponse.getUser().getEmail());
                        sf_loading.setVisibility(View.GONE);
                        ll_data.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetailResponse> call, Throwable t) {
                sf_loading.setVisibility(View.GONE);
                Toast.makeText(context, "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
            }
        });
        sf_loading.stopShimmer();
        refreshLayout.setRefreshing(false);
    }

    public void showDialogBusiness() {
        dialog_choose_business = new Dialog(context);
        dialog_choose_business.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (SharedPrefManager.getInstance(context).isSaveBusiness()) {
            dialog_choose_business.setCancelable(true);
            dialog_choose_business.setCanceledOnTouchOutside(true);
        } else {
            dialog_choose_business.setCancelable(false);
            dialog_choose_business.setCanceledOnTouchOutside(false);
        }
        dialog_choose_business.setContentView(R.layout.dialog_choose_business);
        dialog_choose_business.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog_choose_business.getWindow().setLayout(width, height);

        rv_business = dialog_choose_business.findViewById(R.id.rv_business);

        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        Call<BusinessResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getBusiness(token);

        call.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                BusinessResponse businessResponse = response.body();
                if (response.isSuccessful()) {
                    if (businessResponse.isSuccess()) {
                        businessArrayList = businessResponse.getBusinesses();
                        businessAdapter = new BusinessAdapter(context, businessArrayList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_business.setLayoutManager(eLayoutManager);
                        rv_business.setAdapter(businessAdapter);
                        businessAdapter.notifyDataSetChanged();
                        dialog_choose_business.show();
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
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void closeDialogBusiness() {
        dialog_choose_business.dismiss();
    }

    public void getBusinessName() {
        Business business = SharedPrefManager.getInstance(this).getBusiness();
        String bussinessName = business.getBusiness_name();
        tv_business_name.setText(bussinessName);
    }
}
