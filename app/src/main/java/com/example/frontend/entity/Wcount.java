package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wcount {

    @SerializedName("one")
    public int one;

    @SerializedName("two")
    public int two;

    @SerializedName("three")
    public int three;

    @SerializedName("four")
    public int four;

    @SerializedName("five")
    public int five;

    @SerializedName("wcountList")
    public List<Wcount> wcountList;

    public int getOne() {
        return one;
    }

    public int getTwo() {
        return two;
    }

    public int getThree() {
        return three;
    }

    public int getFour() {
        return four;
    }

    public int getFive() {
        return five;
    }

    public List<Wcount> getWcountList() {
        return wcountList;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public void setWcountList(List<Wcount> wcountList) {
        this.wcountList = wcountList;
    }
}
