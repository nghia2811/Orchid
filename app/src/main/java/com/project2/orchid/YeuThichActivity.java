package com.project2.orchid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class YeuThichActivity extends AppCompatActivity {
    DatabaseReference reference, delete;
    ArrayList<Product> lstYeuthich;
    ImageButton back;
    FirebaseAuth mAuth;
    ConstraintLayout constraintLayout;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_thich);

        constraintLayout = findViewById(R.id.layout_yeuthich);
        back = findViewById(R.id.yeuthich_back);

        LinearLayoutManager layoutManagerGioHang = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerViewGioHang = (RecyclerView) findViewById(R.id.recyclerView_yeuthich);
        recyclerViewGioHang.setLayoutManager(layoutManagerGioHang);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid()).child("YeuThich");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstYeuthich = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstYeuthich.add(p);
                }
                final RecyclerViewAdapterGioHang myAdapterGioHang = new RecyclerViewAdapterGioHang(YeuThichActivity.this, lstYeuthich);
                recyclerViewGioHang.setAdapter(myAdapterGioHang);
                myAdapterGioHang.notifyDataSetChanged();

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(YeuThichActivity.this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        final int position = viewHolder.getAdapterPosition();
                        final Product item = myAdapterGioHang.getData().get(position);

                        StorageReference photoRef = storage.getReferenceFromUrl(item.getHinhAnh());
                        delete = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid()).child("YeuThich").child(item.getTen());
                        delete.removeValue();

                        photoRef.delete();
                        myAdapterGioHang.removeItem(position);
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
                Toast.makeText(YeuThichActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
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
