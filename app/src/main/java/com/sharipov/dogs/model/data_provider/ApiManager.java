package com.sharipov.dogs.model.data_provider;


import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static Api api;
    private final static int CACHE_SIZE = 10 * 1024 * 1024;
    private final static int MAX_AGE = 7;
    private final static String CACHE_DIRECTORY_NAME = "http-cache";
    private final static Interceptor NETWORK_INTERCEPTOR = chain -> {
        Response response = chain.proceed(chain.request());
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(MAX_AGE, TimeUnit.MINUTES)
                .build();
        return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build();
    };

    public static Api getApi(File cacheDir) {
        if (api == null) {
            File httpCacheDirectory = new File(cacheDir, CACHE_DIRECTORY_NAME);
            Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(cache)
                    .addNetworkInterceptor(NETWORK_INTERCEPTOR)
                    .build();
            api = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build().create(Api.class);
        }
        return api;
    }
}
