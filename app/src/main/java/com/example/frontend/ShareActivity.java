package com.example.frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.frontend.entity.Member;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ShareActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation; //바텀 네비게이션


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        Intent intent = getIntent(); /*데이터 수신*/
        Log.d("intent:", intent.getExtras().getString("loginId"));
        String loginId = intent.getExtras().getString("loginId");

        getSupportFragmentManager().beginTransaction().add(R.id.home_ly, new BlankFragment()).commit(); //FrameLayout에 fragment.xml 띄우기


        //바텀 네비게이션뷰 안의 아이템 설정
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.page_share:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new BlankFragment()).commit();
                        break;
                    case R.id.page_all:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new BlankFragment2()).commit();
                        break;
                    case R.id.page_my:
                        Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                        bundle.putString("loginId",loginId);//번들에 넘길 값 저장
                        BlankFragment3 blankFragment3 = new BlankFragment3();
                        blankFragment3.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, blankFragment3).commit();
                        break;

                }
                return true;
            }
        });

    }
}