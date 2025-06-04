package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class SubActivity03 extends ComponentActivity {

    TextView tv_setResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub03);
        tv_setResult = findViewById(R.id.tv_setResult);
        tv_setResult.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(SubActivity03.this, MainActivity.class);
            intent.putExtra("key1", "这是结果01，来自SubActivity03");
            setResult(234, intent);
            SubActivity03.this.finish();
        });
    }
}
