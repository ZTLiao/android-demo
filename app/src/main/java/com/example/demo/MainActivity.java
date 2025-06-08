package com.example.demo;


import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.example.demo.aidl.IMyAidlInterface;
import com.example.demo.client.BindProxy;
import com.example.demo.client.IStudentInterface;
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

    TextView tv;

    MyBindService.MyBinder myBinder;

    private MyHandler01 myHandler01;

    TextView tv_client_view;

    Button btn_bindServerService, btn_searchAge;

    IStudentInterface iStudentInterface;

    IMyAidlInterface iMyAidlInterface;

    static final Uri uri = Uri.parse("content://com.example.db.authority/user");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test01();
        test02();
        tv_client_view = findViewById(R.id.tv_client_view);
        btn_bindServerService = findViewById(R.id.btn_bindServerService);
        btn_searchAge = findViewById(R.id.btn_searchAge);
        btn_bindServerService.setOnClickListener(v -> {
            bindRemoteService();
        });
        btn_searchAge.setOnClickListener(v -> {
            Log.i(TAG, "search age");
            try {
                iMyAidlInterface.getStudentAge("tom");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            iStudentInterface = BindProxy.asInterface(iBinder);
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iMyAidlInterface = null;
        }
    };

    private void bindRemoteService() {
        String action = "android.intent.action.server.student";
        Intent intent = new Intent(action);
        intent.setPackage("com.example.demo.server");
        boolean bt = this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        Log.i(TAG, "bindRemoteService");
    }

    private void test02() {
        myHandler01 = new MyHandler01(this);
        SubThreadCreateHandler subThread = new SubThreadCreateHandler();
        subThread.start();
        findViewById(R.id.tv_async_view);
        tv.setOnClickListener(v -> {
            MyThread01 myThread01 = new MyThread01(myHandler01);
            myThread01.start();
            myHandler01.post(() -> {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    updateUI01();
                    updateUI02();
                    updateUI03();
                    tv.setText("i : " + i);
                }
            });
            Message message = Message.obtain();
            message.what = 99;
            Bundle bundle = new Bundle();
            bundle.putString("key1", "value1");
            message.setData(bundle);
            subThread.subHandler.sendMessage(message);
        });
    }

    public void updateUI01() {
        tv.setText("updateUI01");
    }

    public void updateUI02() {
        tv.setText("updateUI02");
    }

    public void updateUI03() {
        tv.setText("updateUI03");
    }

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

    private void test01() {
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
        btn_delete.setOnClickListener(v -> {
            ContentResolver contentResolver = getContentResolver();
            int count = contentResolver.delete(uri, "age < ?", new String[]{"18"});
            if (count > 0) {
                Log.i(TAG, "delete success");
            }
        });
        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("age", "66");
            ContentResolver contentResolver = getContentResolver();
            int count = contentResolver.update(uri, contentValues, "age < ?", new String[]{"18"});
            if (count > 0) {
                Log.i(TAG, "update success");
            }
        });
        btn_query = findViewById(R.id.btn_query);
        btn_query.setOnClickListener(v -> {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(uri, new String[]{"uid", "name", "age", "score"}, "age < ?", new String[]{"17"}, null);
            if (cursor == null) {
                return;
            }
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int uid = cursor.getInt(cursor.getColumnIndex("uid"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex("age"));
                @SuppressLint("Range") String score = cursor.getString(cursor.getColumnIndex("score"));
                Log.i(TAG, "uid : " + uid + ", name : " + name + ", age : " + age + ", score : " + score);
            }
        });
        TextView tv_downloadResult = findViewById(R.id.tv_downloadResult);
        findViewById(R.id.btn_startDownload).setOnClickListener(v -> {
            DownloadZip downloadZip = new DownloadZip(tv_downloadResult);
            downloadZip.execute("https://res.dawalive.com/download/nvm-setup.zip");
        });
        findViewById(R.id.btn_startUnzip).setOnClickListener(v -> {
            UnzipFile downloadZip = new UnzipFile(tv_downloadResult);
            downloadZip.execute("");
        });
    }
}
