package com.example.frontend;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.frontend.entity.Card;
import com.example.frontend.entity.LoginResponse;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyEditFragment() {
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
    public static MyEditFragment newInstance(String param1, String param2) {
        MyEditFragment fragment = new MyEditFragment();
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


    private ImageView image_back;
    private CircleImageView image1;
    private TextView contentUserNameTop;
    private TextView contentUserName;
    private TextView contentTelno1;
    private TextView contentEmail;
    private EditText contentWorkType;
    private EditText contentResidence;
    private EditText contentMbti;
    private EditText contentUniv;
    private FloatingActionButton saveBtn;

    private IRetrofit iRetrofit;

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE=10;
    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름
    private String imagePath;
    private Uri imgUri;
    private int file_flag = 0;
    private String originFileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Activity.MODE_PRIVATE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_edit, container, false);

        image1 = (CircleImageView) view.findViewById(R.id.image1);
        contentUserNameTop = (TextView) view.findViewById(R.id.content_user_name_top);
        contentUserName= (TextView) view.findViewById(R.id.content_user_name);
        contentTelno1= (TextView) view.findViewById(R.id.content_telno1);
        contentEmail= (TextView) view.findViewById(R.id.content_email);
        contentWorkType= (EditText) view.findViewById(R.id.content_work_type);
        contentResidence= (EditText) view.findViewById(R.id.content_residence);
        contentMbti= (EditText) view.findViewById(R.id.content_mbti);
        contentUniv= (EditText) view.findViewById(R.id.content_major);
        saveBtn = (FloatingActionButton) view.findViewById(R.id.save_btn);

        if (getArguments() != null)
        {
            String userNo = getArguments().getString("userNo"); // 프래그먼트1에서 받아온 값 넣기

            //retrofit 생성
            iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
            Call<Card> call = iRetrofit.selectMyInfo(userNo);
            call.enqueue(new Callback<Card>() {
                @Override
                public void onResponse(Call<Card> call, Response<Card> response) {
                    Log.d("retrofit", "Data fetch success");

                    if(response.isSuccessful() && response.body() != null){
                        //response.body()를 result에 저장
                        Card cardInfo = response.body().getCardInfo().get(0);

                        //받은 코드 저장
                        String userName = cardInfo.getUserName().toString();
                        String telno = cardInfo.getTelno1().toString();
                        String email = cardInfo.getEmail().toString();
                        String workType = cardInfo.getWorkType().toString();
                        //String birthdate = cardInfo.getBirthdate().toString();
                        //String gender = cardInfo.getGender().toString();
                        String residence = cardInfo.getResidence().toString();
                        String mbti = cardInfo.getMbti().toString();
                        String univ = cardInfo.getUniv().toString();

                        contentUserNameTop.setText(userName);
                        contentUserName.setText(userName);
                        contentTelno1.setText(telno);
                        contentEmail.setText(email);
                        contentWorkType.setText(workType);
                        contentResidence.setText(residence);
                        contentMbti.setText(mbti);
                        contentUniv.setText(univ);

                        if(cardInfo.getFileId()!= null){
                            String fileId = cardInfo.getFileId().toString();
                            originFileName = fileId;
                            if(!"".equals(fileId)){
                                setFile(fileId);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Card> call, Throwable t) {
                    Log.d("info::::::", t.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("알림")
                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            });

        }


        /*프로필 사진 변경*/
        image1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectGallery();
                file_flag = 1;
            }
        });

        /*저장 버튼*/
        saveBtn.setOnClickListener(new View.OnClickListener(){
            String userNo1 = getArguments().getString("userNo");

            @Override
            public void onClick(View view) {
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Alarm")
                        .setMessage("Are you sure you want to save?")
                        .setPositiveButton("yes", null)
                        .create()
                        .show();*/

                if(file_flag == 0){
                    saveMyInfo(userNo1, originFileName);
                }else{
                    File file = new File(imagePath);
                    InputStream inputStream = null;
                    try {
                        Log.d("imgUri:", String.valueOf(imgUri));
                        inputStream = getContext().getContentResolver().openInputStream(imgUri);
                    }catch(IOException e) {
                        e.printStackTrace();
                    }

                    Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray());
                    MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("uploadFile", file.getName() ,requestBody);

                    HashMap<String, RequestBody> map = new HashMap<>();
                    RequestBody userNo = createPartFromString(userNo1);
                    map.put("userNo", userNo);

                    iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
                    Call<Card> call = iRetrofit.fileUpload(uploadFile, map);
                    call.enqueue(new Callback<Card>() {
                        @Override
                        public void onResponse(Call<Card> call, Response<Card> response) {
                            Card result = response.body();

                            if(result.getResultCode().equals("success")){
                                saveMyInfo(userNo1, result.getFileId());
                            }

/*                if(result.getStatus() == 200){
                    Toast.makeText(save_button.getContext(),"저장이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                }*/
                        }
                        @Override
                        public void onFailure(Call<Card> call, Throwable t) {
                            Log.d("eroorrrrrrrrrrrrrr",t.toString());
                            //Toast.makeText(save_button.getContext(), "통신에러",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }

    /*set image file*/
    private void setFile(String fileId) {

        iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
        Call<ResponseBody> call = iRetrofit.fileDownload(fileId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("file", "Data fetch success");

                if (response.isSuccessful()) {

                    new Thread() {
                        public void run() {
                            InputStream inputStream = response.body().byteStream();
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                            Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    image1.setImageBitmap(bitmap);
                                }
                            });

                        }
                    }.start();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("info::::::", t.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }

        });
    }

    private void saveMyInfo(String userNo, String fileId){
        String savedWorkType = contentWorkType.getText().toString();
        String savedResidence = contentResidence.getText().toString();
        String savedMbti = contentMbti.getText().toString();
        String savedUniv = contentUniv.getText().toString();

        if (getArguments() != null)
        {
            Card card = new Card(userNo, savedWorkType, savedResidence, savedMbti, savedUniv, fileId);

            //retrofit 생성
            iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
            Call<Card> call = iRetrofit.updateMyInfo(card);
            call.enqueue(new Callback<Card>() {
                @Override
                public void onResponse(Call<Card> call, Response<Card> response) {
                    Log.d("retrofit", "Data fetch success");

                    if(response.isSuccessful() && response.body() != null){
                        //response.body()를 result에 저장
                        Card result = response.body();

                        //받은 코드 저장
                        String resultCode = result.getResultCode();

                        if(Integer.parseInt(resultCode)>0){
                            Bundle bundle_my = new Bundle(); // 번들을 통해 값 전달
                            bundle_my.putString("userNo", userNo);//번들에 넘길 값 저장
                            BlankFragment3 blankFragment3 = new BlankFragment3();
                            blankFragment3.setArguments(bundle_my);
                            ((ShareActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, blankFragment3).commit();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Alarm")
                                    .setMessage("success.")
                                    .setPositiveButton("yes", null)
                                    .create()
                                    .show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Card> call, Throwable t) {
                    Log.d("info::::::", t.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Alarm")
                            .setMessage("failed.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            });

        }
    }


    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        image1.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    private void selectGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.d("requestCode:", String.valueOf(requestCode));
            switch (requestCode) {

                case GALLERY_CODE:
                    sendPicture(data.getData()); //갤러리에서 가져오기
                    break;
                case CAMERA_CODE:
                    getPictureForPhoto(); //카메라에서 가져오기
                    break;

                default:
                    break;
            }

        }
    }

    private void sendPicture(Uri photoUri) {
        imgUri = photoUri;
        imagePath = getRealPathFromURI(photoUri); // path 경로

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        image1.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기


    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
    }


    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

}