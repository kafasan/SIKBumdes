package com.sikbumdes.bumdes.api;

import com.sikbumdes.bumdes.model.LoginResponse;

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


    //==== AKUN ====


    //==== NERACA ====
}
