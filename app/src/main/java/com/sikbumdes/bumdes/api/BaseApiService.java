package com.sikbumdes.bumdes.api;

import com.sikbumdes.bumdes.model.AkunAkunResponse;
import com.sikbumdes.bumdes.model.AkunAkunUpdateResponse;
import com.sikbumdes.bumdes.model.AkunClassUpdateResponse;
import com.sikbumdes.bumdes.model.AkunClassResponse;
import com.sikbumdes.bumdes.model.AkunClassUpdateResponse;
import com.sikbumdes.bumdes.model.AkunDeleteResponse;
import com.sikbumdes.bumdes.model.AkunParentResponse;
import com.sikbumdes.bumdes.model.LoginResponse;
import com.sikbumdes.bumdes.model.SignUpResponse;
import com.sikbumdes.bumdes.model.UserDetailResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
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

    @GET("user")
    Call<UserDetailResponse> getUserDetail(
            @Header("Authorization") String token
    );

    //==== AKUN ====
    @GET("parent")
    Call<AkunParentResponse> getAkunParent(
            @Header("Authorization") String token
    );

    @GET("classification")
    Call<AkunClassResponse> getAkunClassAll(
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

    @FormUrlEncoded
    @POST("classification")
    Call<AkunClassUpdateResponse> storeClass(
            @Header("Authorization") String token,
            @Field("id_parent") String id_parent,
            @Field("name") String name,
            @Field("code") String code

    );

    @FormUrlEncoded
    @POST("classification/{id}")
    Call<AkunClassUpdateResponse> updateClass(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("id_parent") int id_parent,
            @Field("name") String name,
            @Field("code") String code
    );

    @DELETE("classification/{id}")
    Call<AkunDeleteResponse> deleteClass(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("account")
    Call<AkunAkunUpdateResponse> storeAkun(
            @Header("Authorization") String token,
            @Field("id_classification") String id_classification,
            @Field("name") String name,
            @Field("code") String code,
            @Field("position") String position

    );

    @FormUrlEncoded
    @POST("account/{id}")
    Call<AkunAkunUpdateResponse> updateAkun(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("id_classification") int id_classification,
            @Field("name") String name,
            @Field("code") String code,
            @Field("position") String position
    );

    @DELETE("account/{id}")
    Call<AkunDeleteResponse> deleteAccount(
            @Header("Authorization") String token,
            @Path("id") int id
    );


    //==== NERACA ====
}
