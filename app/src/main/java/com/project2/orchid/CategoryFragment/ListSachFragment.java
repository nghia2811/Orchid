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
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterSach;

import java.util.ArrayList;

public class ListSachFragment extends Fragment {
    ArrayList<Category2> sach, vpp;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_sach, container, false);
        category = root.findViewById(R.id.btn_list_sach);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("DanhMuc", category.getText());
                startActivity(intent);
            }
        });

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