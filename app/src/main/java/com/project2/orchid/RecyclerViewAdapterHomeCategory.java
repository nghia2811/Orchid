package com.project2.orchid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapterHomeCategory extends RecyclerView.Adapter<RecyclerViewAdapterHomeCategory.MyViewHolder> {

    private HomeFragment mContext ;
    private List<Category> mData ;
    List<LinearLayout> cardViewList = new ArrayList<>();


    public RecyclerViewAdapterHomeCategory(HomeFragment mContext, List<Category> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext.getActivity());
        view = mInflater.inflate(R.layout.button_home_noibat,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_category_title.setText(mData.get(position).getTitle());
        cardViewList.add(holder.cardView);
        cardViewList.get(0).setBackgroundResource(R.drawable.khung2);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(LinearLayout cardView : cardViewList){
                    cardView.setBackgroundResource(R.drawable.khung);
                }
                holder.cardView.setBackgroundResource(R.drawable.khung2);
                if (position == 0) {

                }
                else if (position == 1) {

                }
                else if (position == 2){

                }
                else if (position == 3){

                }
                else if (position == 4){

                }
                else if (position == 5){

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_category_title;
        LinearLayout cardView ;
        RecyclerView Rall, Rdienthoai, Rquanao, Rlamdep, Rsach, Rnhacua;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_title = (TextView) itemView.findViewById(R.id.home_btn_title) ;
            cardView = (LinearLayout) itemView.findViewById(R.id.home_btn_noibat);
            Rall = (RecyclerView) itemView.findViewById(R.id.recyclerView_home_noibat);
            Rdienthoai = (RecyclerView) itemView.findViewById(R.id.recyclerView_home_dienthoai);
            Rlamdep = (RecyclerView) itemView.findViewById(R.id.recyclerView_home_lamdep);
            Rquanao= (RecyclerView) itemView.findViewById(R.id.recyclerView_home_quanao);
            Rsach = (RecyclerView) itemView.findViewById(R.id.recyclerView_home_sach);
            Rnhacua = (RecyclerView) itemView.findViewById(R.id.recyclerView_home_nhacua);
        }
    }


}
