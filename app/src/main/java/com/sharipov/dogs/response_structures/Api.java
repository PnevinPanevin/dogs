package com.sharipov.dogs.response_structures;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "https://dog.ceo/api/";

    @GET("breeds/list/all")
    Call<Breeds> getBreeds();

    @GET("breeds/image/random")
    Call<RandomImage> getRandomImage();

    @GET("breed/{subBreed}/images/random")
    Call<RandomImage> getBreedImage(@Path("subBreed") String subBreed);

    @GET("breed/{subBreed}/{breed}/images/random")
    Call<RandomImage> getSubBreedImage(@Path("subBreed") String subBreed, @Path("breed") String breed);

    @GET("breed/{breed}/list")
    Call<SubBreeds> getSubBreedsList(@Path("breed") String breed);

    @GET("breed/{breed}/images")
    Call<ImageList> getBreedImageList(@Path("breed") String breed);

    @GET("breed/{breed}/{subBreed}/images")
    Call<ImageList> getSubBreedImageList(@Path("breed") String breed, @Path("subBreed") String subBreed);

}
