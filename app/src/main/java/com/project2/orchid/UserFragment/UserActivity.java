package com.project2.orchid.UserFragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project2.orchid.Object.Product;
import com.project2.orchid.R;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    TextView email, diachi, date, id, giohang, yeuthich, daban, name;
    ImageView avatar;
    FirebaseAuth mAuth;
    DatabaseReference ref;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = findViewById(R.id.text_name);
        giohang = findViewById(R.id.text_giohang);
        yeuthich = findViewById(R.id.text_yeuthich);
        daban = findViewById(R.id.text_khohang);
        email = findViewById(R.id.text_email);
        diachi = findViewById(R.id.text_diachi);
        date = findViewById(R.id.text_date);
        id = findViewById(R.id.text_id);
        avatar = findViewById(R.id.avatar);

        loadData();
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("ten").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                date.setText(dataSnapshot.child("date").getValue().toString());
                id.setText(dataSnapshot.child("uid").getValue().toString());

                Glide.with(UserActivity.this).load(dataSnapshot.child("anhDaiDien").getValue().toString())
                        .placeholder(R.drawable.noimage).into(avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Load thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("ten").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                date.setText(dataSnapshot.child("date").getValue().toString());
                id.setText(dataSnapshot.child("uid").getValue().toString());
                diachi.setText(dataSnapshot.child("diaChi").getValue().toString());

                Glide.with(UserActivity.this).load(dataSnapshot.child("anhDaiDien").getValue().toString())
                        .placeholder(R.drawable.noimage).into(avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Load thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Product> full = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    full.add(p);
                }
                giohang.setText(Integer.toString(full.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("Favourite").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Product> full = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    full.add(p);
                }
                yeuthich.setText(Integer.toString(full.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        query = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("nguoiBan").equalTo(currentUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Product> full = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);
                    full.add(p);
                }
                daban.setText(Integer.toString(full.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
