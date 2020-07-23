package com.sikbumdes.bumdes.api;

import com.sikbumdes.bumdes.model.LoginResponse;
import com.sikbumdes.bumdes.model.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {

    //==== GENERAL ====
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<SignUpResponse> signUp(
            @Field("name") String company,
            @Field("address") String address,
            @Field("phone_number") String phone,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String confirm_password
    );


    //==== AKUN ====


    //==== NERACA ====
}
