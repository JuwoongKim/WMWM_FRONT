package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryResponse {

    @SerializedName("result")
    public String resultCode;

    @SerializedName("historyInfoList")
    public List<HistoryRequest> historyInfoList;


    public List<HistoryRequest> getHistoryInfoList() {
        return historyInfoList;
    }

    public void setHistoryInfoList(List<HistoryRequest> historyInfoList) {
        this.historyInfoList = historyInfoList;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }
}
