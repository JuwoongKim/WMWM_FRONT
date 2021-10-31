package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    public String resultCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
