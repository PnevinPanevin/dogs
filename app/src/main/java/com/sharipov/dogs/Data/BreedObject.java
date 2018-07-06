package com.sharipov.dogs.Data;

import java.util.List;

public class BreedObject {
    private String breed;
    private List<String> subBreeds;
    private String imageUri;

    public BreedObject(String breed, List<String> subBreeds, String imageUri){
        this.breed = breed;
        this.subBreeds = subBreeds;
        this.imageUri = imageUri;
    }

    public String getBreed() {
        return breed;
    }

    public String getImageUri() {
        return imageUri;
    }

    public List<String> getSubBreeds() {
        return subBreeds;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
