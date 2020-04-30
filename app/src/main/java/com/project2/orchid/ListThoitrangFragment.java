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

public class ListThoitrangFragment extends Fragment {
    ArrayList<Category2> thoitrangnu, thoitrangnam, phukien;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_thoitrang, container, false);

        thoitrangnu  = new ArrayList<>();
        thoitrangnu.add(new Category2("Áo sơ mi nữ", R.drawable.thoitrangnu));

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_thoitrangnu);
        RecyclerViewAdapterThoiTrang myAdapter = new RecyclerViewAdapterThoiTrang(getContext(),thoitrangnu);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        thoitrangnam = new ArrayList<>();
        thoitrangnam.add(new Category2("Áo thun nam 5S", R.drawable.thoitrangnam));

        RecyclerView myrv1 = (RecyclerView) root.findViewById(R.id.recyclerView_thoitrangnam);
        RecyclerViewAdapterThoiTrang myAdapter1 = new RecyclerViewAdapterThoiTrang(getContext(),thoitrangnam);
        myrv1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv1.setAdapter(myAdapter1);

        phukien  = new ArrayList<>();
        phukien.add(new Category2("Bộ balo thời trang", R.drawable.phukien1));
        phukien.add(new Category2("Ví mini", R.drawable.phukien));
        phukien.add(new Category2("Giày thể thao Sneaker", R.drawable.phukien2));

        RecyclerView myrv2 = (RecyclerView) root.findViewById(R.id.recyclerView_phukien);
        RecyclerViewAdapterThoiTrang myAdapter2 = new RecyclerViewAdapterThoiTrang(getContext(),phukien);
        myrv2.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv2.setAdapter(myAdapter2);
        return root;
    }
}