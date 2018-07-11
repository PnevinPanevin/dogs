package com.sharipov.dogs.response_structures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.TreeMap;

public class Breeds {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private TreeMap<String, List<String>> message;
    //private LinkedList<BreedObject> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TreeMap<String, List<String>> getMessage() {
        return message;
    }
//    public LinkedList<BreedObject> getMessage() {
//        return message;
//    }

    public void setMessage(TreeMap<String, List<String>> message) {
        this.message = message;
    }
//        public void setMessage(LinkedList<BreedObject> message) {
//        this.message = message;
//    }
}
