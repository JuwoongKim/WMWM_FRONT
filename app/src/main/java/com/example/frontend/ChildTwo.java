package com.example.frontend;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frontend.entity.Card;
import com.example.frontend.entity.Tcount;
import com.example.frontend.entity.Test;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildTwo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildTwo newInstance(String param1, String param2) {
        ChildTwo fragment = new ChildTwo();
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

    private IRetrofit retrofit;
    private List<Card> cardList;

    // add new by juwoong
    private ChildTwoOne childTwoOne_fragment;
    private ChildTwoTwo childTwoTwo_fragment;
    private ChildTwoThree childTwoThree_fragment;
    private ChildTwoFour childTwoFour_fragment;
    private ChildTwoFive childTwoFive_fragment;


    private ViewPager2 viewPager;
    private ChildTwoPageAdapter pagerAdapter;
    private TabLayout child_two_tabs;

    private String[] titles = new String[]{"1개월", "3개월", "6개월", "1년", "전체"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_two,container,false);

        viewPager =rootView.findViewById(R.id.child_two_container);
        pagerAdapter = new ChildTwoPageAdapter(getChildFragmentManager(),getLifecycle());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        childTwoOne_fragment=  new ChildTwoOne();
        childTwoTwo_fragment= new ChildTwoTwo();
        childTwoThree_fragment=  new ChildTwoThree();
        childTwoFour_fragment= new ChildTwoFour();
        childTwoFive_fragment=  new ChildTwoFive();

        pagerAdapter.addFragment(childTwoOne_fragment);
        pagerAdapter.addFragment(childTwoTwo_fragment);
        pagerAdapter.addFragment(childTwoThree_fragment);
        pagerAdapter.addFragment(childTwoFour_fragment);
        pagerAdapter.addFragment(childTwoFive_fragment);

        child_two_tabs = rootView.findViewById(R.id.child_two_tabs);

        new TabLayoutMediator(child_two_tabs,viewPager,(tab, position) -> tab.setText(titles[position])).attach();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("childTwo", "shareViewModel On Changed!");
                userNo = s;
                System.out.println("userNo:"+userNo);
            }
        });

        return rootView;
    }
}