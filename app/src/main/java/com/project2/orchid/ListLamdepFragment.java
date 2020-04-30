package com.project2.orchid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListLamdepFragment extends Fragment {
    ArrayList<Category2> mipham, suckhoe;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_lamdep, container, false);

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