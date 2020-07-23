package com.sikbumdes.bumdes.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL_API = "https://bumdes.sikbumdes.com/dashboard/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public BaseApiService getApi() {
        return retrofit.create(BaseApiService.class);
    }

}
