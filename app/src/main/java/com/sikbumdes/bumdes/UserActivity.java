package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class UserActivity extends AppCompatActivity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        back = findViewById(R.id.iv_back);
        back.setOnClickListener(view -> onBackPressed());

    }
}