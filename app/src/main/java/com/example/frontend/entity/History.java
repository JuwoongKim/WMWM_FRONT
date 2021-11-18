package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class History {

    @SerializedName("seq")
    public int seq;

    @SerializedName("loginId")
    public String loginId;

    @SerializedName("subLoginId")
    public String subLoginId;

    @SerializedName("type")
    public String type;

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String longitude;

    @SerializedName("regDt")
    public String regdt;



    @SerializedName("historyList")
    public List<History> historyList;


    /*start getter, setter*/

    public int getSeq() { return seq; }

    public String getLoginId() {
        return loginId;
    }

    public String getSubLoginId() {
        return subLoginId;
    }

    public String getType() {
        return type;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public List<History> getHistoryList() { return historyList; }

    public String getRegdt() { return regdt; }

    public void setSeq(int seq) { this.seq = seq; }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setSubLoginId(String subLoginId) {
        this.subLoginId = subLoginId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setHistoryList(List<History> historyList) { this.historyList = historyList; }

    public void setRegdt(String regdt) { this.regdt = regdt; }
}
