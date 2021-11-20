package com.example.frontend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.frontend.entity.HistoryRequest;
import com.example.frontend.entity.Rank;

import java.util.List;

public class ChildTwoAdapter extends ArrayAdapter<Rank> {
    private ShareActivity sActivity;

    public ChildTwoAdapter(@NonNull Context context, List<Rank> objects, ShareActivity shareActivity) {
        super(context, 0, objects);
        sActivity = shareActivity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        Rank item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        ViewHolder viewHolder = new ViewHolder();

        LayoutInflater vi = LayoutInflater.from(getContext());
        View cell = vi.inflate(R.layout.rank_title_layout, parent, false);

        viewHolder.tv_rank = (TextView) cell.findViewById(R.id.tv_rank);
        viewHolder.tv_user_name = (TextView) cell.findViewById(R.id.tv_user_name);
        viewHolder.tv_total = (TextView) cell.findViewById(R.id.tv_total);
        cell.setTag(viewHolder);

        if (null == item){
            return cell;
        }

        viewHolder.tv_rank.setText(item.getRank());
        viewHolder.tv_user_name.setText(item.getUserName());
        String totalText = item.getTotal().trim()+" times";
        viewHolder.tv_total.setText(totalText);

        return cell;
    }

    // View lookup cache
    private static class ViewHolder {

        TextView tv_rank;
        TextView tv_user_name;
        TextView tv_total;

    }
}
