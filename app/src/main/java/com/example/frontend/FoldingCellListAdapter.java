package com.example.frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.frontend.entity.Card;
import com.example.frontend.entity.Item;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends ArrayAdapter<Card> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    public FoldingCellListAdapter(Context context, List<Card> objects) {
        super(context, 0, objects);
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
            viewHolder.userName = cell.findViewById(R.id.title_userName);
            viewHolder.birthdate = cell.findViewById(R.id.title_birthdate);
            viewHolder.univ = cell.findViewById(R.id.title_univ);

            viewHolder.telno1 = cell.findViewById(R.id.content_telno1);






            viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);

            //@@@@@@@@@@@@@@@@@@@@@@@추가
            viewHolder.image1 = cell.findViewById(R.id.image1);

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
        //viewHolder.price.setText(item.getMbti());
        //viewHolder.time.setText(item.getMbti());
        //viewHolder.date.setText(item.getMbti());
        viewHolder.userName.setText(item.getUserName());
        viewHolder.birthdate.setText(item.getBirthdate());
        viewHolder.univ.setText(item.getUniv());

        viewHolder.telno1.setText(item.getTelno1());


        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@추가
        viewHolder.image1.setImageResource(R.drawable.jenny);


        // set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }

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

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        CircleImageView image1;
        TextView price;
        TextView contentRequestBtn;
        TextView date;
        TextView time;


        TextView univ;
        TextView birthdate;
        TextView userName;
        TextView telno1;
    }
}