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
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterNhacua;

import java.util.ArrayList;

public class ListGiadungFragment extends Fragment {
    ArrayList<Category2> nhabep, noithat;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_giadung, container, false);
        category = root.findViewById(R.id.btn_list_giadung);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("DanhMuc", category.getText());
                startActivity(intent);
            }
        });

        nhabep = new ArrayList<>();
        nhabep.add(new Category2("Bình đá Duy Tân", R.drawable.nhabep1));
        nhabep.add(new Category2("Combo 6 hộp bảo quản", R.drawable.nhabep2));
        nhabep.add(new Category2("Cây lau nhà đứng", R.drawable.nhabep3));

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_nhabep);
        RecyclerViewAdapterNhacua myAdapter = new RecyclerViewAdapterNhacua(getContext(),nhabep);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        noithat = new ArrayList<>();
        noithat.add(new Category2("Bàn học kệ sách", R.drawable.noithat1));
        noithat.add(new Category2("Tủ Tomi-S 4 ngăn", R.drawable.noithat2));
        RecyclerView myrv1 = (RecyclerView) root.findViewById(R.id.recyclerView_noithat);
        RecyclerViewAdapterNhacua myAdapter1 = new RecyclerViewAdapterNhacua(getContext(),noithat);
        myrv1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv1.setAdapter(myAdapter1);
        return root;
    }
}