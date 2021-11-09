package com.example.frontend.entity;

import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Card {

    @SerializedName("userNo")
    private String userNo;

    @SerializedName("age")
    private String age;

    @SerializedName("birthdate")
    private String birthdate;

    @SerializedName("gender")
    private String gender;

    @SerializedName("mbti")
    private String mbti;

    @SerializedName("residence")
    private String residence;

    @SerializedName("univ")
    private String univ;

    @SerializedName("cardInfo")
    private List<Card> cardInfo;

    @SerializedName("friendsInfoList")
    private ArrayList<Card> friendsInfoList;

    @SerializedName("userName")
    private String userName;

    @SerializedName("telno1")
    private String telno1;

    @SerializedName("email")
    private String email;




    /*start getter, setter*/
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getUniv() {
        return univ;
    }

    public void setUniv(String univ) {
        this.univ = univ;
    }

    public List<Card> getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(List<Card> cardInfo) {
        this.cardInfo = cardInfo;
    }

    public ArrayList<Card> getFriendsInfoList() {
        return friendsInfoList;
    }

    public void setFriendsInfoList(ArrayList<Card> friendsInfoList) {
        this.friendsInfoList = friendsInfoList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelno1() {
        return telno1;
    }

    public void setTelno1(String telno1) {
        this.telno1 = telno1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*end getter, setter*/


    private View.OnClickListener requestBtnClickListener;

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    public Card(String userNo, String age, String birthdate, String gender, String mbti, String residence, String univ) {
        this.userNo = userNo;
        this.age = age;
        this.birthdate = birthdate;
        this.gender = gender;
        this.mbti = mbti;
        this.residence = residence;
        this.univ = univ;
        this.userName = userName;
        this.telno1 = telno1;
        this.email = email;
    }


    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Card> getTestingList() {
        ArrayList<Card> items = new ArrayList<>();
        items.add(new Card("$14", "$270", "W 79th St, NY, 10024", "W 139th St, NY, 10030", "ENTP", "TODAY", "05:10 PM"));
        items.add(new Card("$23", "$116", "W 36th St, NY, 10015", "W 114th St, NY, 10037", "ENTP", "TODAY", "11:10 AM"));
        items.add(new Card("$63", "$350", "W 36th St, NY, 10029", "56th Ave, NY, 10041", "ENTP", "TODAY", "07:11 PM"));
        items.add(new Card("$19", "$150", "12th Ave, NY, 10012", "W 57th St, NY, 10048", "ENTP", "TODAY", "4:15 AM"));
        items.add(new Card("$5", "$300", "56th Ave, NY, 10041", "W 36th St, NY, 10029", "ENTP", "TODAY", "06:15 PM"));
        return items;

    }
}
