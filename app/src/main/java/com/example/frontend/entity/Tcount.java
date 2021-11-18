package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tcount {

   @SerializedName("type")
    public String type;

   @SerializedName("count")
    public int count;

   @SerializedName("tcountList")
    public List<Tcount> tcountList;

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public List<Tcount> getTcountList() {
        return tcountList;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTcountList(List<Tcount> tcountList) {
        this.tcountList = tcountList;
    }
}
