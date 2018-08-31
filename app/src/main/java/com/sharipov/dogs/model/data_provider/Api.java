package com.sharipov.dogs.model.data_provider;

import com.sharipov.dogs.model.response.Breeds;
import com.sharipov.dogs.model.response.ImageList;
import com.sharipov.dogs.model.response.RandomImage;
import com.sharipov.dogs.model.response.SubBreeds;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "https://dog.ceo/api/";

    @GET("breeds/list/all")
    Observable<Breeds> getBreeds();

    @GET("breeds/image/random")
    Observable<RandomImage> getRandomImage();

    @GET("breed/{subBreed}/images/random")
    Observable<RandomImage> getBreedImage(@Path("subBreed") String subBreed);

    @GET("breed/{subBreed}/{breed}/images/random")
    Observable<RandomImage> getSubBreedImage(@Path("subBreed") String subBreed, @Path("breed") String breed);

    @GET("breed/{breed}/list")
    Observable<SubBreeds> getSubBreedsList(@Path("breed") String breed);

    @GET("breed/{breed}/images")
    Observable<ImageList> getBreedImageList(@Path("breed") String breed);

    @GET("breed/{breed}/{subBreed}/images")
    Observable<ImageList> getSubBreedImageList(@Path("breed") String breed, @Path("subBreed") String subBreed);

}
