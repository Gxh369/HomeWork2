package com.jy.gxh.day2homework.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jy.gxh.day2homework.R;
import com.jy.gxh.day2homework.beans.HomeBean;

import java.security.KeyStore;
import java.util.ArrayList;

/**
 * Created by GXH on 2019/9/19.
 */

public class MyRecyAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<HomeBean.DataBean.DatasBean> datas;

    public MyRecyAdapter(Context context, ArrayList<HomeBean.DataBean.DatasBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==1) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_item1_layout, parent, false);
            return new MyHolder1(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.home_item2_layout, parent, false);
            return new MyHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                MyHolder1 myHolder= (MyHolder1) holder;
                myHolder.item1_name.setText(datas.get(position).getChapterName());
                Glide.with(context).load(datas.get(position).getEnvelopePic()).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(myHolder.item1_img);
                break;
            case 2:
                MyHolder2 myHolder2= (MyHolder2) holder;
                Glide.with(context).load(datas.get(position).getEnvelopePic()).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(myHolder2.item2_img);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==3){
            return 2;
        }else {
            return 1;
        }
    }
    class MyHolder1 extends RecyclerView.ViewHolder {

        private final ImageView item1_img;
        private final TextView item1_name;

        public MyHolder1(View itemView) {
            super(itemView);
            item1_img = itemView.findViewById(R.id.item1_img);
            item1_name = itemView.findViewById(R.id.item1_name);

        }
    }
    class MyHolder2 extends RecyclerView.ViewHolder {
        private final ImageView item2_img;
        public MyHolder2(View itemView) {
            super(itemView);
            item2_img = itemView.findViewById(R.id.item2_img);
        }
    }

}
