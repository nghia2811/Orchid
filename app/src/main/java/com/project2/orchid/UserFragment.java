package com.project2.orchid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {
    ImageView gioHang;
    Button btnQuanli,btnYeuthich,btnDangxuat,btnMuasau;
    TextView email, ten, ngaydangki;
    ImageView img;
    FirebaseAuth mAuth;
    DatabaseReference ref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        btnMuasau = root.findViewById(R.id.user_muasau);
        btnYeuthich = root.findViewById(R.id.user_yeuthich);
        btnQuanli = (Button) root.findViewById(R.id.user_quanli);
        gioHang = root.findViewById(R.id.btn_user_giohang);
        email = root.findViewById(R.id.user_email);
        btnDangxuat = root.findViewById(R.id.user_dangxuat);
        ten = root.findViewById(R.id.user_name);
        ngaydangki = root.findViewById(R.id.user_date);
        img = root.findViewById(R.id.user_img1);

        loadData();

        gioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GioHangActivity.class);
                startActivity(intent);
            }
        });

        btnQuanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        btnYeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YeuThichActivity.class);
                startActivity(intent);
            }
        });

        btnMuasau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DaBanActivity.class);
                startActivity(intent);
            }
        });

        btnDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return root;
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Setting values
                ten.setText(dataSnapshot.child("ten").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                ngaydangki.setText(dataSnapshot.child("diaChi").getValue().toString());

                Glide.with(getActivity()).load(dataSnapshot.child("anhDaiDien").getValue().toString())
                        .placeholder(R.drawable.noimage).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "load thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}