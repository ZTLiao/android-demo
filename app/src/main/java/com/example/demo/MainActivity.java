package com.example.demo;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class MainActivity extends ComponentActivity {

    Button btn_start, btn_startForResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.sample_text);
        Button btn = findViewById(R.id.btn_test);
        btn.setText("按钮");
        btn.setOnClickListener(v -> {
            tv.setText("按钮被点击...");
        });
        btn_start = findViewById(R.id.btn_startActivity);
        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubActivity02.class);
            startActivity(intent);
        });
        btn_startForResult = findViewById(R.id.btn_startActivityGetResult);
        btn_startForResult.setOnClickListener(v -> {

        });
    }
}
