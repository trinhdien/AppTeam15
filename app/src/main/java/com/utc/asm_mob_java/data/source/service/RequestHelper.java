package com.utc.asm_mob_java.data.source.service;

import com.utc.asm_mob_java.utils.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHelper {
    private static RentRoomApi instance;

    public static RentRoomApi getInstanceAddress() {
        if (instance == null) {
            instance = getApiServiceAddress();
        }
        return instance;
    }

    public static RentRoomApi getApiServiceAddress() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(RentRoomApi.class);
    }
}
