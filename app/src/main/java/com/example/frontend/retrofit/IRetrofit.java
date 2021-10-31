package com.example.frontend.retrofit;

import com.example.frontend.entity.LoginRequest;
import com.example.frontend.entity.LoginResponse;
import com.example.frontend.entity.Member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofit {
    @POST("/member/test/")  // 모든 유저의 id값만 받아오는 메서드(id 중복체크를 위해)
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    @POST("/member/all/")  // 모든 유저의 id값만 받아오는 메서드(id 중복체크를 위해)
    Call<Member> getMember();

    @GET("/member/test/")  // 모든 유저의 id값만 받아오는 메서드(id 중복체크를 위해)
    Call<Member> getMemberInfo(@Query("loginId") String loginId);
}
