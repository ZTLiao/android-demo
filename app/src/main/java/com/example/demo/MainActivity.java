package com.example.demo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.example.demo.service.MyService01;
import com.example.demo.service.MyBindService;

public class MainActivity extends ComponentActivity {

    String TAG = MainActivity.class.getSimpleName();

    Button btn_start, btn_startForResult;

    Button btn_startService, btn_stopService;
    
    Button btn_bindService, btn_unbindService;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == requestCode && resultCode == 234) {
            String ret = data.getStringExtra("key1");
            Log.i(MainActivity.class.getSimpleName(), ret);
        }
    }
    
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
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), res -> {
            Log.i(TAG, res.getData().getStringExtra("key1"));
        });
        btn_startForResult = findViewById(R.id.btn_startActivityGetResult);
        btn_startForResult.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubActivity02.class);
            startActivityForResult(intent, 234);
            //launcher.launch(intent);
        });
        btn_startService = findViewById(R.id.btn_startService);
        btn_startService.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService01.class);
            startService(intent);
        });
        btn_stopService = findViewById(R.id.btn_stopService);
        btn_stopService.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService01.class);
            stopService(intent);
        });
        btn_bindService = findViewById(R.id.btn_bindService);
        btn_bindService.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyBindService.class);
        });
        btn_unbindService = findViewById(R.id.btn_unbindService);
        btn_unbindService.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyBindService.class);
        });

    }
}
