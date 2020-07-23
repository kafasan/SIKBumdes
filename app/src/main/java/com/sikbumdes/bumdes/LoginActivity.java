package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.LoginResponse;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout tilEmail, tilPassword;
    TextInputEditText etEmail, etPassword;
    TextView forgetPass, registration;
    Button login;
    ProgressDialog loading;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmail = findViewById(R.id.til_Email);
        tilPassword = findViewById(R.id.til_Password);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        forgetPass = findViewById(R.id.tv_forgetpass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        registration = findViewById(R.id.tv_register);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    public void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void loginUser(){

        loading = ProgressDialog.show(LoginActivity.this, null, "Mohon tunggu sebentar...", true, false);

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse =response.body();
                if (response.isSuccessful()){
                    if (loginResponse.isSuccess()){
                        Log.i("LOGIN CHECK", "onResponse : SUCCESSFUL");
                        loading.dismiss();
                        SharedPrefManager.getInstance(LoginActivity.this).saveUser(loginResponse.getUser());
                        Toast.makeText(LoginActivity.this, "Login berhasil!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Log.i("LOGIN CHECK", "onResponse  : FAILED");
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, "Email atau Password salah, mohon coba lagi.", Toast.LENGTH_LONG).show();
                    }
                }else {
                    loading.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LOGIN CHECK", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                Toast.makeText(LoginActivity.this, "Kesalahan terjadi. Silakan coba lagi.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
