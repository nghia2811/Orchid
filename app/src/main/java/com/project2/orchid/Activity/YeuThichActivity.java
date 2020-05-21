package com.project2.orchid.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.project2.orchid.Animation.SwipeToDeleteCallback;
import com.project2.orchid.MainActivity;
import com.project2.orchid.Object.Product;
import com.project2.orchid.R;
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterGioHang;

import java.util.ArrayList;

import static android.view.View.GONE;

public class YeuThichActivity extends AppCompatActivity {
    DatabaseReference reference, delete;
    ArrayList<Product> lstYeuthich;
    ImageView back;
    FirebaseAuth mAuth;
    ConstraintLayout layout;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    ProgressBar loadingView;
    LinearLayout linearLayout;
    Button tieptuc;
    RecyclerView recyclerView;
    boolean j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_thich);

        layout = findViewById(R.id.layout_yeuthich);
        back = findViewById(R.id.yeuthich_back);
        loadingView = findViewById(R.id.loading_view_yeuthich);
        linearLayout = findViewById(R.id.layoutyt_noProduct);
        tieptuc = findViewById(R.id.yeuthich_tieptuc);
        recyclerView = findViewById(R.id.recyclerView_yeuthich);

        loadData();

        tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YeuThichActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        LinearLayoutManager layoutManagerGioHang = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerGioHang);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("Favourite").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstYeuthich = new ArrayList<Product>();
                loadingView.setVisibility(GONE);
                if (dataSnapshot.exists()) linearLayout.setVisibility(GONE);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    lstYeuthich.add(p);
                }
                final RecyclerViewAdapterGioHang myAdapter = new RecyclerViewAdapterGioHang(YeuThichActivity.this, lstYeuthich);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(YeuThichActivity.this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        final int position = viewHolder.getAdapterPosition();
                        final Product item = myAdapter.getData().get(position);
                        j = true;

                        myAdapter.removeItem(position);
                        Snackbar snackbar = Snackbar
                                .make(layout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                j = false;
                                myAdapter.restoreItem(item, position);
                                recyclerView.scrollToPosition(position);
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
                                    delete = FirebaseDatabase.getInstance().getReference().child("Favourite").child(currentUser.getUid()).child(item.getTen());
                                    delete.removeValue();

                                    photoRef.delete();
                                }
                            }
                        }, 2900);
                    }
                };
                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
                itemTouchhelper.attachToRecyclerView(recyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(YeuThichActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
