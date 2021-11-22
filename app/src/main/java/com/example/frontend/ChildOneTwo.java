package com.example.frontend;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frontend.entity.Hcount;
import com.example.frontend.entity.History;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildOneTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildOneTwo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildOneTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildOneTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildOneTwo newInstance(String param1, String param2) {
        ChildOneTwo fragment = new ChildOneTwo();
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
    private List<Hcount> hcountList;
    private Hcount hcount;

    private BarChart barChart;
    List<String> typeList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_one_two,container,false);
        barChart = rootView.findViewById(R.id.one_two_bar_chart);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                System.out.println("this is 12");
                userNo = s;
                System.out.println(userNo);
                System.out.println("======================");

                iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                Call<Hcount> call = iRetrofit.selectHourCount(userNo);
                call.enqueue(new Callback<Hcount>() {
                    @Override
                    public void onResponse(Call<Hcount> call, Response<Hcount> response) {

                        if(response.isSuccessful()&& response.body()!=null)
                        {
                            hcountList = response.body().getHcountList();
                            hcount =hcountList.get(0);

                            System.out.println("======SIBALLLLLLLLLL========");
                            System.out.println("==============");
                            System.out.println(hcount.getOne());
                            System.out.println("======SIBALLLLLLLLLL========");
                            System.out.println("==============");
                            System.out.println(hcount.getTwo());
                            System.out.println("======SIBALLLLLLLLLL========");
                            System.out.println("==============");
                            System.out.println(hcount.getThree());
                            System.out.println("==============");


                            // 여기서 부터 그래프



                            barChart.animateY(2000);
                            barChart.animateX(2000);


                            ArrayList<BarEntry> values = new ArrayList<>();

                            //데이터 삽입
                            values.add(new BarEntry(0,hcount.one ));
                            values.add(new BarEntry(1,hcount.two ));
                            values.add(new BarEntry(2,hcount.three ));
                            values.add(new BarEntry(3,hcount.four ));
                            values.add(new BarEntry(4,hcount.five ));
                            values.add(new BarEntry(5,hcount.six ));

                            //데이터 속성
                            BarDataSet set1;
                            set1= new BarDataSet(values, "시간대");

                            set1.setDrawIcons(false);
                            int startColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_light);
                            int startColor2 = ContextCompat.getColor(getContext(), android.R.color.holo_blue_light);
                            int startColor3 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_light);
                            int startColor4 = ContextCompat.getColor(getContext(), android.R.color.holo_green_light);
                            int startColor5 = ContextCompat.getColor(getContext(), android.R.color.holo_red_light);
                            int startColor6 = ContextCompat.getColor(getContext(), android.R.color.holo_purple);

                            set1.setColors(new int[] {startColor1,startColor2,startColor3,startColor4,startColor5,startColor6});




                            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                            dataSets.add(set1);

                            BarData data = new BarData(dataSets);
                            data.setValueTextSize(10f);
                            data.setBarWidth(0.9f);


                            barChart.setData(data);

                            // 배경 설정
                            //barChart.getAxisLeft().setDrawGridLines(false);
                            barChart.getXAxis().setDrawGridLines(false);

                            //설명
                            barChart.getDescription().setEnabled(false);
                            //Description description = new Description();
                            //description.setText("시간대별 만남횟수");
                            //description.setTextSize(18);
                            //description.setTextColor(Color.BLACK);

                            barChart.getLegend().setEnabled(false);


                            //Y축 설정  (왼, 오)축
                            barChart.getAxisRight().setEnabled(false);
                            barChart.getAxisLeft().setTextColor(Color.BLACK);

                            //X축 설정
                            typeList = new ArrayList<>(Arrays.asList("0-4시", "4-8시", "8-12시","12-16시","16-20시", "20-24시"));
                            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(typeList));
                            barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                            //barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                            //barChart.getXAxis().setDrawGridLines(false);
                            //barChart.getXAxis().setDrawGridLines(false);


                        }

                    }

                    @Override
                    public void onFailure(Call<Hcount> call, Throwable t) {

                    }
                });


            }
        });

        return rootView;




    }
}