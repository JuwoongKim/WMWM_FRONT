package com.example.frontend.retrofit;

import com.example.frontend.entity.Card;
import com.example.frontend.entity.LoginRequest;
import com.example.frontend.entity.LoginResponse;
import com.example.frontend.entity.Member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofit {
    /*Entity: Login*/
    @POST("/login/")    // 로그인 (Param: loginRequest)
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);


    /*Entity: Member*/
    @GET("/member/")    // 회원 - 상세 조회 (Param: loginId)
    Call<Member> getMemberInfo(@Query("loginId") String loginId);



    /*Entity: Card*/
    @GET("/card/my")      // 내 정보 - 상세 조회 (Param: userNo)
    Call<Card> selectMyInfo(@Query("userNo") String userNo);

    @GET("/friends/all")      // 지인 정보 - 목록 조회 (Param: userNo)
    Call<Card> selectFriendsInfoList(@Query("userNo") String userNo);



}
