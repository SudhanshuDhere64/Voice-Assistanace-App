package com.codes.pratik.voiceassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                (findViewById(R.id.splashlogo)).setVisibility(View.VISIBLE);
                (findViewById(R.id.splashtxt)).setVisibility(View.VISIBLE);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        },3100);


    }
}