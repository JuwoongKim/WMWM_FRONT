package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
