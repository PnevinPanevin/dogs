package com.sharipov.dogs.model.data_provider;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static Api api;
    public static final ApiManager INSTANCE = new ApiManager();

    private ApiManager() {
        api =  new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(Api.class);
    }

    public static Api getApi() {
        return api;
    }
}
