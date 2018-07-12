package com.sharipov.dogs.data;

import android.util.Log;

import com.sharipov.dogs.response_structures.Api;
import com.sharipov.dogs.response_structures.Breeds;
import com.sharipov.dogs.response_structures.RandomImage;

import java.util.ArrayList;
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
        api = ApiInstance.getApi();
        breedObjectList = new ArrayList<>();
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
                            breedObjectList.add(new BreedObject(firstCharToUpperCase(breed), breed, subBreedList, imageUri));
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

    private String firstCharToUpperCase(String s) {
        char[] sCharArray = s.toCharArray();
        sCharArray[0] = Character.toUpperCase(sCharArray[0]);
        return new String(sCharArray);
    }

    public static List<BreedObject> getFilteredList(String query, List<BreedObject> breedObjects) {
        List<BreedObject> newList = new ArrayList<>();
        for (BreedObject b : breedObjects) {
            if (b.getBreed().contains(query)) {
                newList.add(b);
            }
        }
        return newList;
    }
}
