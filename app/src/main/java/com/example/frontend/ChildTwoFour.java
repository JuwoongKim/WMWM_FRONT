package com.example.frontend;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.frontend.entity.Rank;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildTwoOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildTwoFour extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildTwoFour() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildTwoOne.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildTwoFour newInstance(String param1, String param2) {
        ChildTwoFour fragment = new ChildTwoFour();
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
    private IRetrofit iRetrofit;

    private String userNo;
    private String period;
    private List<Rank> rankList;
    private ArrayList<Rank> items = new ArrayList<>();
    private Context context;
    private ChildTwoAdapter adapter;
    private ListView theListView;
    private int rank=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_two_four,container,false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                userNo = s;
                period = "12";   //1months before
                rankList = new ArrayList<Rank>();

                iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                Call<Rank> call = iRetrofit.getPeriodRankList(userNo, period);
                call.enqueue(new Callback<Rank>() {
                    @Override
                    public void onResponse(Call<Rank> call, Response<Rank> response) {
                        Log.d("Child 1 months retrofit in","!!!!!!");
                        if( response.isSuccessful() && response.body()!=null)
                        {
                            List<Rank> periodRankList = response.body().getPeriodRankList();

                            if(periodRankList != null && periodRankList.size() != 0){
                                for(Rank rankInfo : periodRankList){
                                    rank++;
                                    rankInfo.setRank(String.valueOf(rank));
                                    Log.d("rank", rankInfo.toString());
                                    rankList.add(rankInfo);
                                }
                                items.addAll(rankList);
                            }
                            //context = container.getContext();

                            adapter = new ChildTwoAdapter(getContext(), items, (ShareActivity) getActivity());

                            theListView = rootView.findViewById(R.id.rankListView);
                            theListView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<Rank> call, Throwable t) {
                        Log.d("ChildTwoOne", t.toString());
                    }
                });
            }
        });

        return rootView;
    }
}