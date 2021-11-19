package com.example.frontend.retrofit;

import com.example.frontend.entity.Card;
import com.example.frontend.entity.Hcount;
import com.example.frontend.entity.History;
import com.example.frontend.entity.HistoryRequest;
import com.example.frontend.entity.HistoryResponse;
import com.example.frontend.entity.LoginRequest;
import com.example.frontend.entity.LoginResponse;
import com.example.frontend.entity.Member;
import com.example.frontend.entity.Rank;
import com.example.frontend.entity.Tcount;
import com.example.frontend.entity.Wcount;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface IRetrofit {
    /*Entity: Login*/
    @POST("/login/")    // 로그인 (Param: loginRequest)
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    /*Entity: History*/
    @POST("/history/")    // 로그인 (Param: loginRequest)
    Call<HistoryResponse> insertHistoryInfo(@Body HistoryRequest historyRequest);

    @GET("/history/detail")    // 히스토리 - 상세 조회 (Param: loginId)
    Call<HistoryResponse> getHistoryInfoList(@Query("seq") String seq);


    /*Entity: Member*/
    @GET("/member/")    // 회원 - 상세 조회 (Param: loginId)
    Call<Member> getMemberInfo(@Query("loginId") String loginId);

    /*Entity: Card*/
    @GET("/card/my")      // 내 정보 - 상세 조회 (Param: userNo)
    Call<Card> selectMyInfo(@Query("userNo") String userNo);

    @GET("/friends/all")      // 지인 정보 - 목록 조회 (Param: userNo)
    Call<Card> selectFriendsInfoList(@Query("userNo") String userNo);

    @POST("/card/my")      // 내 정보 - 업데이트 (Param: Card)
    Call<Card> updateMyInfo(@Body Card card);

    /*Entity: File*/
    @Multipart
    @POST("/card/fileUpload/")
    Call<Card> fileUpload(@Part MultipartBody.Part uploadFile, @PartMap HashMap<String, RequestBody> userNo);

    @GET("/card/fileDownload/")
    @Streaming
    Call<ResponseBody> fileDownload(@Query("fileId") String fileId);


    // 주웅이 새로 추가한것

    @GET("/history/location")      // 지인 정보 - 목록 조회 (Param: userNo)
    Call<History> selectHistoryList(@Query("userNo") String userNo);

    @GET("/history/tcount")
    Call<Tcount> selectTypeCount(@Query("userNo") String userNo);

    @GET("/history/hcount")
    Call<Hcount> selectHourCount(@Query("userNo") String userNo);

    @GET("/history/wcount")
    Call<Wcount> selectWeekCount(@Query("userNo") String userNo);

    @GET("/card/mbti")
    Call<Card> selectMbti(@Query("userNo") String userNo);

    //@GET("/history/best")
    //Call<Card> selectBestFriends(@Query("userNo") String userNo);




    //@GET("/history/test")
    //Call<Test>  firstTest(@Query("para") String param);


    @GET("/history/rank")      // 히스토리 - 기간별 만남 순위
    Call<Rank> getPeriodRankList(@Query("userNo") String userNo, @Query("period") String period);





}
