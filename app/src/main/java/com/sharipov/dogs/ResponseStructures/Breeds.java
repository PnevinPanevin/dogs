package com.sharipov.dogs.ResponseStructures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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

    public Set<String> getKeys(){
        return message.keySet();
    }
}
