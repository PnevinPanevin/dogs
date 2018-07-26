package com.sharipov.dogs.response_structures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class Breeds {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private HashMap<String, List<String>> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, List<String>> getMessage() {
        return message;
    }

    public void setMessage(HashMap<String, List<String>> message) {
        this.message = message;
    }

}
