package com.example.demo;


import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.example.demo.receiver.MyReceiver;
import com.example.demo.receiver.OrderReceiver01;
import com.example.demo.receiver.OrderReceiver02;
import com.example.demo.receiver.OrderReceiver03;
import com.example.demo.service.MyService01;
import com.example.demo.service.MyBindService;

public class MainActivity extends ComponentActivity {

    String TAG = MainActivity.class.getSimpleName();

    Button btn_start, btn_startForResult;

    Button btn_startService, btn_stopService;
    
    Button btn_bindService, btn_unbindService;

    Button btn_sendBroadcast, btn_sendOrderBroadcast;

    Button btn_insert, btn_delete, btn_update, btn_query;

    MyBindService.MyBinder myBinder;

    static final Uri uri = Uri.parse("content://com.example.db.authority/user");

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
        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (iBinder instanceof MyBindService.MyBinder) {
                    myBinder = (MyBindService.MyBinder) iBinder;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(TAG, "onServiceDisconnected");
            }
        };
        btn_bindService = findViewById(R.id.btn_bindService);
        btn_bindService.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyBindService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        });
        btn_unbindService = findViewById(R.id.btn_unbindService);
        btn_unbindService.setOnClickListener(v -> {
            try {
                unbindService(conn);
            } catch (Exception e) {
                Log.e(TAG, "unBindService", e);
            }
        });
        btn_sendBroadcast = findViewById(R.id.btn_broadcast);
        btn_sendBroadcast.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyReceiver.class);
            intent.setAction("RULE");
            intent.putExtra("key2", "value2");
            sendBroadcast(intent);
        });
        OrderReceiver01 orderReceiver01 = new OrderReceiver01();
        OrderReceiver02 orderReceiver02 = new OrderReceiver02();
        OrderReceiver03 orderReceiver03 = new OrderReceiver03();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("RULE");
        registerReceiver(orderReceiver01, intentFilter);
        registerReceiver(orderReceiver02, intentFilter);
        registerReceiver(orderReceiver03, intentFilter);
        btn_sendOrderBroadcast = findViewById(R.id.btn_orderBroadcast);
        btn_sendOrderBroadcast.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("RULE");
            String data = "order broadcast";
            Bundle extra = new Bundle();
            sendOrderedBroadcast(intent, null, new MyReceiver(), null, 0, data, extra);
        });
        btn_insert = findViewById(R.id.btn_insert);
        btn_insert.setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("uid", 3);
            contentValues.put("name", "john");
            contentValues.put("age", "20");
            contentValues.put("score", "78");
            ContentResolver contentResolver = getContentResolver();
            contentResolver.insert(uri, contentValues);
        });
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);
        btn_query = findViewById(R.id.btn_query);
    }
}
