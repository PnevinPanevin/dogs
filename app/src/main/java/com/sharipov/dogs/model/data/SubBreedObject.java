package com.sharipov.dogs.model.data;

public class SubBreedObject {
    private String title;
    private String subBreed;
    private String imageUri;

    public SubBreedObject(String title, String subBreed, String imageUri) {
        this.title = title;
        this.subBreed = subBreed;
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public String getSubBreed() {
        return subBreed;
    }

    public String getImageUri() {
        return imageUri;
    }
}
