package com.project2.orchid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class UserFragment extends Fragment {
    ImageButton gioHang;
    Button btnQuanli,btnYeuthich,btnDangxuat,btnMuasau;
    TextView email, ten, ngaydangki;
    ImageView img;
    FirebaseAuth mAuth;
    DatabaseReference ref;
    FirebaseStorage storage = FirebaseStorage.getInstance();

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

                UserFragment.LoadImage loadImage = new UserFragment.LoadImage(img);
                loadImage.execute(dataSnapshot.child("anhDaiDien").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "load thất bại", Toast.LENGTH_SHORT).show();
            }
        });

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
                Intent intent = new Intent(getActivity(), GioHangActivity.class);
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

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView img;
        public LoadImage(ImageView ivResult) {
            this.img = ivResult;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);
        }
    }

}