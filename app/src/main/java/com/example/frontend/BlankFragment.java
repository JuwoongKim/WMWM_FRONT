package com.example.frontend;

import static java.nio.charset.StandardCharsets.UTF_8;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate.Status;

import org.w3c.dom.Text;

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

    private String status;

    // View
    private View myView;
    //Button
    private Button shareButton;
    private Button disconnectButton;

    //Test용 변수
    private TextView myNameText;
    private TextView statusText;
    private TextView friendNameText;


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
                    connectionsClient.acceptConnection(endpointId, payloadCallback);
                    friendName = connectionInfo.getEndpointName();
                    setFriendNameText(friendName);
                    setStatusText("connected");
                    System.out.println("===========");
                    System.out.println("===========");
                    System.out.println("===========");
                    System.out.println(friendName);
                    System.out.println("===========");
                    System.out.println("===========");
                    System.out.println("===========");
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    if (result.getStatus().isSuccess()) {
                        System.out.println("onConnectionResult: connection succeess");
                        connectionsClient.stopDiscovery();
                        connectionsClient.stopAdvertising();
                        friendEndpointId = endpointId;
                        setStatusText("finished");

                    } else {
                        System.out.println("onConnectionResult: connection failed");
                    }
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
                    System.out.println(myName);
                    System.out.println(" ===================i am a discovery part======================");
                    connectionsClient.requestConnection(myName, endpointId, connectionLifecycleCallback);
                }

                @Override
                public void onEndpointLost(String endpointId) {}
            };





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        connectionsClient = Nearby.getConnectionsClient(getContext());
        myView = inflater.inflate(R.layout.fragment_blank, container, false);


        myNameText = myView.findViewById(R.id.myName);
        statusText = myView.findViewById(R.id.status);
        friendNameText = myView.findViewById(R.id.frinedName);

        disconnectButton= myView.findViewById(R.id.disconnect);
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectionsClient.disconnectFromEndpoint(friendEndpointId);
                resetAll();
            }
        });


        shareButton = myView.findViewById(R.id.btn_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                // 통신해야하는 부분

                System.out.println("the test is good ");

                if (!hasPermissions(getContext(), REQUIRED_PERMISSIONS)) {
                    requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_REQUIRED_PERMISSIONS);
                }

                if (getArguments() != null)
                {
                    myName = getArguments().getString("loginId");
                    setMyNameText(myName);
                    System.out.println("=========== the name is =============");
                    System.out.println(myName);
                    findFriend();
                }












                // Button Activity
            }
        });

        // Inflate the layout for this fragment
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
        setStatusText("Searching");
    }

    private void startDiscovery() {

        connectionsClient.startDiscovery(
                getActivity().getPackageName(),
                endpointDiscoveryCallback,
                new DiscoveryOptions.Builder().setStrategy(STRATEGY).build());
    }

    private void startAdvertising() {

        connectionsClient.startAdvertising(
                myName,
                getActivity().getPackageName(),
                connectionLifecycleCallback,
                new AdvertisingOptions.Builder().setStrategy(STRATEGY).build());
    }

    public void setMyNameText(String myName){
        myNameText.setText(myName);
    }


    public  void setStatusText(String status)
    {
        statusText.setText(status);
    }

    public  void setFriendNameText(String friendName){
        friendNameText.setText(friendName);
    }

    public  void resetAll(){

        String status = "disconnected";
        String friendName = " ";

        setStatusText(status);
        setFriendNameText(friendName);


    }
}