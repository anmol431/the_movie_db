package com.themovie.db.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.themovie.db.app.databinding.ActivitySplashBinding;


public class SplashActivity extends AppCompatActivity {
    public final int DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(() -> {
            setScreen();
            finish();
        }, DELAY_TIME);
    }

    private void setScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }
}