package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("loginId")
    public String loginId;

    @SerializedName("pwd")
    public String pwd;

    public String getLoginId() {
        return loginId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public LoginRequest(String loginId, String pwd) {
        this.loginId = loginId;
        this.pwd = pwd;
    }
}
