package com.example.frontend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.frontend.entity.Member;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Member> arrayList = new ArrayList<>();
    private Context context;

    public CustomAdapter(ArrayList<Member> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("adater","onCreateViewHolder들어옴!");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
/*        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);*/
        holder.tv_id.setText(arrayList.get(position).getLoginId());
        holder.tv_pwd.setText(arrayList.get(position).getPwd());
        holder.tv_email.setText(arrayList.get(position).getEmail());



    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        /*ImageView iv_profile;*/
        TextView tv_id;
        TextView tv_pwd;
        TextView tv_email;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            /*this.iv_profile = itemView.findViewById(R.id.iv_profile);*/
            this.tv_id = itemView.findViewById(R.id.tv_id);
            this.tv_pwd = itemView.findViewById(R.id.tv_pwd);
            this.tv_email = itemView.findViewById(R.id.tv_email);
        }
    }
}
