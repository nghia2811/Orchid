package com.project2.orchid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project2.orchid.MainActivity;
import com.project2.orchid.Object.Product;
import com.project2.orchid.R;
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back, giohang, home, background;
    Button search;
    TextView category, danhmuc;
    Query reference;
    ArrayList<Product> lstCategory;
    String danhMuc;
    RecyclerView recyclerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        back = findViewById(R.id.category_back);
        search = findViewById(R.id.category_search);
        giohang = findViewById(R.id.btn_category_giohang);
        home = findViewById(R.id.btn_category_home);
        category = findViewById(R.id.category_text);
        danhmuc = findViewById(R.id.category_danhmuc);
        background = findViewById(R.id.img_category);

        // Recieve data
        Intent intent = getIntent();
        danhMuc = intent.getExtras().getString("DanhMuc");

        search.setText(danhMuc);
        danhmuc.setText(danhMuc);

        back.setOnClickListener(this);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        category.setOnClickListener(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        giohang.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recyclerView_category);
        recyclerView.setLayoutManager(layoutManager);

        switch (danhMuc) {
            case "Điện thoại - Máy tính":
                background.setBackgroundResource(R.drawable.dienthoai);
                queryProduct();
                break;
            case "Nhà cửa - Đồ gia dụng":
                background.setBackgroundResource(R.drawable.nhacua);
                queryProduct();
                break;
            case "Quần áo - Thời trang":
                background.setBackgroundResource(R.drawable.thoitrang);
                queryProduct();
                break;
            case "Sức khỏe - Làm đẹp":
                background.setBackgroundResource(R.drawable.lamdep);
                queryProduct();
                break;
            case "Sách - Văn phòng phẩm":
                background.setBackgroundResource(R.drawable.sach);
                queryProduct();
                break;
        }
    }

    private void queryProduct() {
        reference = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("danhMuc").equalTo(danhMuc);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstCategory = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstCategory.add(p);
                }
                final RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(CategoryActivity.this, lstCategory);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CategoryActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.category_back:
                finish();
                break;
            case R.id.btn_category_giohang:
                intent = new Intent(CategoryActivity.this, GioHangActivity.class);
                startActivity(intent);
                break;
            case R.id.category_text:
                intent = new Intent(CategoryActivity.this, MainActivity.class);
                intent.putExtra("Selection", "List");
                startActivity(intent);
        }
    }
}
