package com.example.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OrderReceiver01 extends BroadcastReceiver {

    String TAG = OrderReceiver01.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive");
        String action = intent.getAction();
        if ("RULE".equals(action)) {
            Log.i(TAG, "data : " + getResultData());
            setResultData(getResultData() + "01");
        }
    }
}
