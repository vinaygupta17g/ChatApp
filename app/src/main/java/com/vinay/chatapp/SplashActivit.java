package com.vinay.chatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        Thread thrd = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(SplashActivit.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thrd.start();
    }
}