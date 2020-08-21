package com.sikbumdes.bumdes.api;

import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunResponse;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.AkunClassResponse;
import com.sikbumdes.bumdes.model.AkunParentResponse;
import com.sikbumdes.bumdes.model.LoginResponse;
import com.sikbumdes.bumdes.model.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    @GET("parent")
    Call<AkunParentResponse> getAkunParent(
            @Header("Authorization") String token
    );

    @GET("classification/{id}")
    Call<AkunClassResponse> getAkunClass(
            @Header("Authorization") String token,
            @Path("id") int id_parent
    );

    @GET("account/{id}")
    Call<AkunAkunResponse> getAkunAkun(
            @Header("Authorization") String token,
            @Path("id") int id_class
    );

    //==== NERACA ====
}
