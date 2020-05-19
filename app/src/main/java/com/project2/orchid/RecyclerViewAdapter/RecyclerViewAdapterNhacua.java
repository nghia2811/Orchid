package com.project2.orchid.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project2.orchid.Activity.ProductActivity;
import com.project2.orchid.Object.Category2;
import com.project2.orchid.R;

import java.util.List;


public class RecyclerViewAdapterNhacua extends RecyclerView.Adapter<RecyclerViewAdapterNhacua.MyViewHolder> {
    private Context mContext ;
    private List<Category2> mData ;

    public RecyclerViewAdapterNhacua(Context mContext, List<Category2> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_category_title.setText(mData.get(position).getTitle());
        holder.img_category_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductActivity.class);

                // passing data to the book activity
                intent.putExtra("Ten",mData.get(position).getTitle());
                intent.putExtra("DanhMuc","Nhà cửa - Đồ gia dụng");
                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_category_title;
        ImageView img_category_thumbnail;
        LinearLayout cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_title = (TextView) itemView.findViewById(R.id.danhmuc_ten) ;
            img_category_thumbnail = (ImageView) itemView.findViewById(R.id.danhmuc_anh);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardview_list);
        }
    }


}
