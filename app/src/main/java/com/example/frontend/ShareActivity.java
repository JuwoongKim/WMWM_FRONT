package com.example.frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.frontend.entity.Member;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

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
    private ShareViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        getLocationPermisson();
        model = new ViewModelProvider(this).get(ShareViewModel.class);
        System.out.println("niceeeeeee");
        model.setLiveData("helloworld");
        System.out.println("gooooood");


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

        Bundle bundle1 = new Bundle(); // 번들을 통해 값 전달
        bundle1.putString("loginId",loginId);//번들에 넘길 값 저장
        BlankFragment blankFragment = new BlankFragment();
        blankFragment.setArguments(bundle1);

        getSupportFragmentManager().beginTransaction().add(R.id.home_ly, new BlankFragment()).commit(); //FrameLayout에 fragment.xml 띄우기

        model.getLiveData().observe(this, new Observer<String>(){

            @Override
            public void onChanged(String s) {
                System.out.println("==========");
                System.out.println("==========");
                System.out.println(s);
            }
        });

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

                    case R.id.page_statistics:
                        Bundle bundle_static = new Bundle(); // 번들을 통해 값 전달
                        bundle_static.putString("userNo", userNoSub);//번들에 넘길 값 저장
                        BlankFragment4 blankFragment4 = new BlankFragment4();
                        blankFragment4.setArguments(bundle_static);
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, blankFragment4).commit();
                        break;
                }

                return true;
            }
        });

    }

    public void getLocationPermisson(){
        AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permisson) { }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permission) { }
                })
                .start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }



}