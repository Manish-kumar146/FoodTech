package com.example.foodtech.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtech.Login;
import com.example.foodtech.R;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        }, 4000);


    }
}
