package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.api.SharedPrefManager;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout cv_profile, cv_mngEmployee, cv_mngBusiness, cv_changePass, cv_logout;
    MaterialButton btn_upgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cv_profile = findViewById(R.id.ll_profile);
        cv_mngEmployee = findViewById(R.id.ll_employee);
        cv_mngBusiness = findViewById(R.id.ll_business);
        cv_changePass = findViewById(R.id.ll_changepass);
        cv_logout = findViewById(R.id.ll_logout);

        btn_upgrade = findViewById(R.id.btn_buyPro);

        cv_profile.setOnClickListener(this);
        cv_mngEmployee.setOnClickListener(this);
        cv_mngBusiness.setOnClickListener(this);
        cv_changePass.setOnClickListener(this);
        cv_logout.setOnClickListener(this);

        btn_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_profile:
                startActivity(new Intent(SettingsActivity.this, UserActivity.class));
                break;
            case R.id.ll_employee:
                startActivity(new Intent(SettingsActivity.this, UserActivity.class));
                break;
            case R.id.ll_business:
                startActivity(new Intent(SettingsActivity.this, UserActivity.class));
                break;
            case R.id.ll_changepass:
                startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.ll_logout:
                SharedPrefManager.getInstance(SettingsActivity.this).clear();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}
