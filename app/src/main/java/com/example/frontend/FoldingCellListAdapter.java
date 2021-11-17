package com.example.frontend;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.frontend.entity.Card;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.ramotion.foldingcell.FoldingCell;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends ArrayAdapter<Card> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultHistoryBtnClickListener;
    private IRetrofit iRetrofit;
    private ShareActivity sActivity;

    public FoldingCellListAdapter(Context context, List<Card> objects, ShareActivity shareActivity) {
        super(context, 0, objects);
        sActivity = shareActivity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        Card item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;


        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder

            /*Title View*/
            viewHolder.image1 = cell.findViewById(R.id.image1);
            viewHolder.userName = cell.findViewById(R.id.title_userName);
            viewHolder.birthdate = cell.findViewById(R.id.title_birthdate);

            /*Content View*/
            viewHolder.telno1 = cell.findViewById(R.id.content_telno1);
            viewHolder.email = cell.findViewById(R.id.content_email);
            viewHolder.workType = cell.findViewById(R.id.content_work_type);
            //viewHolder.workTypeDetail = cell.findViewById(R.id.content_telno1);
            viewHolder.residence = cell.findViewById(R.id.content_residence);
            //viewHolder.residenceDetail = cell.findViewById(R.id.content_telno1);
            viewHolder.mbti = cell.findViewById(R.id.content_mbti);
            viewHolder.univ = cell.findViewById(R.id.content_major);
            viewHolder.totalText = cell.findViewById(R.id.content_total_text);

            viewHolder.contentCallImage = cell.findViewById(R.id.content_call_image);
            viewHolder.contentEmailImage = cell.findViewById(R.id.content_email_image);
            viewHolder.contentHistoryBtn = cell.findViewById(R.id.content_history_btn);


            cell.setTag(viewHolder);

        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        // bind data from selected element to view through view holder

        /*Title View*/
        viewHolder.userName.setText(item.getUserName());
        viewHolder.birthdate.setText(item.getBirthdate());

        /*set image file*/
        String fileId = item.getFileId();
        if(!"".equals(fileId) && fileId != null){
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
                                Log.d("inputStream",inputStream.toString());
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                                Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);

                                sActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        viewHolder.image1.setImageBitmap(bitmap);
                                    }
                                });

                            }
                        }.start();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("info::::::", t.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(sActivity);
                    builder.setTitle("알림")
                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }

            });
        }

        /*Content View*/
        viewHolder.telno1.setText(item.getTelno1());
        viewHolder.email.setText(item.getEmail());
        viewHolder.workType.setText(item.getWorkType());
        //viewHolder.workTypeDetail.setText(item.getWorkTypeDetail());
        viewHolder.residence.setText(item.getResidence());
        //viewHolder.residenceDetail.setText(item.getResidenceDetail());
        viewHolder.mbti.setText(item.getMbti());
        viewHolder.univ.setText(item.getUniv());

        /*전화걸기*/
        viewHolder.contentCallImage.setOnClickListener(new View.OnClickListener(){
            String tel = "tel:";
            @Override
            public void onClick(View view) {
                tel += item.getTelno1();
                view.getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(tel)));
            }
        });

        /*이메일 보내기*/
        viewHolder.contentEmailImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {item.getEmail()};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "내용을 입력해주세요.");
                view.getContext().startActivity(email);

            }
        });

        /*히스토리 뷰로 화면 전환*/
        viewHolder.contentHistoryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                HistoryFragment historyFragment = new HistoryFragment();
                ((ShareActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, historyFragment).commit();
            }
        });

        /*총 만남 횟수 세팅*/
        String str = "total 15 times you met";
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 6, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.totalText.setText(ssb);

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }
    // View lookup cache
    private static class ViewHolder {
        /*Title View*/
        CircleImageView image1;
        TextView userName;
        TextView birthdate;

        /*Content View*/
        TextView telno1;
        TextView email;
        TextView workType;
        TextView workTypeDetail;
        TextView residence;
        TextView residenceDetail;
        TextView mbti;
        TextView univ;

        TextView totalText;
        TextView contentHistoryBtn;
        ImageView contentCallImage;
        ImageView contentEmailImage;

    }

}