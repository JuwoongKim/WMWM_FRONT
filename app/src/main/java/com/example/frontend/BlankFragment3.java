package com.example.frontend;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frontend.entity.LoginResponse;
import com.example.frontend.entity.Member;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment3 newInstance(String param1, String param2) {
        BlankFragment3 fragment = new BlankFragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView tv_id;
    private TextView tv_email;
    private IRetrofit iRetrofit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank3, container, false);

        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_email = (TextView) view.findViewById(R.id.tv_email);

        if (getArguments() != null)
        {
            Log.d("ddddddddddddddddd","Ddddddddddddddddddddd");
            String loginId = getArguments().getString("loginId"); // 프래그먼트1에서 받아온 값 넣기
            Log.d("loginId:",loginId);


            //retrofit 생성
            iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
            Call<Member> call = iRetrofit.getMemberInfo(loginId);
            call.enqueue(new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {
                    Log.d("retrofit", "Data fetch success");

                    if(response.isSuccessful() && response.body() != null){
                        //response.body()를 result에 저장
                        Member result = response.body();

                        Member memberInfo = result.getMemberInfo();
                        //받은 코드 저장
                        String email = memberInfo.getEmail().toString();
                        String pwd = memberInfo.getPwd().toString();

                        Log.d("email:", email);
                        Log.d("pwd:", pwd);

                        tv_id.setText(loginId);
                        tv_email.setText(email);

                    }
                }

                @Override
                public void onFailure(Call<Member> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("알림")
                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();

                }
            });


        }

        return view;
    }
}