package com.project2.orchid.RecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project2.orchid.HomeFragment;
import com.project2.orchid.Object.Category;
import com.project2.orchid.Object.Product;
import com.project2.orchid.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecyclerViewAdapterHomeCategory extends RecyclerView.Adapter<RecyclerViewAdapterHomeCategory.MyViewHolder> {

    private HomeFragment mContext;
    private List<Category> mData;
    List<LinearLayout> cardViewList = new ArrayList<>();


    public RecyclerViewAdapterHomeCategory(HomeFragment mContext, List<Category> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext.getActivity());
        view = mInflater.inflate(R.layout.button_home_noibat, parent, false);
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
                for (LinearLayout cardView : cardViewList) {
                    cardView.setBackgroundResource(R.drawable.khung);
                }
                holder.cardView.setBackgroundResource(R.drawable.khung2);

                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext.getActivity(), LinearLayoutManager.HORIZONTAL, false);
                final RecyclerView recyclerView = mContext.root.findViewById(R.id.recyclerView_home_noibat);
                recyclerView.setLayoutManager(layoutManager);

                switch (mData.get(position).getTitle()) {
                    case "Tất cả":
                        mContext.reference = FirebaseDatabase.getInstance().getReference().child("Product");
                        mContext.reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstNoibat = new ArrayList<Product>();
                                List<Product> full = new ArrayList<>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);
                                    full.add(p);
                                }
                                Collections.shuffle(full);

                                for (int i = 0; i < 5; ++i)
                                    mContext.lstNoibat.add(full.get(i));

                                RecyclerViewAdapter myAdapterNoibat = new RecyclerViewAdapter(mContext.getContext(), mContext.lstNoibat);
                                recyclerView.setAdapter(myAdapterNoibat);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Điện thoại - Máy tính":
                        mContext.refDienthoai = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("danhMuc").equalTo("Điện thoại - Máy tính");
                        mContext.refDienthoai.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstDienthoai = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);
                                        mContext.lstDienthoai.add(p);
                                }
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstDienthoai);
                                recyclerView.setAdapter(myAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Thời trang":
                        mContext.refQuanao = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("danhMuc").equalTo("Quần áo - Thời trang");
                        mContext.refQuanao.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstQuanao = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);
                                        mContext.lstQuanao.add(p);
                                }
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstQuanao);
                                recyclerView.setAdapter(myAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Sách":
                        mContext.refSach = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("danhMuc").equalTo("Sách - Văn phòng phẩm");
                        mContext.refSach.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstSach = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);
                                        mContext.lstSach.add(p);
                                }
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstSach);
                                recyclerView.setAdapter(myAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Làm đẹp - Sức khỏe":
                        mContext.refLamdep = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("danhMuc").equalTo("Sức khỏe - Làm đẹp");
                        mContext.refLamdep.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstLamdep = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);
                                        mContext.lstLamdep.add(p);
                                }
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstLamdep);
                                recyclerView.setAdapter(myAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Đồ gia dụng":
                        mContext.refNhacua = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("danhMuc").equalTo("Nhà cửa - Đồ gia dụng");
                        mContext.refNhacua.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstNhacua = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);
                                        mContext.lstNhacua.add(p);
                                }
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstNhacua);
                                recyclerView.setAdapter(myAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
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
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_title = (TextView) itemView.findViewById(R.id.home_btn_title);
            cardView = (LinearLayout) itemView.findViewById(R.id.home_btn_noibat);
        }
    }

}
