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
import com.example.frontend.entity.HistoryRequest;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.ramotion.foldingcell.FoldingCell;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryAdapter extends ArrayAdapter<HistoryRequest>  {

    private ShareActivity sActivity;

    public HistoryAdapter(@NonNull Context context, List<HistoryRequest> objects, ShareActivity shareActivity) {
        super(context, 0, objects);
        sActivity = shareActivity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        HistoryRequest item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        ViewHolder viewHolder;


        viewHolder = new ViewHolder();
        LayoutInflater vi = LayoutInflater.from(getContext());
        View cell = vi.inflate(R.layout.history_title_layout, parent, false);
        Log.d("historyadapter","in!!!!!!!!!!!!!!!!!");
        
        // binding view parts to view holder

        /*Title View*/
        viewHolder.tv_day = (TextView) cell.findViewById(R.id.tv_day);
        viewHolder.tv_date = (TextView) cell.findViewById(R.id.tv_date);
        viewHolder.tv_before = (TextView) cell.findViewById(R.id.tv_before);

        cell.setTag(viewHolder);

        if (null == item){
            return cell;
        }

        /*Content View*/
        viewHolder.tv_day.setText(item.getDay());
        viewHolder.tv_date.setText(item.getRegDt());
        String beforeText = item.getBeforeDay().trim()+"일 전";
        viewHolder.tv_before.setText(beforeText);

        return cell;
    }

    // View lookup cache
    private static class ViewHolder {

        /*Content View*/
        TextView tv_day;
        TextView tv_date;
        TextView tv_before;

    }
}
