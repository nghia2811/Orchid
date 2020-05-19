package com.project2.orchid.CategoryFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.orchid.Activity.CategoryActivity;
import com.project2.orchid.Object.Category2;
import com.project2.orchid.R;
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterLamdep;

import java.util.ArrayList;

public class ListLamdepFragment extends Fragment {
    ArrayList<Category2> mipham, suckhoe;
    Button category;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_lamdep, container, false);
        category = root.findViewById(R.id.btn_list_lamdep);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("DanhMuc", category.getText());
                startActivity(intent);
            }
        });

        suckhoe = new ArrayList<>();
        suckhoe.add(new Category2("Combo 4 khẩu trang", R.drawable.suckhoe1));
        suckhoe.add(new Category2("Gel nha đam", R.drawable.suckhoe2));
        suckhoe.add(new Category2("Nước rửa tay Lifebuoy", R.drawable.suckhoe3));
        suckhoe.add(new Category2("Nước súc miệng Listerine", R.drawable.suckhoe4));
        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_suckhoe);
        RecyclerViewAdapterLamdep myAdapter = new RecyclerViewAdapterLamdep(getContext(),suckhoe);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        mipham = new ArrayList<>();
        mipham.add(new Category2("Son Charme", R.drawable.son));
        RecyclerView myrv1 = (RecyclerView) root.findViewById(R.id.recyclerView_mipham);
        RecyclerViewAdapterLamdep myAdapter1 = new RecyclerViewAdapterLamdep(getContext(),mipham);
        myrv1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv1.setAdapter(myAdapter1);
        return root;
    }
}