package com.example.frontend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.frontend.entity.LoginRequest;
import com.example.frontend.entity.LoginResponse;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private RetrofitClient retrofitClient;
    private IRetrofit iRetrofit;

    private TextInputEditText search_id_text;
    private TextInputEditText search_pw_text;
    private Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_id_text = findViewById(R.id.search_id_text);
        search_pw_text = findViewById(R.id.search_pw_text);
        btn_search = (Button)findViewById(R.id.btn_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginId = search_id_text.getText().toString();
                String pwd = search_pw_text.getText().toString();
                //hideKeyboard();

                //로그인 정보 미입력 시
                if (loginId.trim().length() == 0 || pwd.trim().length() == 0 || loginId == null || pwd == null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("알림")
                            .setMessage("로그인 정보를 입력바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                } else {
                    //로그인 통신
                    LoginResponse();
                }

/*                IRetrofit iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                //Call<Map<String, Object>> call = iRetrofit.getLogin(search_pw_text.getText().toString());
                Call<String> call = iRetrofit.getLoginResponse();
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("ddddddddddddddddddd","ss");
                        if(response.isSuccessful() && response.body() != null){
                            String getted_pwd = response.body().toString();
                            Log.e("getPwd():" ,"suuccess:"+getted_pwd);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("getPwd():" ,"error:"+t.getMessage());

                    }
                });*/
            }
        }) ;
    }


    public void LoginResponse(){
        String loginId = search_id_text.getText().toString().trim();
        String pwd = search_pw_text.getText().toString().trim();

        //loginRequest에 사용자가 입력한 id와 pw를 저장
        LoginRequest loginRequest = new LoginRequest(loginId, pwd);

        //retrofit 생성
        iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
        Call<LoginResponse> call = iRetrofit.getLoginResponse(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("retrofit", "Data fetch success");

                if(response.isSuccessful() && response.body() != null){
                    //response.body()를 result에 저장
                    LoginResponse result = response.body();

                    //받은 코드 저장
                    String resultCode = result.getResultCode();

                    String success = "200"; //로그인 성공
                    String errorId = "300"; //계정 불일치

                    if(resultCode.equals(success)){
                        String loginId = search_id_text.getText().toString();
                        Toast.makeText(MainActivity.this, loginId+"님 환영합니다.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                        intent.putExtra("loginId", loginId);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("알림")
                                .setMessage("일치하는 계정 정보가 없습니다.\n 고객센터에 문의바랍니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }


                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("ddddddddddddddddd",t.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();

            }
        });
    }

    //키보드 숨기기
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search_id_text.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(search_pw_text.getWindowToken(), 0);
    }

}


/*
public class MainActivity extends AppCompatActivity {

    private TextInputEditText search_pw_text;
    private Button btn_search;
    private FrameLayout frame_search_button;
    private ScrollView main_scrollview;
    private ProgressBar btn_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("tag", "로그인 화면 진입");

        search_pw_text = (TextInputEditText) findViewById(R.id.search_pw_text);
        btn_search = (Button) findViewById(R.id.btn_search);
        frame_search_button = (FrameLayout)findViewById(R.id.frame_search_button);


        search_pw_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }



            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                main_scrollview = (ScrollView) findViewById(R.id.main_scrollview);
                if(charSequence.toString().length() > 0){
                    frame_search_button.setVisibility(View.VISIBLE);

                    main_scrollview.scrollTo(0,200);
                }else{
                    frame_search_button.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearchButtonUi();
            }
        }) ;

    }

    private void handleSearchButtonUi(){
        btn_progress = (ProgressBar) findViewById(R.id.btn_progress);
        btn_progress.setVisibility(View.VISIBLE);

        btn_search.setText("");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                btn_progress.setVisibility(View.INVISIBLE);
                btn_search.setText("검색");

            }

        }, 1500);

    }

}*/
