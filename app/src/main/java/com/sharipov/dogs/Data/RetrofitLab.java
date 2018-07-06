package com.sharipov.dogs.Data;

import com.sharipov.dogs.ResponseStructures.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLab {
    private static final RetrofitLab ourInstance = new RetrofitLab();
    private static Retrofit retrofit;

    private RetrofitLab() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static RetrofitLab getInstance() {
        return ourInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
