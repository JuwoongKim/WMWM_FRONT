package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rank {

    @SerializedName("userNo")
    private String userNo;

    @SerializedName("seq")
    private String seq;

    @SerializedName("total")
    private String total;

    @SerializedName("regDt")
    private String regDt;

    @SerializedName("userName")
    private String userName;

    @SerializedName("periodRankList")
    private ArrayList<Rank> periodRankList;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Rank> getPeriodRankList() {
        return periodRankList;
    }

    public void setPeriodRankList(ArrayList<Rank> periodRankList) {
        this.periodRankList = periodRankList;
    }
}
