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

public class ListSachFragment extends Fragment {
    ArrayList<Category2> sach, vpp;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_sach, container, false);

        sach = new ArrayList<>();
        sach.add(new Category2("Nhà giả kim", R.drawable.sach2));
        sach.add(new Category2("Đắc nhân tâm", R.drawable.sach4));
        sach.add(new Category2("Trên đường băng", R.drawable.sach3));

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_sach);
        RecyclerViewAdapterSach myAdapter = new RecyclerViewAdapterSach(getContext(),sach);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        vpp = new ArrayList<>();
        vpp.add(new Category2("Giấy Double A4", R.drawable.vpp1));
        vpp.add(new Category2("File hộp Deli", R.drawable.vpp));
        RecyclerView myrv1 = (RecyclerView) root.findViewById(R.id.recyclerView_vpp);
        RecyclerViewAdapterSach myAdapter1 = new RecyclerViewAdapterSach(getContext(),vpp);
        myrv1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv1.setAdapter(myAdapter1);

        return root;
    }
}