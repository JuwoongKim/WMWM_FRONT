package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryRequest {

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

    @SerializedName("historyList")
    public List<HistoryRequest> historyList;

    @SerializedName("day")
    public String day;

    @SerializedName("beforeDay")
    public String beforeDay;

    @SerializedName("regDt")
    public String regDt;

    public HistoryRequest(String loginId, String subLoginId, String aType, String latitude, String longitude) {
        this.loginId = loginId;
        this.subLoginId = subLoginId;
        this.type = aType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public List<HistoryRequest> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<HistoryRequest> historyList) {
        this.historyList = historyList;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getSubLoginId() {
        return subLoginId;
    }

    public void setSubLoginId(String subLoginId) {
        this.subLoginId = subLoginId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBeforeDay() {
        return beforeDay;
    }

    public void setBeforeDay(String beforeDay) {
        this.beforeDay = beforeDay;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }
}
