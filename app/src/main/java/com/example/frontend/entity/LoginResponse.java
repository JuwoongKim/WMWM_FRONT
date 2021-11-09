package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    public String resultCode;

    /*start getter, setter*/
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    /*end getter, setter*/
}
