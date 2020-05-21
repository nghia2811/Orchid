package com.project2.orchid.Animation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.project2.orchid.Activity.LoginActivity;
import com.project2.orchid.MainActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(700);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (auth.getCurrentUser() == null)
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    else
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
        };
        timer.start();
    }
}
