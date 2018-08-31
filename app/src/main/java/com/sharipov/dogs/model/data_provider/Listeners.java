package com.sharipov.dogs.model.data_provider;

import java.util.HashMap;
import java.util.List;

public interface Listeners {

    interface OnGetBreeds {
        void onSuccess(HashMap<String, List<String>> breedsMap);
    }

    interface OnFail {
        void onFail(Throwable t);
    }

    interface OnGetList<T> {
        void onSuccess(List<T> t);
    }

    interface OnGetImage {
        void onSuccess(String imageUri);
    }
}
