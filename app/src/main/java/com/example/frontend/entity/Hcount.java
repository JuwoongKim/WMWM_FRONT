package com.example.frontend.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hcount {

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

    @SerializedName("six")
    public int six;

    @SerializedName("hcountList")
    public List<Hcount> hcountList;

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

    public int getSix() {
        return six;
    }

    public List<Hcount> getHcountList() {
        return hcountList;
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

    public void setSix(int six) {
        this.six = six;
    }

    public void setHcountList(List<Hcount> hcountList) {
        this.hcountList = hcountList;
    }
}
