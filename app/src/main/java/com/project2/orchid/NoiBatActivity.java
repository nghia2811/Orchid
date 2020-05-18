package com.project2.orchid;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project2.orchid.object.Product;

import java.util.ArrayList;

public class NoiBatActivity extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<Product> lstYeuthich;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_bat);

        back = findViewById(R.id.noibat_back);

        LinearLayoutManager layoutManagerGioHang = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerViewGioHang = (RecyclerView) findViewById(R.id.recyclerView_noibat);
        recyclerViewGioHang.setLayoutManager(layoutManagerGioHang);

        reference = FirebaseDatabase.getInstance().getReference().child("NoiBat");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstYeuthich = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstYeuthich.add(p);
                }
                final RecyclerViewAdapterGioHang myAdapterGioHang = new RecyclerViewAdapterGioHang(NoiBatActivity.this, lstYeuthich);
                recyclerViewGioHang.setAdapter(myAdapterGioHang);
                myAdapterGioHang.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NoiBatActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
