package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView company_name, company_email;
    CardView cv_data_akun, cv_neraca_awal, cv_rencana_anggaran,
            cv_jurnal, cv_buku_besar, cv_perubahan_ekuitas, cv_realisasi_anggaran, cv_neraca, cv_laba_rugi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//        iv_settings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
        iv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(MainActivity.this).clear();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
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
}
