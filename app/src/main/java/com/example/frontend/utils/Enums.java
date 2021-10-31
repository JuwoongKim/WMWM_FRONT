package com.example.frontend.utils;

public enum Enums {
    TAG("로그"),
    ID("아이디"),
    PW("비밀번호");

    private final String value;

    Enums(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}


