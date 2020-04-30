package com.project2.orchid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {
    ArrayList<Product> lstDaxem, lstNoibat, lstDienthoai, lstQuanao, lstNhacua, lstSach, lstLamdep;
    List<Category> lstBtnNoibat, lstBtnTimkiem;
    List<Category2> lstDanhmuc;
    Button search;
    DatabaseReference reference, refDaxem, refDienthoai, refQuanao, refNhacua, refLamdep, refSach;
    TextView xemthemYeuThich,xemthemNoiBat;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        xemthemYeuThich = root.findViewById(R.id.home_daxem_xemthem);
        xemthemNoiBat = root.findViewById(R.id.home_noibat_xemthem);
        search = (Button) root.findViewById(R.id.home_search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        xemthemYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YeuThichActivity.class);
                startActivity(intent);
            }
        });

        xemthemNoiBat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoiBatActivity.class);
                startActivity(intent);
            }
        });

        lstBtnNoibat = new ArrayList<>();
        lstBtnNoibat.add(new Category("Tất cả"));
        lstBtnNoibat.add(new Category("Điện thoại - Máy tính"));
        lstBtnNoibat.add(new Category("Thời trang"));
        lstBtnNoibat.add(new Category("Sách"));
        lstBtnNoibat.add(new Category("Làm đẹp - Sức khỏe"));
        lstBtnNoibat.add(new Category("Đồ gia dụng"));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewBtnNoibat = (RecyclerView) root.findViewById(R.id.recyclerView_home_button);
        recyclerViewBtnNoibat.setLayoutManager(layoutManager2);
        RecyclerViewAdapterHomeCategory myAdapterBtnNoibat = new RecyclerViewAdapterHomeCategory(this, lstBtnNoibat);
        recyclerViewBtnNoibat.setAdapter(myAdapterBtnNoibat);

        LinearLayoutManager layoutManagerNoibat = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewNoibat = (RecyclerView) root.findViewById(R.id.recyclerView_home_noibat);
        recyclerViewNoibat.setLayoutManager(layoutManagerNoibat);

        reference = FirebaseDatabase.getInstance().getReference().child("NoiBat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstNoibat = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstNoibat.add(p);
                }
                RecyclerViewAdapter myAdapterNoibat = new RecyclerViewAdapter(getContext(), lstNoibat);
                recyclerViewNoibat.setAdapter(myAdapterNoibat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerDienthoai = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewDienthoai = (RecyclerView) root.findViewById(R.id.recyclerView_home_dienthoai);
        recyclerViewDienthoai.setLayoutManager(layoutManagerDienthoai);

        refDienthoai = FirebaseDatabase.getInstance().getReference().child("Product").child("Điện thoại - Máy tính");
        refDienthoai.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstDienthoai = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstDienthoai.add(p);
                }
                RecyclerViewAdapter myAdapterDienthoai = new RecyclerViewAdapter(getContext(), lstDienthoai);
                recyclerViewDienthoai.setAdapter(myAdapterDienthoai);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerQuanao = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewQuanao = (RecyclerView) root.findViewById(R.id.recyclerView_home_quanao);
        recyclerViewQuanao.setLayoutManager(layoutManagerQuanao);

        refQuanao = FirebaseDatabase.getInstance().getReference().child("Product").child("Quần áo - Thời trang");
        refQuanao.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstQuanao = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstQuanao.add(p);
                }
                RecyclerViewAdapter myAdapterQuanao = new RecyclerViewAdapter(getContext(), lstQuanao);
                recyclerViewQuanao.setAdapter(myAdapterQuanao);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerNhacua = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewNhacua = (RecyclerView) root.findViewById(R.id.recyclerView_home_nhacua);
        recyclerViewNhacua.setLayoutManager(layoutManagerNhacua);

        refNhacua = FirebaseDatabase.getInstance().getReference().child("Product").child("Nhà cửa - Đồ gia dụng");
        refNhacua.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstNhacua = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstNhacua.add(p);
                }
                RecyclerViewAdapter myAdapterNhacua = new RecyclerViewAdapter(getContext(), lstNhacua);
                recyclerViewNhacua.setAdapter(myAdapterNhacua);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerLamdep = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewLamdep = (RecyclerView) root.findViewById(R.id.recyclerView_home_lamdep);
        recyclerViewLamdep.setLayoutManager(layoutManagerLamdep);

        refLamdep = FirebaseDatabase.getInstance().getReference().child("Product").child("Sức khỏe - Làm đẹp");
        refLamdep.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstLamdep = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstLamdep.add(p);
                }
                RecyclerViewAdapter myAdapterLamdep = new RecyclerViewAdapter(getContext(), lstLamdep);
                recyclerViewLamdep.setAdapter(myAdapterLamdep);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerSach = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewSach = (RecyclerView) root.findViewById(R.id.recyclerView_home_sach);
        recyclerViewSach.setLayoutManager(layoutManagerSach);

        refSach = FirebaseDatabase.getInstance().getReference().child("Product").child("Sách - Văn phòng phẩm");
        refSach.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstSach = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstSach.add(p);
                }
                RecyclerViewAdapter myAdapterSach = new RecyclerViewAdapter(getContext(), lstSach);
                recyclerViewSach.setAdapter(myAdapterSach);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerDaxem = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewDaxem = (RecyclerView) root.findViewById(R.id.recyclerView_home_daxem);
        recyclerViewDaxem.setLayoutManager(layoutManagerDaxem);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        refDaxem = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid()).child("YeuThich");
        refDaxem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstDaxem = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstDaxem.add(p);
                }
                RecyclerViewAdapter myAdapterDaxem = new RecyclerViewAdapter(getContext(), lstDaxem);
                recyclerViewDaxem.setAdapter(myAdapterDaxem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        lstDanhmuc = new ArrayList<>();
        lstDanhmuc.add(new Category2("Điện thoại - Máy tính", R.drawable.dienthoaimaytinh));
        lstDanhmuc.add(new Category2("Thời trang", R.drawable.thoitrang1));
        lstDanhmuc.add(new Category2("Nhà cửa - Nội thất", R.drawable.xoong));
        lstDanhmuc.add(new Category2("Làm đẹp - Sức khỏe", R.drawable.son));
        lstDanhmuc.add(new Category2("Sách", R.drawable.khuyenhoc));
        lstDanhmuc.add(new Category2("Đồ chơi, Mẹ và bé", R.drawable.mevabe));
        lstDanhmuc.add(new Category2("Thể thao - Dã ngoại", R.drawable.thethao));
        lstDanhmuc.add(new Category2("Ô tô - Xe máy", R.drawable.xemay));
        lstDanhmuc.add(new Category2("Voucher - Dịch vụ", R.drawable.voucher));

        GridLayoutManager layoutManagerDanhmuc = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewDanhmuc = (RecyclerView) root.findViewById(R.id.recyclerView_home_danhmuc);
        recyclerViewDanhmuc.setLayoutManager(layoutManagerDanhmuc);
        RecyclerViewAdapterDanhmuc myAdapterDanhmuc = new RecyclerViewAdapterDanhmuc(getContext(), lstDanhmuc);
        recyclerViewDanhmuc.setAdapter(myAdapterDanhmuc);
//
        lstBtnTimkiem = new ArrayList<>();
        lstBtnTimkiem.add(new Category("Khẩu trang"));
        lstBtnTimkiem.add(new Category("Iphone"));
        lstBtnTimkiem.add(new Category("Nước rửa tay"));
        lstBtnTimkiem.add(new Category("Tai nghe bluetooth"));
        lstBtnTimkiem.add(new Category("Mì tôm"));
        lstBtnTimkiem.add(new Category("Balo"));
        lstBtnTimkiem.add(new Category("Đồng hồ thông minh"));

        LinearLayoutManager layoutManagerTimkiem = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewBtnTimkiem = (RecyclerView) root.findViewById(R.id.recyclerView_home_timkiem);
        recyclerViewBtnTimkiem.setLayoutManager(layoutManagerTimkiem);
        RecyclerViewAdapterHomeCategory myAdapterBtnTimkiem = new RecyclerViewAdapterHomeCategory(this, lstBtnTimkiem);
        recyclerViewBtnTimkiem.setAdapter(myAdapterBtnTimkiem);
        return root;
    }
}
