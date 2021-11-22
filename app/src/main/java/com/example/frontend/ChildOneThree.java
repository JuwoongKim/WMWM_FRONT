package com.example.frontend;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.frontend.entity.Card;
import com.example.frontend.entity.Hcount;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildOneThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildOneThree extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildOneThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildOneThree.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildOneThree newInstance(String param1, String param2) {
        ChildOneThree fragment = new ChildOneThree();
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




    private ShareViewModel sharedViewModel;
    private String userNo;
    private IRetrofit iRetrofit;
    private List<Card> cardList;
    private String mbtiCount;

    // new add by juwoong
    private TextView mbtiName;
    private TextView mbtiName2;
    private AlphaAnimation anim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_one_three,container,false);

        mbtiName = rootView.findViewById(R.id.onethree2);
        mbtiName2 = rootView.findViewById(R.id.onethree5);

        anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(100); anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);


        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                System.out.println("this is 13");
                userNo = s;
                System.out.println(userNo);
                System.out.println("======================");

                iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                Call<Card> call = iRetrofit.selectMbti(userNo);
                call.enqueue(new Callback<Card>() {
                    @Override
                    public void onResponse(Call<Card> call, Response<Card> response) {

                        if(response.isSuccessful()&& response.body()!=null){

                            cardList = response.body().getCardInfo();

                            if(cardList.size() != 0 && cardList != null){
                                for( Card card : cardList)
                                {
                                    System.out.println(card.getMbti());
                                }

                                mbtiCount = Integer.toString(cardList.size());

                                System.out.println("당신과 같은  MBTI 수는 ");
                                System.out.println(mbtiCount);
                                System.out.println("=========");

                                // if(mbtiCount=0){

                                //}

                                String str = "당신의 MBTI " + cardList.get(0).getMbti()+"와 같은";
                                SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                                ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ffbf12")), 8, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                mbtiName.setText(ssb);
                                mbtiName2.setText(mbtiCount);
                                mbtiName.setAnimation(anim);
                                mbtiName2.setAnimation(anim);
                            }else{
                                mbtiName.setText("당신의 MBTI와 같은");
                                mbtiName2.setText("0");
                            }



                        }
                    }

                    @Override
                    public void onFailure(Call<Card> call, Throwable t) {


                    }
                });




            }
        });




        return rootView;








    }
}