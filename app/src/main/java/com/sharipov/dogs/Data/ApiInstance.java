package com.sharipov.dogs.Data;

import com.sharipov.dogs.ResponseStructures.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {
    private static final ApiInstance ourInstance = new ApiInstance();
    private static Api api;

    private ApiInstance() {
        api = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api.class);

    }
    public static ApiInstance getInstance() {
        return ourInstance;
    }

    public Api getApi() {
        return api;
    }
}
