package com.sharipov.dogs.model.data_provider;

import android.util.Log;

import com.sharipov.dogs.model.data.SubBreedObject;
import com.sharipov.dogs.model.response.RandomImage;
import com.sharipov.dogs.model.response.SubBreeds;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SubBreedsDataProvider {

    private static final String TAG = "qqq";
    private String breed;
    private Api api;
    private List<SubBreedObject> subBreedsList;

    public SubBreedsDataProvider(String breed, File cacheDir) {
        this.breed = breed;
        api = ApiManager.getApi(cacheDir);
        subBreedsList = new LinkedList<>();
    }

    private void getMessage(Listeners.OnGetList<String> onGetList, Listeners.OnFail onFail) {
        api.getSubBreedsList(breed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(SubBreeds::getMessage)
                .subscribe(
                        list -> onGetList.onSuccess(list),
                        throwable -> onFail.onFail(throwable),
                        () -> Log.d(TAG, "getMessage: onComplete")
                );
    }

    public void getSubBreedsList(Listeners.OnGetList<SubBreedObject> onGetList, Listeners.OnFail onFail) {
        getMessage(
                subBreedsStringlist -> {
                    for (String subBreed : subBreedsStringlist) {
                        getImageUri(
                                breed,
                                subBreed,
                                imageUri -> {
                                    subBreedsList.add(new SubBreedObject(getTitle(subBreed), subBreed, imageUri));
                                    if (subBreedsList.size() == subBreedsStringlist.size()) {
                                        Collections.sort(subBreedsList, (o1, o2) -> o1.getSubBreed().compareTo(o2.getSubBreed()));
                                        onGetList.onSuccess(subBreedsList);
                                    }
                                },
                                throwable -> onFail.onFail(throwable)
                        );
                    }
                },
                throwable -> onFail.onFail(throwable)
        );
    }

    private void getImageUri(String breed, String subBreed, Listeners.OnGetImage onGetImage, Listeners.OnFail onFail) {
        api.getSubBreedImage(breed, subBreed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(RandomImage::getMessage)
                .subscribe(
                        imageUri -> onGetImage.onSuccess(imageUri),
                        throwable -> onFail.onFail(throwable),
                        () -> Log.d(TAG, "getImageUri: onComplete")
                );
    }

    private String getTitle(String s) {
        char[] sCharArray = s.toCharArray();
        sCharArray[0] = Character.toUpperCase(sCharArray[0]);
        return new String(sCharArray);
    }
}
