package com.example.frontend;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.frontend.entity.Card;
import com.example.frontend.entity.History;
import com.example.frontend.entity.HistoryRequest;
import com.example.frontend.entity.HistoryResponse;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
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
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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




    private HistoryAdapter adapter;
    private ListView theListView;
    private List<HistoryRequest> list;
    private ArrayList<HistoryRequest> items = new ArrayList<>();

    private IRetrofit iRetrofit;
    private Context context;

    private TextView totalText;
    private TextView titleSubName;

    // new add by juwoong
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private List<History> historyList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_history, container, false);

        // 지도 받음
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.hmap);


        theListView = rootView.findViewById(R.id.historyListView);
        list = new ArrayList<HistoryRequest>();

        totalText= (TextView) rootView.findViewById(R.id.totalText);
        titleSubName= (TextView) rootView.findViewById(R.id.title_subName);

        if (getArguments() != null) {
            String userNo = getArguments().getString("userNo"); // 프래그먼트1에서 받아온 값 넣기
            String seq = getArguments().getString("seq");
            String userName = getArguments().getString("userName");
            titleSubName.setText(userName);

            Log.d("도착!!!!!!!!!! 번들 시퀀스 확인",getArguments().getString("seq"));
            Log.d("도착!!!!!!!!!! 번들 유저내임 확인",getArguments().getString("userName"));
            //retrofit 생성
            iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
            Call<HistoryResponse> call = iRetrofit.getHistoryInfoList(seq);
            call.enqueue(new Callback<HistoryResponse>() {
                @Override
                public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                    Log.d("retrofit", "Data fetch success");

                    if (response.isSuccessful() && response.body() != null) {
                        List<HistoryRequest> historyInfoList = response.body().getHistoryInfoList();

                        int a = 0;
                        if (historyInfoList != null) {
                            for (HistoryRequest historyInfo : historyInfoList) {
                                a++;

                                list.add(historyInfo);
                            }
                            items.addAll(list);

                            context = container.getContext();

                            // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                            adapter = new HistoryAdapter(getContext(), items, (ShareActivity) getActivity());

                            Log.d("여기는 히스토리 프래그먼트 어댑터","생성 후 지점!!!!!!!!!!!");
                            // set elements to adapter
                            theListView.setAdapter(adapter);

                            /*총 만남 횟수 세팅*/
                            String str = "total "+getArguments().getString("total")+ " times";
                            SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 6, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            totalText.setText(ssb);



                            // set on click event listener to list view
/*                            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                    // toggle clicked cell state
                                    ((FoldingCell) view).toggle(false);
                                    // register in adapter that state for selected cell is toggled
                                    adapter.registerToggle(pos);
                                }
                            });*/

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("알림")
                                    .setMessage("목록 정보가 없습니다.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                        }


                    }
                }

                @Override
                public void onFailure(Call<HistoryResponse> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("BlankFragment2 Retrofit 알림")
                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();

                }
            });

            //       iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
            //       Call<HistoryResponse> call = iRetrofit.getHistoryInfoList(seq);

            Call<History> call2 = iRetrofit.selectHistoryList(userNo);
            call2.enqueue(new Callback<History>() {
                @Override
                public void onResponse(Call<History> call, Response<History> response) {


                    if(response.isSuccessful()&& response.body()!=null) {
                        historyList = response.body().getHistoryList();

                        //for (History history : historyList) {
                        //    System.out.println("========");
                        //     System.out.println(history.getLatitude());
                        //    System.out.println(history.getLongitude());
                        //}


                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                map = googleMap;

                                for( History history : historyList)
                                {
                                    //    System.out.println(history.getLatitude());
                                    //    System.out.println(history.getLongitude());
                                    //    System.out.println(history.getType());
                                    //    System.out.println(history.getRegdt());
                                    MarkerOptions makerOptions = new MarkerOptions();
                                    makerOptions
                                            .position(new LatLng(Double.parseDouble(history.getLatitude()),Double.parseDouble(history.getLongitude())))
                                            .title("활동 내용 타입 : " +history.getType())
                                            .snippet("만난 일자  : " +history.getRegdt());
                                    map.addMarker(makerOptions);
                                }

                                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(@NonNull Marker marker) {

                                        return false;
                                    }
                                });

                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.52487, 126.92723),10));






                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<History> call, Throwable t) {

                }
            });
        }


        return rootView;
    }
}