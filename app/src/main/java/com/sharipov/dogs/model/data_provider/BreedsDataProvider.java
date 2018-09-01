package com.sharipov.dogs.model.data_provider;

import android.util.Log;

import com.sharipov.dogs.model.data.BreedObject;
import com.sharipov.dogs.model.response.Breeds;
import com.sharipov.dogs.model.response.RandomImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BreedsDataProvider {

    public static final String TAG = "qqq";
    private Api api;
    private List<BreedObject> breedObjectList;

    public BreedsDataProvider(File cacheDir) {
        api = ApiManager.getApi(cacheDir);
    }

    private void getBreedsMap(Listeners.OnGetBreeds onGetBreeds, Listeners.OnFail onFail) {
        api.getBreeds()
                .subscribeOn(Schedulers.io())
                .map(Breeds::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hashMap -> onGetBreeds.onSuccess(hashMap),
                        throwable -> onFail.onFail(throwable),
                        () -> Log.d(TAG, "getBreedsMap: onComplete")
                );
    }

    private void getImageUri(String breed,
                             Listeners.OnGetImage onGetImage,
                             Listeners.OnFail onFail) {
        api.getBreedImage(breed)
                .subscribeOn(Schedulers.io())
                .map(RandomImage::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        message -> onGetImage.onSuccess(message),
                        throwable -> onFail.onFail(throwable),
                        () -> Log.d(TAG, "getImageUri: onComplete")
                );
    }

    public void getBreedObjectList(Listeners.OnGetList<BreedObject> onGetList, Listeners.OnFail onFail) {
        if (breedObjectList == null) {
            breedObjectList = new ArrayList<>();
            getBreedsMap(
                    breedsMap -> {
                        for (Map.Entry<String, List<String>> entry : breedsMap.entrySet()) {
                            String breed = entry.getKey();
                            List<String> subBreedList = entry.getValue();
                            getImageUri(
                                    breed,
                                    imageUri -> {
                                        breedObjectList.add(new BreedObject(firstCharToUpperCase(breed), breed, subBreedList, imageUri));
                                        if (breedObjectList.size() == breedsMap.size()) {
                                            Collections.sort(breedObjectList, (o1, o2) -> o1.getBreed().compareTo(o2.getBreed()));
                                            onGetList.onSuccess(breedObjectList);
                                        }
                                        Log.d(TAG, "onSuccess: " + breedObjectList.size() + ")" + breed + " " + imageUri);
                                    },
                                    t -> onFail.onFail(t));
                        }
                    },
                    t -> onFail.onFail(t)
            );
        } else {
            onGetList.onSuccess(breedObjectList);
        }
    }

    private String firstCharToUpperCase(String s) {
        char[] sCharArray = s.toCharArray();
        sCharArray[0] = Character.toUpperCase(sCharArray[0]);
        return new String(sCharArray);
    }
}
