package com.project2.orchid;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project2.orchid.object.Product;

import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    DatabaseReference reference, delete;
    ArrayList<Product> lstGioHang = new ArrayList<>();
    ImageView back;
    FirebaseAuth mAuth;
    ConstraintLayout constraintLayout;
    RecyclerView recyclerViewGioHang;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    boolean j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        back = findViewById(R.id.giohang_back);
        constraintLayout = findViewById(R.id.layout_giohang);
        recyclerViewGioHang = findViewById(R.id.recyclerView_giohang);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewGioHang.setLayoutManager(layoutManager2);
        final RecyclerViewAdapterGioHang myAdapter = new RecyclerViewAdapterGioHang(GioHangActivity.this, lstGioHang);


        reference = FirebaseDatabase.getInstance().getReference().child("Cart").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstGioHang.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstGioHang.add(p);
                }
                final RecyclerViewAdapterGioHang myAdapter = new RecyclerViewAdapterGioHang(GioHangActivity.this, lstGioHang);
                recyclerViewGioHang.setAdapter(myAdapter);

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(GioHangActivity.this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        final int position = viewHolder.getAdapterPosition();
                        final Product item = myAdapter.getData().get(position);
                        j = true;

                        myAdapter.removeItem(position);
                        Snackbar snackbar = Snackbar
                                .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                j = false;
                                myAdapter.restoreItem(item, position);
                                recyclerViewGioHang.scrollToPosition(position);
                            }
                        });
                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (j) {
                                    StorageReference photoRef = storage.getReferenceFromUrl(item.getHinhAnh());
                                    delete = FirebaseDatabase.getInstance().getReference().child("Cart").child(currentUser.getUid()).child(item.getTen());
                                    delete.removeValue();

                                    photoRef.delete();
                                }
                            }
                        }, 2900);

                    }
                };
                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
                itemTouchhelper.attachToRecyclerView(recyclerViewGioHang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GioHangActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
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
