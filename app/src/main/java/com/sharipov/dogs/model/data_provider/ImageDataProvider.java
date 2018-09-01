package com.sharipov.dogs.model.data_provider;

import android.util.Log;

import com.sharipov.dogs.model.response.ImageList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ImageDataProvider {

    private final String TAG = "qqq";
    private Api api;
    private List<String> imageUriList;
    private String breed, subBreed;

    public ImageDataProvider(String breed, String subBreed, File cacheDir){
        this.breed = breed;
        this.subBreed = subBreed;
        api = ApiManager.getApi(cacheDir);
        imageUriList = new ArrayList<>();
    }

    public void getImageList(Listeners.OnGetList<String> onGetList, Listeners.OnFail onFail) {
        if (imageUriList.size() == 0) {
            Observable<ImageList> imageListObservable;
            if (subBreed.equals("") || subBreed.equals(breed)) {
                imageListObservable = api.getBreedImageList(breed);
            } else {
                imageListObservable = api.getSubBreedImageList(breed, subBreed);
            }
            imageListObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(ImageList::getMessage)
                    .subscribe(
                            imageList -> {
                                imageUriList = imageList;
                                onGetList.onSuccess(imageUriList);
                            },
                            throwable -> onFail.onFail(throwable),
                            () -> Log.d(TAG, "getImageList: onComplete")
                    );
        } else {
            onGetList.onSuccess(imageUriList);
        }
    }
}
