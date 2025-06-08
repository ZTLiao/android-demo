package com.example.demo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyHandler01 extends Handler {

    String TAG = this.getClass().getSimpleName();

    private MainActivity mainActivity;

    public MyHandler01(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

    }
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        Log.i(TAG, "handleMessage");
        switch (msg.what) {
            case 1: {
                mainActivity.updateUI01();
                break;
            }
            case 2: {
                mainActivity.updateUI01();
                break;
            }
            case 3: {
                mainActivity.updateUI01();
                break;
            }
        }
    }


}
