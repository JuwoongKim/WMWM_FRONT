package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Member {
    /*private String profile;*/
    private String email;
    private String loginId;
    private String pwd;
    private String userName;

    @SerializedName("memberList")
    private List<Member> memberList;

    @SerializedName("memberInfo")
    private Member memberInfo;

    public Member getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(Member memberInfo) {
        this.memberInfo = memberInfo;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

/*    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }*/

    public Member(){

    }

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
}
