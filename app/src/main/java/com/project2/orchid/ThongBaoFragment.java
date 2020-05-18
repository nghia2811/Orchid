package com.project2.orchid;

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

import com.project2.orchid.object.Category2;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoFragment extends Fragment {
    List<Category2> lstBtn;
    ImageView gioHang;
    Button tieptuc;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_thongbao, container, false);

        gioHang = root.findViewById(R.id.btn_thongbao_giohang);
        tieptuc = root.findViewById(R.id.thongbao_tieptuc);
        gioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GioHangActivity.class);
                startActivity(intent);
            }
        });

        tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        lstBtn = new ArrayList<>();
        lstBtn.add(new Category2(null, R.drawable.ic_home_gray_24dp));
        lstBtn.add(new Category2(null,R.drawable.ic_insert_emoticon_gray_24dp));
        lstBtn.add(new Category2(null,R.drawable.ic_whatshot_gray_24dp));
        lstBtn.add(new Category2(null,R.drawable.ic_sms_gray_24dp));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_thongbao);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterThongbao myAdapter = new RecyclerViewAdapterThongbao(this,lstBtn);
        recyclerView.setAdapter(myAdapter);

        return root;
    }
}