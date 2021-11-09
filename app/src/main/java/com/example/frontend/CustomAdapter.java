package com.example.frontend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.frontend.entity.Card;
import com.example.frontend.entity.Member;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Card> arrayList = new ArrayList<>();
    private Context context;

    public CustomAdapter(ArrayList<Card> arrayList, Context context) {
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
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
/*        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);*/
        holder.tv_id.setText(arrayList.get(position).getAge());
        holder.tv_pwd.setText(arrayList.get(position).getBirthdate());
        holder.tv_email.setText(arrayList.get(position).getMbti());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = position ;
                if (pos != RecyclerView.NO_POSITION) {
                    // 데이터 리스트로부터 아이템 데이터 참조.
                    Log.d("sssssssss::!!!?", arrayList.get(pos).getAge());
                }
            }
        });

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
