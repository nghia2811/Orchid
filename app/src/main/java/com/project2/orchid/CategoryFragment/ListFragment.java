package com.project2.orchid.CategoryFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.orchid.Activity.GioHangActivity;
import com.project2.orchid.Activity.SearchActivity;
import com.project2.orchid.Object.Category2;
import com.project2.orchid.R;
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterList;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    List<Category2> lstProduct;
    ImageView gioHang;
    Button search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        search = root.findViewById(R.id.list_search);
        gioHang = root.findViewById(R.id.btn_list_giohang);

        gioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GioHangActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        getFragmentManager().beginTransaction().add(R.id.list_host_fragment, new ListHomeFragment()).commit();

        lstProduct = new ArrayList<>();
        lstProduct.add(new Category2("Gợi ý cho bạn", R.drawable.star));
        lstProduct.add(new Category2("Thời trang",R.drawable.thoitrang1));
        lstProduct.add(new Category2("Điện thoại - Máy tính",R.drawable.dienthoaimaytinh));
        lstProduct.add(new Category2("Đồ gia dụng",R.drawable.xoong));
        lstProduct.add(new Category2("Làm đẹp - Sức khỏe",R.drawable.son));
        lstProduct.add(new Category2("Sách",R.drawable.khuyenhoc));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_list_danhmuc);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterList myAdapter = new RecyclerViewAdapterList(this, lstProduct);
        recyclerView.setAdapter(myAdapter);
        return root;
    }
}