package com.project2.orchid.CategoryFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.orchid.Object.Category2;
import com.project2.orchid.R;
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterList2;

import java.util.ArrayList;
import java.util.List;

public class ListHomeFragment extends Fragment {
    List<Category2> lstDanhmuc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listhome, container, false);

        lstDanhmuc = new ArrayList<>();
        lstDanhmuc.add(new Category2("Điện thoại - Máy tính", R.drawable.dienthoaimaytinh));
        lstDanhmuc.add(new Category2("Thời trang",R.drawable.thoitrang1));
        lstDanhmuc.add(new Category2("Nhà cửa - Nội thất",R.drawable.xoong));
        lstDanhmuc.add(new Category2("Làm đẹp - Sức khỏe",R.drawable.son));
        lstDanhmuc.add(new Category2("Sách",R.drawable.khuyenhoc));
        lstDanhmuc.add(new Category2("Đồ chơi, Mẹ và bé",R.drawable.mevabe));
        lstDanhmuc.add(new Category2("Thể thao - Dã ngoại",R.drawable.thethao));
        lstDanhmuc.add(new Category2("Ô tô - Xe máy",R.drawable.xemay));
        lstDanhmuc.add(new Category2("Voucher - Dịch vụ",R.drawable.voucher));

        GridLayoutManager layoutManagerDanhmuc = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewDanhmuc = (RecyclerView) root.findViewById(R.id.recyclerView_list);
        recyclerViewDanhmuc.setLayoutManager(layoutManagerDanhmuc);
        RecyclerViewAdapterList2 myAdapterDanhmuc = new RecyclerViewAdapterList2(this,lstDanhmuc);
        recyclerViewDanhmuc.setAdapter(myAdapterDanhmuc);

        return root;
    }
}