package com.sharipov.dogs.Data;

import android.content.Context;
import android.widget.Toast;

import com.sharipov.dogs.ResponseStructures.Api;
import com.sharipov.dogs.ResponseStructures.Breeds;
import com.sharipov.dogs.ResponseStructures.RandomImage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsLab {
    private Api api;
    private HashMap<String, List<String>> breedsHashMap;
    private Context context;
    private List<BreedObject> breedObjectList;

    public interface OnGetBreedListener {
        void onSuccess(List<BreedObject> breedObjects);

        void onFalue(Throwable t);
    }

    public BreedsLab(Context context) {
        this.context = context;
        api = RetrofitLab
                .getInstance()
                .getRetrofit()
                .create(Api.class);
    }

    public void getBreedsHashMap(OnGetBreedListener listener) {
        breedObjectList = new LinkedList<>(); //data
        Call<Breeds> callBreeds = api.getBreeds();
        callBreeds.enqueue(new Callback<Breeds>() {
            @Override
            public void onResponse(Call<Breeds> call, Response<Breeds> response) {
                Breeds breeds = response.body();
                breedsHashMap = breeds.getMessage();

                for (Map.Entry<String, List<String>> entry : breedsHashMap.entrySet()) {
                    String breed = entry.getKey();
                    List<String> subBreedList = entry.getValue();
                    breedObjectList.add(new BreedObject(breed, subBreedList, null));
                }

                for (BreedObject b : breedObjectList) {
                    String subBreed = "";
                    List<String> subBreedList = b.getSubBreeds();
                    if (b.getSubBreeds().size() != 0) {
                        Random random = new Random();
                        subBreed = subBreedList.get(random.nextInt(subBreedList.size())) + "/";
                    }
                    Call<RandomImage> callBreeds = api.getRandomImage(b.getBreed(), subBreed);
                    callBreeds.enqueue(new Callback<RandomImage>() {
                        @Override
                        public void onResponse(Call<RandomImage> call, Response<RandomImage> response) {
                            RandomImage randomImage = response.body();
                            b.setImageUri(randomImage.getMessage());
                        }

                        @Override
                        public void onFailure(Call<RandomImage> call, Throwable t) {
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            listener.onFalue(t);
                        }
                    });
                }
                if (checkLoadAll(breedObjectList)) {
                    listener.onSuccess(breedObjectList);
                }
            }

            @Override
            public void onFailure(Call<Breeds> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                listener.onFalue(t);
            }
        });
    }

//    private void getImageUri(HashMap<String, List<String>> map, String subBreed) {
//        String breed = "";
//        List<String> breedList = map.get(subBreed);
//        if (breedList.size() != 0) {
//            Random random = new Random();
//            breed = breedList.get(random.nextInt(breedList.size())) + "/";
//        }
//        Call<RandomImage> call = api.getRandomImage(subBreed, breed);
//        call.enqueue(new Callback<RandomImage>() {
//            @Override
//            public void onResponse(Call<RandomImage> call, Response<RandomImage> response) {
//                RandomImage randomImage = response.body();
//                imageUri = randomImage.getMessage();
//            }
//
//            @Override
//            public void onFailure(Call<RandomImage> call, Throwable t) {
//                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public List<BreedObject> getBreedObjectList() {
        getBreedsHashMap(new OnGetBreedListener() {
            @Override
            public void onSuccess(List<BreedObject> breedObjects) {
                BreedsLab.this.breedObjectList = breedObjects;
            }

            @Override
            public void onFalue(Throwable t) {
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
