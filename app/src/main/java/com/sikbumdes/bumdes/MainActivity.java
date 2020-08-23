package com.sikbumdes.bumdes;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView company_name, company_email;
    CardView cv_data_akun, cv_neraca_awal, cv_rencana_anggaran,
            cv_jurnal, cv_buku_besar, cv_perubahan_ekuitas, cv_realisasi_anggaran, cv_neraca, cv_laba_rugi;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnabled(false);

        company_name = findViewById(R.id.tv_name);
        company_email = findViewById(R.id.tv_mail);
        cv_data_akun = findViewById(R.id.cv_data_akun);
        cv_neraca_awal = findViewById(R.id.cv_neraca_awal);
        cv_rencana_anggaran = findViewById(R.id.cv_rencana_anggaran);
        cv_jurnal = findViewById(R.id.cv_jurnal);
        cv_buku_besar = findViewById(R.id.cv_buku_besar);
        cv_perubahan_ekuitas = findViewById(R.id.cv_perubahan_ekuitas);
        cv_realisasi_anggaran = findViewById(R.id.cv_realisasi_anggaran);
        cv_neraca = findViewById(R.id.cv_neraca);
        cv_laba_rugi = findViewById(R.id.cv_laba_rugi);

        User user = SharedPrefManager.getInstance(this).getUser();
        company_name.setText(user.getCompany());
        company_email.setText(user.getEmail());

        cv_data_akun.setOnClickListener(this);
        cv_neraca_awal.setOnClickListener(this);
        cv_rencana_anggaran.setOnClickListener(this);
        cv_jurnal.setOnClickListener(this);
        cv_buku_besar.setOnClickListener(this);
        cv_perubahan_ekuitas.setOnClickListener(this);
        cv_realisasi_anggaran.setOnClickListener(this);
        cv_neraca.setOnClickListener(this);
        cv_laba_rugi.setOnClickListener(this);

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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
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
            //checkVersion();
        }
    }
}
