package com.example.frontend;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildOne extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildOne.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildOne newInstance(String param1, String param2) {
        ChildOne fragment = new ChildOne();
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







    private ChildOneOne childOneOne_fragment;
    private ChildOneTwo childOneTwo_fragment;
    private ChildOneThree childOneThree_fragment;

    private ViewPager2 viewPager;
    private ChildOnePagerAdapter pagerAdapter;
    private TabLayout child_ond_tabs;

    private String[] titles = new String[]{"최근추세", "선호시간", "MBTI"};




    private ShareViewModel sharedViewModel;
    private String userNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_one,container,false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                System.out.println("this is ChildOne");
                userNo = s;
                System.out.println(userNo);
                System.out.println("======================");
            }
        });


        childOneOne_fragment=  new ChildOneOne();
        childOneTwo_fragment= new ChildOneTwo();
        childOneThree_fragment= new ChildOneThree();


        viewPager = (ViewPager2)rootView.findViewById(R.id.child_one_container);
        pagerAdapter = new ChildOnePagerAdapter(getChildFragmentManager(), getLifecycle());

        pagerAdapter.addFragment(childOneOne_fragment);
        pagerAdapter.addFragment(childOneTwo_fragment);
        pagerAdapter.addFragment(childOneThree_fragment);
        viewPager.setAdapter(pagerAdapter);
        //viewPager.setUserInputEnabled(false);

        viewPager.setCurrentItem(0);

        child_ond_tabs = rootView.findViewById(R.id.child_one_tabs);


        new TabLayoutMediator(child_ond_tabs,viewPager,(tab, position) -> tab.setText(titles[position])).attach();


        return rootView;

    }
}