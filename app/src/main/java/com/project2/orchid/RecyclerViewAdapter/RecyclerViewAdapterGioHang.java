package com.project2.orchid.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project2.orchid.Activity.ProductActivity;
import com.project2.orchid.Object.Product;
import com.project2.orchid.R;

import java.util.ArrayList;

public class RecyclerViewAdapterGioHang extends RecyclerView.Adapter<RecyclerViewAdapterGioHang.MyViewHolder> {
    private Context mContext ;
    private ArrayList<Product> mData;

    public RecyclerViewAdapterGioHang(Context mContext, ArrayList<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_giohang, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tensp.setText(mData.get(position).getTen());
        holder.nhasx.setText("Cung cấp bởi " + mData.get(position).getNhaSanXuat());
        holder.giasp.setText(mData.get(position).getGiaTien());
        Glide.with(mContext).load(mData.get(position).getHinhAnh()).placeholder(R.drawable.noimage).into(holder.anhsp);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductActivity.class);

                // passing data to the book activity
                intent.putExtra("Ten", mData.get(position).getTen());

                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Product item, int position) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Product> getData() {
        return mData;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nhasx, tensp, giasp;
        ImageView anhsp;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            giasp = (TextView) itemView.findViewById(R.id.giohang_gia);
            nhasx = (TextView) itemView.findViewById(R.id.giohang_nhasx);
            tensp = (TextView) itemView.findViewById(R.id.giohang_ten);
            anhsp = (ImageView) itemView.findViewById(R.id.imageView_giohang);
            cardView =  itemView.findViewById(R.id.card_giohang);

        }
    }
}


