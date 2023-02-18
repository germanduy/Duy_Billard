package com.example.billiardshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import com.example.billiardshop.constant.GlobalFuntion;
import com.example.billiardshop.R;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startMainActivity();
    }

    private void startMainActivity() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GlobalFuntion.startActivity(SplashActivity.this, MainActivity.class);
                finish();
            }
        }, 2000);

    }
}