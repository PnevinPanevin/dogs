package com.sharipov.dogs.Data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sharipov.dogs.ResponseStructures.Api;
import com.sharipov.dogs.ResponseStructures.Breeds;
import com.sharipov.dogs.ResponseStructures.RandomImage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsLab {

    private final String TAG = "qqq";
    private Api api;
    private HashMap<String, List<String>> breedsHashMap;
    private Context context;
    private List<BreedObject> breedObjectList;

    public interface OnGetBreedListener {
        void onSuccess(List<BreedObject> breedObjects);

        void onFail(Throwable t);
    }

    public interface OnGetImageListener {
        void onSuccess(String imageUri);

        void onFail(Throwable t);
    }

    public BreedsLab(Context context) {
        this.context = context;
        api = RetrofitLab
                .getInstance()
                .getRetrofit()
                .create(Api.class);
    }

    private void getBreedsHashMap(OnGetBreedListener onGetBreedListener) {
        breedObjectList = new LinkedList<>(); //data
        Call<Breeds> callBreeds = api.getBreeds();
        callBreeds.enqueue(new Callback<Breeds>() {
            @Override
            public void onResponse(Call<Breeds> call, Response<Breeds> response) {
                Breeds breeds = response.body();
                breedsHashMap = breeds.getMessage();

                Log.d(TAG, "onResponse: Breeds");

                for (Map.Entry<String, List<String>> entry : breedsHashMap.entrySet()) {
                    String breed = entry.getKey();
                    List<String> subBreedList = entry.getValue();
                    breedObjectList.add(new BreedObject(breed, subBreedList, null));
                }

                Log.d(TAG, "onResponse: " + breedObjectList.size());
                onGetBreedListener.onSuccess(breedObjectList);

                for (BreedObject b : breedObjectList) {
                    String subBreed = "";
                    List<String> subBreedList = b.getSubBreeds();

                    Log.d(TAG, "onResponse: " + b.getBreed());

                    if (b.getSubBreeds().size() != 0) {
                        Random random = new Random();
                        subBreed = subBreedList.get(random.nextInt(subBreedList.size())) + "/";
                    }

                    getImageUri(b.getBreed(), subBreed, new OnGetImageListener() {
                        @Override
                        public void onSuccess(String imageUri) {
                            b.setImageUri(imageUri);
                        }

                        @Override
                        public void onFail(Throwable t) {
                            Log.d(TAG, "onFail: " + t);
                        }
                    });
                }
//                if (checkLoadAll(breedObjectList)) {
//                    onGetBreedListener.onSuccess(breedObjectList);
//                }
            }

            @Override
            public void onFailure(Call<Breeds> call, Throwable t) {
                onGetBreedListener.onFail(t);
            }
        });
    }

    private void getImageUri(String breed, String subBreed, OnGetImageListener onGetImageListener) {
        Call<RandomImage> callBreeds = api.getRandomImage(breed, subBreed);
        callBreeds.enqueue(new Callback<RandomImage>() {
            @Override
            public void onResponse(Call<RandomImage> call, Response<RandomImage> response) {
                RandomImage randomImage = response.body();
                if (randomImage != null) {
                    onGetImageListener.onSuccess(randomImage.getMessage());
                    //Log.d(TAG, "onSuccess: " + randomImage.getMessage());
                } else Log.d(TAG, "onResponse: " + "\n******************\n" + breed + " " + subBreed + "\n******************");
            }

            @Override
            public void onFailure(Call<RandomImage> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                onGetImageListener.onFail(t);
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    public List<BreedObject> getBreedObjectList() {
        getBreedsHashMap(new OnGetBreedListener() {
            @Override
            public void onSuccess(List<BreedObject> breedObjects) {
                BreedsLab.this.breedObjectList = breedObjects;
                Log.d(TAG, "onSuccess: " + breedObjectList.size());
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return breedObjectList;
    }

    private boolean checkLoadAll(List<BreedObject> breedObjects) {
        boolean passTheCheck = true;

        for (BreedObject b : breedObjects) {
            if (b.getImageUri() == null) {
                passTheCheck = false;
                break;
            }
        }
        return passTheCheck;
    }
}
