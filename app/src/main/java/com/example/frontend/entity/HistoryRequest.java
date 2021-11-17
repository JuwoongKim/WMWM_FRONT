package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

public class HistoryRequest {

    @SerializedName("loginId")
    public String loginId;

    @SerializedName("subLoginId")
    public String subLoginId;

    @SerializedName("aType")
    public String aType;

    @SerializedName("loginId")
    public String latitude;

    @SerializedName("loginId")
    public String longitude;

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


}
