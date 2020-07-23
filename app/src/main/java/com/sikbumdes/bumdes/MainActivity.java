package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.User;

public class MainActivity extends AppCompatActivity {

    TextView company_name, company_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        company_name = findViewById(R.id.tv_name);
        company_email = findViewById(R.id.tv_mail);
        User user = SharedPrefManager.getInstance(this).getUser();
        company_name.setText(user.getEmail());
        company_email.setText(user.getToken());
    }
}
