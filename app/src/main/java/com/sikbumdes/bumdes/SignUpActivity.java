package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.model.SignUpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout tilCompany, tilAddress, tilPhone, tilEmail, tilPassword, tilConfPassword;
    TextInputEditText etCompany, etAddress, etPhone, etEmail, etPassword, etConfPassword;
    ImageView login;
    Button register;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tilCompany = findViewById(R.id.til_Company);
        tilAddress = findViewById(R.id.til_Address);
        tilPhone = findViewById(R.id.til_Phone);
        tilEmail = findViewById(R.id.til_Email);
        tilPassword = findViewById(R.id.til_Password);
        tilConfPassword = findViewById(R.id.til_Password_Conf);
        etCompany = findViewById(R.id.et_company);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfPassword = findViewById(R.id.et_password_confirm);

        login = findViewById(R.id.iv_back);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        register = findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void signUp(){

        loading = ProgressDialog.show(SignUpActivity.this, null, "Mohon tunggu sebentar...", true, false);

        String company = etCompany.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmpassword = etConfPassword.getText().toString().trim();

        if (company.isEmpty()){
            loading.dismiss();
            tilCompany.setError("Nama Perusahaan tidak boleh kosong");
            etCompany.requestFocus();
            return;
        }

        if (address.isEmpty()){
            loading.dismiss();
            tilAddress.setError("Alamat Perusahaan tidak boleh kosong");
            etAddress.requestFocus();
            return;
        }

        if (phone.isEmpty()){
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


        if (email.isEmpty()){
            loading.dismiss();
            tilEmail.setError("Email tidak boleh kosong");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loading.dismiss();
            tilEmail.setError("Masukkan email yang valid");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            loading.dismiss();
            tilPassword.setError("Password tidak boleh kosong");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 8){
            loading.dismiss();
            tilPassword.setError("Password harus lebih dari 8 karakter");
            etPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmpassword)) {
            loading.dismiss();
            tilPassword.setError("Password tidak sama");
            tilConfPassword.setError("Password tidak sama");
            etConfPassword.requestFocus();
            return;
        }

//        String phoneEdit;
//        if (phone.substring(0, 1).equals("0")) {
//            phoneEdit = "+62" + phone.substring(1);
//        } else if (phone.substring(0, 2).equals("62")) {
//            phoneEdit = "+62" + phone.substring(2);
//        } else {
//            phoneEdit = phone;
//        }

        Call<SignUpResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signUp(company, address, phone, email, password, confirmpassword);

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse signUpResponse = response.body();
                if (response.isSuccessful()){
                    if (signUpResponse.isSuccess()){
                        Log.i("SIGN UP CHECK", "onResponse : SUCCESSFUL");
                        loading.dismiss();
                        Toast.makeText(SignUpActivity.this, "Registrasi berhasil!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }else {
                    loading.dismiss();
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody.trim());
                        jsonObject = jsonObject.getJSONObject("errors");
                        Iterator<String> keys = jsonObject.keys();
                        StringBuilder errors = new StringBuilder();
                        String separator = "";
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONArray arr = jsonObject.getJSONArray(key);
                            for (int i = 0; i < arr.length(); i++) {
                                errors.append(separator).append(key).append(" : ").append(arr.getString(i));
                                separator = "\n";
                            }
                        }
                        Toast.makeText(SignUpActivity.this, errors.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.e("LOGIN CHECK", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                Toast.makeText(SignUpActivity.this, "Kesalahan terjadi. Silakan coba lagi.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
