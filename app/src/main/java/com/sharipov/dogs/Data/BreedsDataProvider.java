package com.sharipov.dogs.Data;

import android.util.Log;

import com.sharipov.dogs.ResponseStructures.Api;
import com.sharipov.dogs.ResponseStructures.Breeds;
import com.sharipov.dogs.ResponseStructures.RandomImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsDataProvider {

    private final String TAG = "qqq";
    private Api api;
    private List<BreedObject> breedObjectList;

    public interface OnGetBreedsListener {
        void onSuccess(TreeMap<String, List<String>> breedsTreeMap);

        void onFail(Throwable t);
    }

    public interface OnGetImageListener {
        void onSuccess(String imageUri);

        void onFail(Throwable t);
    }

    public interface OnGetListListener {
        void onSuccess(List<BreedObject> breedList);

        void onFail(Throwable t);
    }

    public BreedsDataProvider() {
        api = ApiInstance
                .getInstance()
                .getApi();
        breedObjectList = new LinkedList<>();
    }

    private void getBreedsTreeMap(OnGetBreedsListener onGetBreedsListener) {
        Call<Breeds> callBreeds = api.getBreeds();
        callBreeds.enqueue(new Callback<Breeds>() {
            @Override
            public void onResponse(Call<Breeds> call, Response<Breeds> response) {
                Breeds breeds = response.body();
                onGetBreedsListener.onSuccess(breeds.getMessage());
            }

            @Override
            public void onFailure(Call<Breeds> call, Throwable t) {
                onGetBreedsListener.onFail(t);
            }
        });
    }

    private void getImageUri(String breed, OnGetImageListener onGetImageListener) {
        Call<RandomImage> callBreeds = api.getBreedImage(breed);
        callBreeds.enqueue(new Callback<RandomImage>() {
            @Override
            public void onResponse(Call<RandomImage> call, Response<RandomImage> response) {
                RandomImage randomImage = response.body();
                onGetImageListener.onSuccess(randomImage.getMessage());
            }

            @Override
            public void onFailure(Call<RandomImage> call, Throwable t) {
                onGetImageListener.onFail(t);
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    public void getBreedObjectList(OnGetListListener onGetListListener) {
        getBreedsTreeMap(new OnGetBreedsListener() {
            @Override
            public void onSuccess(TreeMap<String, List<String>> breedsTreemap) {
                for (Map.Entry<String, List<String>> entry : breedsTreemap.entrySet()) {
                    String breed = entry.getKey();
                    List<String> subBreedList = entry.getValue();
                    getImageUri(breed, new OnGetImageListener() {
                        @Override
                        public void onSuccess(String imageUri) {
                            breedObjectList.add(new BreedObject(breed, subBreedList, imageUri));
                            if (breedObjectList.size() == breedsTreemap.size()) {
                                onGetListListener.onSuccess(breedObjectList);
                            }
                            Log.d(TAG, "onSuccess: " + breedObjectList.size() + ")" + breed + " " + imageUri);
                        }

                        @Override
                        public void onFail(Throwable t) {
                            Log.d(TAG, "onFail: " + t);
                        }
                    });
                }
            }

            @Override
            public void onFail(Throwable t) {
                onGetListListener.onFail(t);
                Log.d(TAG, "onFail: " + t);
            }
        });
    }
}
