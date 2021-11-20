package com.example.frontend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class LoadActivity extends AppCompatActivity{

    private RetrofitClient retrofitClient;
    private IRetrofit iRetrofit;

    private TextInputEditText search_id_text;
    private TextInputEditText search_pw_text;
    private Button btn_search;

    private ImageView image_crown;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        image_crown = findViewById(R.id.image_crown);
        Glide.with(this).load(R.drawable.crown).into(image_crown);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadActivity.this, MainActivity.class); //화면 전환
                startActivity(intent);
                finish();
            }
        }, 5000); //딜레이 타임 조절



    }



}

