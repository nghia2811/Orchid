package com.project2.orchid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    DatabaseReference reference, delete;
    ArrayList<Product> lstGioHang = new ArrayList<>();
    ImageButton back;
    FirebaseAuth mAuth;
    ConstraintLayout constraintLayout;
    RecyclerView recyclerViewGioHang;
    RecyclerViewAdapterGioHang myAdapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();

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

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid()).child("GioHang");
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
                myAdapter.notifyDataSetChanged();

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(GioHangActivity.this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        final int position = viewHolder.getAdapterPosition();
                        final Product item = myAdapter.getData().get(position);

                        StorageReference photoRef = storage.getReferenceFromUrl(item.getHinhAnh());
                        delete = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid()).child("GioHang").child(item.getTen());
                        delete.removeValue();

                        photoRef.delete();
                        myAdapter.removeItem(position);
                        Snackbar snackbar = Snackbar
                                .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
//                        snackbar.setAction("UNDO", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                myAdapter.restoreItem(item, position);
//                                recyclerViewGioHang.scrollToPosition(position);
//                            }
//                        });
//
//                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();

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
