package com.example.sampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        new Handler().postDelayed(() -> {
            if (currentUser == null) {
                Intent loginIntent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
                startActivity(loginIntent);
            }  else {
                Intent registerIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(registerIntent);
            }
            finish();
        }, 3000);

    }

}