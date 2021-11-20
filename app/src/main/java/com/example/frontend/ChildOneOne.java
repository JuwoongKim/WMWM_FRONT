package com.example.frontend;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frontend.entity.Tcount;
import com.example.frontend.entity.Wcount;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildOneOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildOneOne extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildOneOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildOneOne.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildOneOne newInstance(String param1, String param2) {
        ChildOneOne fragment = new ChildOneOne();
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
    private List<Wcount> wcountList;
    private Wcount wcount;

    private LineChart lineChart;
    List<String> typeList;

    private int checkbit =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_one_one,container,false);
        lineChart = rootView.findViewById(R.id.one_one_linechart);


        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                System.out.println("this is 11");
                userNo = s;
                System.out.println(userNo);
                System.out.println("======================");

                iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                Call<Wcount> call = iRetrofit.selectWeekCount(userNo);
                call.enqueue(new Callback<Wcount>() {
                    @Override
                    public void onResponse(Call<Wcount> call, Response<Wcount> response) {

                        if( response.isSuccessful() && response.body()!=null)
                        {
                            wcountList = response.body().getWcountList();
                            wcount = wcountList.get(0);

                            for (Wcount wcount : wcountList){
                                System.out.println("=======wakcwakc===========");
                                System.out.println(wcount.getOne());
                                System.out.println(wcount.getTwo());
                                System.out.println(wcount.getThree());
                                System.out.println("==================");
                            }



                            ArrayList<Entry> values = new ArrayList<>();

                            if(wcount.getFive()!=0)  { values.add(new Entry(0, wcount.getFive()/2)); }
                            else{values.add(new Entry(0, wcount.getFive()));}

                            if(wcount.getFour()!=0)  { values.add(new Entry(1, wcount.getFour()/2)); }
                            else{values.add(new Entry(1, wcount.getFour()));}

                            if(wcount.getThree()!=0)  { values.add(new Entry(2, wcount.getThree()/2)); }
                            else{values.add(new Entry(2, wcount.getThree()));}

                            if(wcount.getTwo()!=0)  { values.add(new Entry(3, wcount.getTwo()/2)); }
                            else{values.add(new Entry(3, wcount.getTwo()));}

                            if(wcount.getOne()!=0)  { values.add(new Entry(4, wcount.getOne()/2)); }
                            else{values.add(new Entry(4, wcount.getOne()));}


                            LineDataSet set1;
                            set1 = new LineDataSet(values, "DataSet 1 ");

                            set1.setLineWidth(4);
                            set1.setCircleRadius(6);
                            set1.setCircleColor(Color.parseColor("#FFA1B4DC"));

                            set1.setColor(Color.parseColor("#FFA1B4DC"));
                            set1.setDrawCircleHole(true);
                            set1.setDrawCircles(true);
                            set1.setDrawHorizontalHighlightIndicator(false);
                            set1.setDrawHighlightIndicators(false);
                            set1.setDrawValues(false);


                            //set1.setDrawCubic(true); //선 둥글게 만들기
                            set1.setDrawFilled(true); //그래프 밑부분 색칠*/
                            //set1.setFillColor(Color.GRAY);
                            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
                            set1.setFillDrawable(drawable);
                            set1.setHighLightColor(Color.rgb(244, 117, 117));
                            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(set1);

                            LineData data = new LineData(dataSets);

                            set1.setCircleColor(Color.BLACK);
                            set1.setCircleColor(Color.BLACK);

                            lineChart.setData(data);
                            checkbit =1;

                            // 설정
                            lineChart.getDescription().setEnabled(false);
                            lineChart.getLegend().setEnabled(false);
                            lineChart.getAxisRight().setEnabled(false);

                            // y축설정
                            //lineChart.getAxisLeft().setTypeface(tfLight);
                            //lineChart.getAxisLeft().setLabelCount(6, false);
                            lineChart.getAxisLeft().setTextColor(Color.BLACK);
                            //lineChart.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
                            //lineChart.getAxisLeft().setDrawGridLines(false);
                            lineChart.getAxisLeft().setAxisLineColor(Color.WHITE);




                            //x축설정
                            typeList = new ArrayList<>(Arrays.asList("5주전","4주전","3주전","2주전","1주전"));
                            lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(typeList));
                            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                            lineChart.getXAxis().setDrawGridLines(false);
                            lineChart.getXAxis().setLabelCount(5, true);




                            lineChart.animateY(1500);
                            lineChart.animateX(1500);


                        }




                    }

                    @Override
                    public void onFailure(Call<Wcount> call, Throwable t) {

                    }
                });



            }
        });

/*
        // 여기서부터 그래프 시작
        if(checkbit !=0) {
            ArrayList<Entry> values = new ArrayList<>();

            values.add(new Entry(0, wcount.getFive()));
            values.add(new Entry(1, wcount.getFour()));
            values.add(new Entry(2, wcount.getThree()));
            values.add(new Entry(3, wcount.getTwo()));
            values.add(new Entry(4, wcount.getOne()));

            LineDataSet set1;
            set1 = new LineDataSet(values, "DataSet 1 ");

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);

            set1.setCircleColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            lineChart.setData(data);
        }
        //그래프 끝

*/

        return rootView;





    }
}