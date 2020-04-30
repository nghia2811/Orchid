package com.project2.orchid;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapterThongbao extends RecyclerView.Adapter<RecyclerViewAdapterThongbao.MyViewHolder> {

    private ThongBaoFragment mContext ;
    private List<Category2> mData ;
    List<LinearLayout> cardViewList = new ArrayList<>();

    public RecyclerViewAdapterThongbao(ThongBaoFragment mContext, List<Category2> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext.getActivity());
        view = mInflater.inflate(R.layout.button_thongbao,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.img_category_thumbnail.setImageResource(mData.get(position).getThumbnail());
        cardViewList.add(holder.cardView);
        cardViewList.get(0).setBackgroundResource(R.color.colorWhite);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(LinearLayout cardView : cardViewList){
                    cardView.setBackgroundResource(R.color.colorGray);
                }
                holder.cardView.setBackgroundResource(R.color.colorWhite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_category_thumbnail;
        LinearLayout cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_category_thumbnail = (ImageView) itemView.findViewById(R.id.thongbao_img_id);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardview_thongbao);
        }
    }


}
