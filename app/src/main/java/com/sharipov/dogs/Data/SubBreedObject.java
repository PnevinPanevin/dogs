package com.sharipov.dogs.Data;

public class SubBreedObject {
    private String subBreed;
    private String imageUri;

    public SubBreedObject(String subBreed, String imageUri) {
        this.subBreed = subBreed;
        this.imageUri = imageUri;
    }

    public String getSubBreed() {
        return subBreed;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
