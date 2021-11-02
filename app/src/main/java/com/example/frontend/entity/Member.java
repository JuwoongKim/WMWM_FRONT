package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Member {
    /*private String profile;*/
    @SerializedName("userNo")
    private String userNo;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @SerializedName("email")
    private String email;

    @SerializedName("loginId")
    private String loginId;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("userName")
    private String userName;

    @SerializedName("memberList")
    private List<Member> memberList;

    @SerializedName("memberInfo")
    private List<Member> memberInfo;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public List<Member> getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(List<Member> memberInfo) {
        this.memberInfo = memberInfo;
    }
}
