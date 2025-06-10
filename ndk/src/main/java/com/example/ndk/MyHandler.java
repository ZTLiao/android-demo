package com.example.ndk;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyHandler extends Handler {

    String TAG = this.getClass().getSimpleName();

    private MainActivity mainActivity;

    public MyHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1: {
                mainActivity.updateUI01(msg.getData().getString("fp"));
                break;
            }
            default: {
                Log.i(TAG, "known message " + msg.what);
            }
        }
    }
}
