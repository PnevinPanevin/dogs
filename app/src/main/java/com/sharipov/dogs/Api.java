package com.sharipov.dogs;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://dog.ceo/api/";

    @GET("breeds/list/all")
    Call<Breeds> getBreeds();

    @GET("breeds/image/random")
    Call<RandomImage> getRandomImage();

}
