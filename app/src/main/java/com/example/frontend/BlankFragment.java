package com.example.frontend;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.comix.overwatch.HiveProgressView;
import com.example.frontend.entity.HistoryRequest;
import com.example.frontend.entity.HistoryResponse;
import com.example.frontend.entity.LoginResponse;
import com.example.frontend.entity.Member;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate.Status;
import com.google.android.gms.nearby.connection.Strategy;

import org.w3c.dom.Text;

import java.util.List;

import io.supercharge.funnyloader.FunnyLoader;
import nl.dionsegijn.konfetti.models.Shape;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btn_share;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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

//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

    private static final String[] REQUIRED_PERMISSIONS =
            new String[] {
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };

    static final int REQUEST_CODE_REQUIRED_PERMISSIONS = 1;


    //=============
    private static final Strategy STRATEGY = Strategy.P2P_STAR;
    private ConnectionsClient connectionsClient;


    //============= variable
    private String myName;

    private String friendEndpointId;
    private String friendName;


    private View myView;



    String myloginId;
    String friendLoginId;
    String activity_type;
    Double latitude;
    Double longtitude;

    private IRetrofit iRetrofit;
    String userName ="";


    HistoryRequest historyRequest;


    // Callbacks for receiving payloads
    private final PayloadCallback payloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(String endpointId, Payload payload) {
                    System.out.println("onPayloadReceived is finished");
                }

                @Override
                public void onPayloadTransferUpdate(String endpointId, PayloadTransferUpdate update) {
                    if (update.getStatus() == Status.SUCCESS){
                        System.out.println("onPayloadTransferUpdate is finished");
                    }
                }
            };


    // Callbacks for connections to other devices
    private final ConnectionLifecycleCallback connectionLifecycleCallback =
            new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {

                    System.out.println("=============================");
                    System.out.println(myloginId);
                    System.out.println("Succeed ConnectionInitiated");

                    connectionsClient.acceptConnection(endpointId, payloadCallback);
                    friendLoginId = connectionInfo.getEndpointName();


                    //retrofit 생성
                    /*
                    iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                    Call<Member> call = iRetrofit.getMemberInfo(friendName);
                    Log.d("onConnectionInitiatedfriendName", friendLoginId);
                    call.enqueue(new Callback<Member>() {
                        @Override
                        public void onResponse(Call<Member> call, Response<Member> response) {
                            Log.d("onConnectionInitiated", "Data fetch success");
                            if(response.isSuccessful() && response.body() != null){
                                //response.body()를 result에 저장
                                Member result = response.body();
                                List<Member> memberInfo = result.getMemberInfo();
                                userName = memberInfo.get(0).getUserName();
                            }
                        }
                        @Override
                        public void onFailure(Call<Member> call, Throwable t) {
                            Log.d("info::::::", t.toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("알림")
                                    .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                        }
                    });
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Check")
                            .setMessage("Do you want to connect with "+userName+"?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Bundle bundle_my = new Bundle(); // 번들을 통해 값 전달
                                    bundle_my.putString("userName", userName);//번들에 넘길 값 저장
                                    SuccessFragment successFragment = new SuccessFragment();
                                    successFragment.setArguments(bundle_my);
                                    ((ShareActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, successFragment).commit();
                                }
                            })
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //getActivity().finish();
                                }
                            })
                            .create()
                            .show();
                     */

                };

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    if (result.getStatus().isSuccess()) {
                        System.out.println("onConnectionResult: connection succeess");
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++");

                        connectionsClient.stopDiscovery();
                        connectionsClient.stopAdvertising();

                        iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                        Call<Member> call = iRetrofit.getMemberInfo(friendLoginId);
                        Log.d("onConnectionInitiatedfriendName", friendLoginId);
                        call.enqueue(new Callback<Member>() {
                            @Override
                            public void onResponse(Call<Member> call, Response<Member> response) {
                                Log.d("onConnectionInitiated", "Data fetch success");

                                if(response.isSuccessful() && response.body() != null){
                                    //response.body()를 result에 저장
                                    Member result = response.body();
                                    List<Member> memberInfo = result.getMemberInfo();
                                    userName = memberInfo.get(0).getUserName();
                                }
                            }
                            @Override
                            public void onFailure(Call<Member> call, Throwable t) {
                                Log.d("info::::::", t.toString());
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("알림")
                                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }
                        });

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Check")
                                .setMessage("Do you want to connect with "+userName+"?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Bundle bundle_my = new Bundle(); // 번들을 통해 값 전달
                                        bundle_my.putString("userName", userName);//번들에 넘길 값 저장
                                        SuccessFragment successFragment = new SuccessFragment();
                                        successFragment.setArguments(bundle_my);
                                        ((ShareActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, successFragment).commit();

                                    }
                                })
                                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //getActivity().finish();
                                    }
                                })
                                .create()
                                .show();


                        System.out.println("++++++++++++++++++++++++++++++++===================44444444444444");
                        System.out.println("88888888888888888888888888888888888");
                        System.out.println(myloginId);
                        System.out.println(friendLoginId);
                        System.out.println(latitude);
                        System.out.println(longtitude);
                        System.out.println("88888888888888888888888888888888888");
                        System.out.println("++++++++++++++++++++++++++++++++===================44444444444444");

                        historyRequest =new HistoryRequest();
                        historyRequest.setLoginId(myloginId);
                        historyRequest.setSubLoginId(friendLoginId);
                        historyRequest.setLatitude(Double.toString(latitude));
                        historyRequest.setLongitude(Double.toString(longtitude));

                        Call<HistoryResponse> call2= iRetrofit.insertHistoryInfo(historyRequest);
                        call2.enqueue(new Callback<HistoryResponse>() {
                            @Override
                            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {

                                if(response.isSuccessful()&&response.body()!=null)
                                {
                                    HistoryResponse result = response.body();

                                    //받은 코드 저장
                                    String resultCode = result.getResultCode();

                                    String success = "200";
                                    String errorId = "300";

                                    if(resultCode.equals(success)) {

                                        System.out.println("scuceessssssss");
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<HistoryResponse> call, Throwable t) {

                            }
                        });


                        System.out.println(longtitude);
                        connectionsClient.stopDiscovery();
                        connectionsClient.stopAdvertising();
                        friendEndpointId = endpointId;


                    } else {
                        System.out.println("==========");
                        System.out.println(myloginId);
                        System.out.println("onConnectionResult: connection failed");
                    }


                    //



                }

                @Override
                public void onDisconnected(String endpointId) {
                    System.out.println("onDisconnected: disconnected from the friend");
                    resetAll();
                }
            };



    // Callbacks for finding other devices
    private final EndpointDiscoveryCallback endpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(String endpointId, DiscoveredEndpointInfo info) {
                    System.out.println("=============================");
                    System.out.println(myloginId);
                    System.out.println("discover Found EndPoint");
                    System.out.println();

                    connectionsClient.requestConnection(myloginId, endpointId, connectionLifecycleCallback)
                            .addOnSuccessListener(
                                    (Void unused)->{
                                        System.out.println("========");
                                        System.out.println(myloginId);
                                        System.out.println("========");
                                        System.out.println("========");
                                    }
                            )
                            .addOnFailureListener(
                                    (Exception e)->{
                                        System.out.println("");
                                        System.out.println(myloginId);
                                        System.out.println("fail request conection");
                                        System.out.println(e);
                                        System.out.println("");
                                        System.out.println("end explain");
                                        System.out.println("");
                                        connectionsClient.stopDiscovery();
                                        connectionsClient.stopAdvertising();
                                        findFriend();

                                    }

                            );

                }

                @Override
                public void onEndpointLost(String endpointId) {
                    System.out.println(myloginId);
                    System.out.println("has lost discovered endpoint");
                }
            };




    ImageView image_click;
    TextView connect_text;
    ImageView clickfin;
    FrameLayout button_back;
    TextView click_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ㅇㅈㅇ
        connectionsClient = Nearby.getConnectionsClient(getContext());
        myView = inflater.inflate(R.layout.fragment_blank, container, false);

        image_click = (ImageView) myView.findViewById(R.id.image_click);
        Glide.with(this).load(R.drawable.blacksquare).into(image_click);

        HiveProgressView progressView = (HiveProgressView) myView.findViewById(R.id.hive_progress);
        progressView.setRainbow(true);
        //progressView.setColor(0x000000);



        clickfin = (ImageView) myView.findViewById(R.id.clickfin);
        Glide.with(this).load(R.drawable.clickfin).into(clickfin);

        final Drawable drawable = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_heart);
        final Shape.DrawableShape drawableShape = new Shape.DrawableShape(drawable, true);




        image_click.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 통신해야하는 부분

                image_click = (ImageView) myView.findViewById(R.id.image_click);
                Glide.with(getContext()).load(R.drawable.loading2).into(image_click);
                button_back = (FrameLayout) myView.findViewById(R.id.button_back);
                button_back.setBackgroundColor(Color.parseColor("#00000000"));
                click_text = (TextView) myView.findViewById(R.id.click_text);
                click_text.setText("");
                clickfin.setVisibility(View.INVISIBLE);


                getLocationInfo();

                if (!hasPermissions(getContext(), REQUIRED_PERMISSIONS)) {
                    ;
                    // no problemmmm
                    requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_REQUIRED_PERMISSIONS);
                }


                if (getArguments() != null)
                {
                    myloginId = getArguments().getString("loginId");
                    findFriend();
                }

            }
        });

        return myView;
    }


    /** Returns true if the app was granted all the permissions. Otherwise, returns false. */
    private static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /** Handles user acceptance (or denial) of our permission request. */
    @CallSuper
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != REQUEST_CODE_REQUIRED_PERMISSIONS) {
            return;
        }

        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getContext(), R.string.error_missing_permissions, Toast.LENGTH_LONG).show();
                return;
            }
        }

    }


    public void findFriend() {
        startAdvertising();
        startDiscovery();

    }


    private void startDiscovery() {

        connectionsClient.startDiscovery(
                getActivity().getPackageName(),
                endpointDiscoveryCallback,
                new DiscoveryOptions.Builder().setStrategy(STRATEGY).build())
                .addOnSuccessListener((Void unused)->
                        {
                            System.out.println("");
                            System.out.println(myloginId);
                            System.out.println("is startingdiscovery");
                            System.out.println("============================");}
                )
                .addOnFailureListener((Exception e)->{

                    System.out.println("");
                    System.out.println(myloginId);
                    System.out.println("is fail discovery");
                    System.out.println("============================");
                    System.out.println(e);
                });

    }

    private void startAdvertising() {

        connectionsClient.startAdvertising(
                myloginId,
                getActivity().getPackageName(),
                connectionLifecycleCallback,
                new AdvertisingOptions.Builder().setStrategy(STRATEGY).build())
                .addOnSuccessListener( (Void unused)->{
                    System.out.println("");
                    System.out.println(myloginId);
                    System.out.println("is startingadvertising");
                    System.out.println("============================");;
                }).addOnFailureListener((Exception e)->
                {  System.out.println("disdis");
                    System.out.println(e);}

        );
    }



    public  void resetAll(){
    }


    public void getLocationInfo(){


        LocationManager manager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);


        try{


            System.out.println("test");
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location!=null) {
                System.out.println("test2");
                double latitude_ = location.getLatitude();
                double longtitude_ = location.getLongitude();
                latitude = latitude_;
                longtitude =longtitude_;

                String message = "ratitude : " + latitude + "\n longtitute" + longtitude;
                System.out.println(message);

            }else{
            }


        }catch (SecurityException e){
            e.printStackTrace();
            System.out.println("cant get Location Info");
        }


    }
}