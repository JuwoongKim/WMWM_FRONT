package com.example.frontend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.frontend.entity.Card;
import com.example.frontend.entity.LoginResponse;
import com.example.frontend.entity.Member;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
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

    private CircleImageView image1;
    private ImageView image_back;

    private TextView contentUserNameTop;
    private TextView contentUserName;
    private TextView contentTelno1;
    private TextView contentEmail;
    private TextView contentWorkType;
    private TextView contentResidence;
    private TextView contentMbti;
    private TextView contentUniv;
    private FloatingActionButton editBtn;

    private IRetrofit iRetrofit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank3, container, false);


        image1 = (CircleImageView) view.findViewById(R.id.image1);
        contentUserNameTop = (TextView) view.findViewById(R.id.content_user_name_top);
        contentUserName= (TextView) view.findViewById(R.id.content_user_name);
        contentTelno1= (TextView) view.findViewById(R.id.content_telno1);
        contentEmail= (TextView) view.findViewById(R.id.content_email);
        contentWorkType= (TextView) view.findViewById(R.id.content_work_type);
        contentResidence= (TextView) view.findViewById(R.id.content_residence);
        contentMbti= (TextView) view.findViewById(R.id.content_mbti);
        contentUniv= (TextView) view.findViewById(R.id.content_major);
        editBtn = (FloatingActionButton) view.findViewById(R.id.edit_btn);

        if (getArguments() != null)
        {
            String userNo = getArguments().getString("userNo"); // 프래그먼트1에서 받아온 값 넣기

            //retrofit 생성
            iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
            Call<Card> call = iRetrofit.selectMyInfo(userNo);
            call.enqueue(new Callback<Card>() {
                @Override
                public void onResponse(Call<Card> call, Response<Card> response) {
                    Log.d("retrofit", "Data fetch success");

                    if(response.isSuccessful() && response.body() != null){
                        //response.body()를 result에 저장
                        Card cardInfo = response.body().getCardInfo().get(0);

                        //받은 코드 저장
                        String userName = cardInfo.getUserName().toString();
                        String telno = cardInfo.getTelno1().toString();
                        String email = cardInfo.getEmail().toString();
                        String workType = cardInfo.getWorkType().toString();
                        //String birthdate = cardInfo.getBirthdate().toString();
                        //String gender = cardInfo.getGender().toString();
                        String residence = cardInfo.getResidence().toString();
                        String mbti = cardInfo.getMbti().toString();
                        String univ = cardInfo.getUniv().toString();

                        contentUserNameTop.setText(userName);
                        contentUserName.setText(userName);
                        contentTelno1.setText(telno);
                        contentEmail.setText(email);
                        contentWorkType.setText(workType);
                        contentResidence.setText(residence);
                        contentMbti.setText(mbti);
                        contentUniv.setText(univ);

                        if(cardInfo.getFileId()!= null){
                            String fileId = cardInfo.getFileId().toString();
                            if(!"".equals(fileId)){
                                setFile(fileId);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Card> call, Throwable t) {
                    Log.d("info::::::", t.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("알림")
                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            });

        }

        //수정 버튼
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Bundle bundle_my = new Bundle(); // 번들을 통해 값 전달
                bundle_my.putString("userNo", getArguments().getString("userNo"));//번들에 넘길 값 저장
                MyEditFragment myEditFragment = new MyEditFragment();
                myEditFragment.setArguments(bundle_my);
                ((ShareActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, myEditFragment).commit();
            }
        });

        return view;
    }

    /*set image file*/
    private void setFile(String fileId) {

        iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
        Call<ResponseBody> call = iRetrofit.fileDownload(fileId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("file", "Data fetch success");

                if (response.isSuccessful()) {
                    //response.body()를 result에 저장

                    new Thread() {
                        public void run() {
                            InputStream inputStream = response.body().byteStream();
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                            Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    image1.setImageBitmap(bitmap);
                                }
                            });

                        }
                    }.start();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("info::::::", t.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }

        });
    }
}