package com.example.frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.frontend.entity.Member;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation; //바텀 네비게이션

    private IRetrofit iRetrofit;
    String userNoSub ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        Intent intent = getIntent(); /*데이터 수신*/
        Log.d("In ShareActivity[loginId]:", intent.getExtras().getString("loginId"));
        String loginId = intent.getExtras().getString("loginId");

        //retrofit 생성
        iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
        Call<Member> call = iRetrofit.getMemberInfo(loginId);
        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                Log.d("getUserNo", "Data fetch success");

                if(response.isSuccessful() && response.body() != null){
                    //response.body()를 result에 저장
                    Member result = response.body();
                    List<Member> memberInfo = result.getMemberInfo();
                    userNoSub = memberInfo.get(0).getUserNo();
                }
            }
            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                Log.d("info::::::", t.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(ShareActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.home_ly, new BlankFragment()).commit(); //FrameLayout에 fragment.xml 띄우기

        //바텀 네비게이션뷰 안의 아이템 설정
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.page_share:
                        Bundle bundle1 = new Bundle(); // 번들을 통해 값 전달
                        bundle1.putString("loginId",loginId);//번들에 넘길 값 저장
                        BlankFragment blankFragment = new BlankFragment();
                        blankFragment.setArguments(bundle1);

                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, blankFragment).commit();
                        break;

                    case R.id.page_all:
                        Bundle bundle_all = new Bundle(); // 번들을 통해 값 전달
                        bundle_all.putString("userNo", userNoSub);//번들에 넘길 값 저장
                        BlankFragment2 blankFragment2 = new BlankFragment2();
                        blankFragment2.setArguments(bundle_all);

                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, blankFragment2).commit();
                        break;

                    case R.id.page_my:
                        Bundle bundle_my = new Bundle(); // 번들을 통해 값 전달
                        bundle_my.putString("userNo", userNoSub);//번들에 넘길 값 저장
                        BlankFragment3 blankFragment3 = new BlankFragment3();
                        blankFragment3.setArguments(bundle_my);
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, blankFragment3).commit();
                        break;
                }

                return true;
            }
        });

    }




}