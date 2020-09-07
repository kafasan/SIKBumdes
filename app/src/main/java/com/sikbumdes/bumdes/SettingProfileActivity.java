package com.sikbumdes.bumdes;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.User;
import com.sikbumdes.bumdes.model.UserDetailResponse;
import com.sikbumdes.bumdes.model.UserUpdateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingProfileActivity extends AppCompatActivity {

    TextInputLayout tilCompany, tilAddress, tilPhone, tilEmail;
    TextInputEditText etCompany, etAddress, etPhone, etEmail;
    ImageView ivBack;
    Context context;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        context = this;

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(view -> onBackPressed());

        tilCompany = findViewById(R.id.til_Company);
        tilAddress = findViewById(R.id.til_Address);
        tilPhone = findViewById(R.id.til_Phone);
        tilEmail = findViewById(R.id.til_Email);
        etCompany = findViewById(R.id.et_company);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);

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
            getUserDetail();
        }
    }

    public void getUserDetail() {

        loading = ProgressDialog.show(context, null, "Mohon tunggu sebentar...", true, false);

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
                loading.dismiss();
                if (response.isSuccessful()) {
                    if (userDetailResponse.isSuccess()) {
                        etCompany.setText(userDetailResponse.getUserDetail().getName());
                        etAddress.setText(userDetailResponse.getUserDetail().getAddress());
                        etPhone.setText(userDetailResponse.getUserDetail().getPhone_number());
                        etEmail.setText(userDetailResponse.getUser().getEmail());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetailResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editUserDetail() {

        loading = ProgressDialog.show(context, null, "Mohon tunggu sebentar...", true, false);

        String company = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (company.isEmpty()) {
            loading.dismiss();
            tilCompany.setError("Nama Perusahaan tidak boleh kosong");
            etCompany.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            loading.dismiss();
            tilAddress.setError("Alamat Perusahaan tidak boleh kosong");
            etAddress.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            loading.dismiss();
            tilPhone.setError("Nomor Telepon tidak boleh kosong");
            etPhone.requestFocus();
            return;
        }

        int phoneValid = 0;
        if (phone.substring(0, 1).equals("0")) {
            phoneValid = 1;
        } else if (phone.substring(0, 2).equals("62")) {
            phoneValid = 1;
        } else if (phone.substring(0, 3).equals("+62")) {
            phoneValid = 1;
        }

        if (phoneValid != 1) {
            loading.dismiss();
            tilPhone.setError("Masukkan nomor telepon Indonesia yang valid");
            etPhone.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            loading.dismiss();
            tilEmail.setError("Email tidak boleh kosong");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loading.dismiss();
            tilEmail.setError("Masukkan email yang valid");
            etEmail.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        Call<UserUpdateResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateUser(token, company, address, phone, email);

        call.enqueue(new Callback<UserUpdateResponse>() {
            @Override
            public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                UserUpdateResponse userUpdateResponse = response.body();
                if (response.isSuccessful()) {
                    loading.dismiss();
                    if (userUpdateResponse.isSuccess()) {
                        Toast.makeText(context, "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserUpdateResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "Terjadi kesalahan. Silahkan coba lagi nanti.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}