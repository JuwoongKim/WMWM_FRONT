package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("loginId")
    public String loginId;

    @SerializedName("pwd")
    public String pwd;

    /*start getter, setter*/
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
    /*end getter, setter*/
    

    public LoginRequest(String loginId, String pwd) {
        //생성자
        this.loginId = loginId;
        this.pwd = pwd;
    }

}
