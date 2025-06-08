package com.example.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class SubThreadCreateHandler extends Thread {

    String TAG = this.getClass().getSimpleName();

    public SubHandler subHandler;

    public class SubHandler extends Handler {

        public SubHandler(Looper looper) {

        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "handleMessage");
            Bundle data = msg.getData();
            Log.i(TAG, "get String : " + data.getString("key1"));
        }
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        subHandler = new SubHandler(Looper.myLooper());
        Looper.loop();
    }
}
