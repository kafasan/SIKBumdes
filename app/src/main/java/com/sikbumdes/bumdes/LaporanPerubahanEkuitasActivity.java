package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class LaporanPerubahanEkuitasActivity extends AppCompatActivity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_perubahan_ekuitas);

        back = findViewById(R.id.iv_back);
        back.setOnClickListener(view -> onBackPressed());
    }
}
