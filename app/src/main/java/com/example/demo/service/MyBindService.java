package com.example.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyBindService extends Service {

    String TAG = MyBindService.class.getSimpleName();

    private final MyBinder _myBinder = new MyBinder();

    public class MyBinder extends Binder {
        public MyBinder() {
            Log.i(TAG, "MyBinder");
        }

        public MyBindService getServiceBinder() {
            return MyBindService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return _myBinder;
    }
}
