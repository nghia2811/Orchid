package com.project2.orchid.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project2.orchid.Activity.ProductActivity;
import com.project2.orchid.Object.Notification;
import com.project2.orchid.R;

import java.util.ArrayList;


public class RecyclerViewAdapterNotification extends RecyclerView.Adapter<RecyclerViewAdapterNotification.MyViewHolder> {
    private Context mContext;
    private ArrayList<Notification> mData;
    DatabaseReference ref;

    public RecyclerViewAdapterNotification(Context mContext, ArrayList<Notification> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_notification, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).getTitle());
        String sourceString = "<b>" + mData.get(position).getCustomer() + "</b> " + " đã bình luận về sản phẩm của bạn";
        holder.comment.setText(Html.fromHtml(sourceString));

        ref = FirebaseDatabase.getInstance().getReference().child("Product").child(mData.get(position).getProduct());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(mContext).load(dataSnapshot.child("hinhAnh").getValue().toString())
                        .placeholder(R.drawable.noimage).into(holder.thumbnail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Load hình ảnh thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductActivity.class);

                // passing data to the book activity
                intent.putExtra("Ten", mData.get(position).getProduct());

                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void restoreItem(Notification item, int position) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<Notification> getData() {
        return mData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, comment;
        ImageView thumbnail;
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.ngaythongbao);
            comment = itemView.findViewById(R.id.binhluan);
            cardView = (LinearLayout) itemView.findViewById(R.id.card_notification);
            thumbnail = itemView.findViewById(R.id.notification_img);
        }
    }


}
