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
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterThoiTrang;

import java.util.ArrayList;

public class ListThoitrangFragment extends Fragment {
    ArrayList<Category2> thoitrangnu, thoitrangnam, phukien;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_thoitrang, container, false);
        category = root.findViewById(R.id.btn_list_thoitrang);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("DanhMuc", category.getText());
                startActivity(intent);
            }
        });

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