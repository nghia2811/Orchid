package com.project2.orchid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new HomeFragment()).commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_trangchu: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                        break;
                    }
                    case R.id.nav_danhmuc: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ListFragment()).commit();
                        break;
                    }
                    case R.id.nav_tienich: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new TienIchFragment()).commit();
                        break;
                    }
                    case R.id.nav_thongbao: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ThongBaoFragment()).commit();
                        break;
                    }
                    case R.id.nav_nguoidung: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new UserFragment()).commit();
                        break;
                    }
                }
                return true;
            }
        });
    }
}
