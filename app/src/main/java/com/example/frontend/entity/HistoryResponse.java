package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

public class HistoryResponse {

    @SerializedName("result")
    public String resultCode;

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }
}
