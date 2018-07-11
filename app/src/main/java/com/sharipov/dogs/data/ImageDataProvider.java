package com.sharipov.dogs.data;

import android.util.Log;

import com.sharipov.dogs.response_structures.Api;
import com.sharipov.dogs.response_structures.ImageList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDataProvider {

    private final String TAG = "qqq";
    private Api api;
    private List<String> imageUriList;
    private String breed, subBreed;

    public interface OnGetList{
        void onSuccess(List<String> list);
        void onFail(Throwable t);
    }

    public ImageDataProvider(String breed, String subBreed){
        this.breed = breed;
        this.subBreed = subBreed;
        api = ApiInstance.getApi();
        imageUriList = new ArrayList<>();
    }

    public void getImageList(OnGetList onGetList){
        Call<ImageList> call;
        if (subBreed.equals("")||subBreed.equals(breed)) {
            call = api.getBreedImageList(breed);
        } else {
            call = api.getSubBreedImageList(breed, subBreed);
        }
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                ImageList imageList = response.body();
                imageUriList = imageList.getMessage();
                onGetList.onSuccess(imageUriList);
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {

            }
        });
    }
}
