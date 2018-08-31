package com.sharipov.dogs.model.data;

import java.util.List;

public class BreedObject {
    private String title;
    private String breed;
    private List<String> subBreeds;
    private String imageUri;

    public BreedObject(String title, String breed, List<String> subBreeds, String imageUri){
        this.title = title;
        this.breed = breed;
        this.subBreeds = subBreeds;
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
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
}
