package com.utc.asm_mob_java.data.source.service;

import com.utc.asm_mob_java.utils.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHelper {
    private static ShopBearApi instance;

    public static ShopBearApi getInstanceAddress() {
        if (instance == null) {
            instance = getApiServiceAddress();
        }
        return instance;
    }

    public static ShopBearApi getApiServiceAddress() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(ShopBearApi.class);
    }
}
