package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.api.SharedPrefManager;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout cv_profile, cv_mngEmployee, cv_mngBusiness, cv_changePass, cv_logout;
    MaterialButton btn_upgrade;
    ImageView ivBack;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context = this;

        cv_profile = findViewById(R.id.ll_profile);
        cv_changePass = findViewById(R.id.ll_changepass);
        cv_logout = findViewById(R.id.ll_logout);

        btn_upgrade = findViewById(R.id.btn_buyPro);

        cv_profile.setOnClickListener(this);
        cv_changePass.setOnClickListener(this);
        cv_logout.setOnClickListener(this);

        btn_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(view -> onBackPressed());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_profile:
                startActivity(new Intent(SettingsActivity.this, SettingProfileActivity.class));
                break;
            case R.id.ll_changepass:
                startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.ll_logout:
                confirmLogOut();
                break;
        }
    }

    private void confirmLogOut() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog.getWindow().setLayout(width, height);

        Button btnNo = dialog.findViewById(R.id.btn_no);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(SettingsActivity.this).clear();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}
