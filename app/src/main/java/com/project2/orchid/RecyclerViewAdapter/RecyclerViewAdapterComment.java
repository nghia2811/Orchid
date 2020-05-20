package com.project2.orchid.RecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project2.orchid.Activity.ProductActivity;
import com.project2.orchid.Object.Comment;
import com.project2.orchid.R;

import java.util.List;

public class RecyclerViewAdapterComment extends RecyclerView.Adapter<RecyclerViewAdapterComment.MyViewHolder> {
    private ProductActivity mContext;
    private List<Comment> mData;

    public RecyclerViewAdapterComment(ProductActivity mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tenkhachhang.setText(mData.get(position).getTenkhachhang());
        holder.comment.setText(mData.get(position).getComment());
        Glide.with(mContext).load(mData.get(position).getImage()).placeholder(R.drawable.noimage).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tenkhachhang, comment;
        ImageView avatar;

        public MyViewHolder(View itemView) {
            super(itemView);

            tenkhachhang = itemView.findViewById(R.id.ten_khach_hang);
            comment = itemView.findViewById(R.id.comment);
            avatar = itemView.findViewById(R.id.user_comment);
        }
    }
}
