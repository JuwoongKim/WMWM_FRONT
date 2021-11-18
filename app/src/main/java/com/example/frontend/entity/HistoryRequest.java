package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

public class HistoryRequest {

    @SerializedName("loginId")
    public String loginId;

    @SerializedName("subLoginId")
    public String subLoginId;

    @SerializedName("aType")
    public String aType;

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String longitude;

    @SerializedName("day")
    public String day;

    @SerializedName("beforeDay")
    public String beforeDay;

    @SerializedName("regDt")
    public String regDt;

    public HistoryRequest(String loginId, String subLoginId, String aType, String latitude, String longitude) {
        this.loginId = loginId;
        this.subLoginId = subLoginId;
        this.aType = aType;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getaType() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = aType;
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
