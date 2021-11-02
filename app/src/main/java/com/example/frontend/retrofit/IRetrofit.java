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
    /*Member*/

    // 로그인 (Param: loginRequest)
    @POST("/member/login/")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    // 회원 정보 조회 (Param: loginId)
    @POST("/member/")
    Call<Member> getMemberInfo(@Body String loginId);

    // 회원 전체 리스트
    @GET("/member/all/")
    Call<Member> getMember();

    
    
    
    /*Card*/
    @GET("/card/")
    Call<Card> getCardInfo(@Query("userNo") String userNo);


}
