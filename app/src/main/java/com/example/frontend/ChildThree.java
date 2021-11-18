package com.example.frontend;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.frontend.entity.History;
import com.example.frontend.entity.HistoryRequest;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildThree extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildThree.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildThree newInstance(String param1, String param2) {
        ChildThree fragment = new ChildThree();
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
    private List<History> historyList;

    private IRetrofit iRetrofit;

    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_three,container,false);


        // 지도 받음
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.gmap);




        // model View 를 통해서  userno를 받음
        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                System.out.println("this is ChildThree");
                userNo = s;
                System.out.println(userNo);
                System.out.println("======================");





                // 값을 전달받은 후, 데이터 받아오기
                System.out.println("=====sssssssssssssssss=================");
                if(userNo!=null)
                {
                    System.out.println("lalaaaaaaaaaaaaaaaaaaa");
                    iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                    Call<History> call = iRetrofit.selectHistoryList(userNo);
                    call.enqueue(new Callback<History>() {
                        @Override
                        public void onResponse(Call<History> call, Response<History>response) {

                            if( response.isSuccessful() && response.body()!=null)
                            {
                                historyList = response.body().getHistoryList();

                                for( History history : historyList)
                                {
                                    // System.out.println("========");
                                    //System.out.println(history.getLatitude());
                                    //System.out.println(history.getLongitude());
                                }


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
                            System.out.println("lalall");
                        }
                    });

                }


















            }
        });











        return rootView;
    }
}