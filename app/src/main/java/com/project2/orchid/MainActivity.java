package com.project2.orchid;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private boolean doubleClick = false;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle getSelection = getIntent().getExtras();
        if (getSelection == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new HomeFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ListFragment()).commit();
        }

        bottomNav = findViewById(R.id.bottom_nav);
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

    @Override
    public void onBackPressed() {
        if (doubleClick)
            finish();
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        doubleClick = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClick = false;
            }
        }, 2000);
    }
}
