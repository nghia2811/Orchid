package com.project2.orchid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SearchView searchText;
    ImageButton back, gioHang;
    DatabaseReference reference;
    ArrayList<Product> lstSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText =  findViewById(R.id.edt_search);
        back =  findViewById(R.id.btn_search_back);
        gioHang =findViewById(R.id.btn_search_giohang);
        lstSearch = new ArrayList<Product>();

        if(searchText != null){
            searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        }

        gioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManagerGioHang = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerViewGioHang = (RecyclerView) findViewById(R.id.recyclerView_search);
        recyclerViewGioHang.setLayoutManager(layoutManagerGioHang);

        reference = FirebaseDatabase.getInstance().getReference().child("Product").child("Điện thoại - Máy tính");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstSearch.add(p);
                }

                RecyclerViewAdapterGioHang myAdapterGioHang = new RecyclerViewAdapterGioHang(SearchActivity.this, lstSearch);
                recyclerViewGioHang.setAdapter(myAdapterGioHang);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Product").child("Quần áo - Thời trang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstSearch.add(p);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("Product").child("Nhà cửa - Đồ gia dụng");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstSearch.add(p);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("Product").child("Sức khỏe - Làm đẹp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstSearch.add(p);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("Product").child("Sách - Văn phòng phẩm");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstSearch.add(p);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        Collections.sort(lstSearch, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return (p1.getTen().compareTo(p2.getTen()));
            }
        });
    }

    public void search(String s){
        ArrayList<Product> myList =new ArrayList<>();
        for(Product object: lstSearch){
            if(object.getTen().toLowerCase().contains(s.toLowerCase()) || object.getNhaSanXuat().toLowerCase().contains(s.toLowerCase())){
                myList.add(object);
            }
        }
        LinearLayoutManager layoutManagerGioHang = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerViewGioHang = (RecyclerView) findViewById(R.id.recyclerView_search);
        recyclerViewGioHang.setLayoutManager(layoutManagerGioHang);
        RecyclerViewAdapterGioHang myAdapterGioHang = new RecyclerViewAdapterGioHang(SearchActivity.this, myList);
        recyclerViewGioHang.setAdapter(myAdapterGioHang);
    }
}
