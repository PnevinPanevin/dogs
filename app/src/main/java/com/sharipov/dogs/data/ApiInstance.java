package com.sharipov.dogs.data;

import com.sharipov.dogs.response_structures.Api;

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

    public static Api getApi() {
        return api;
    }
}
