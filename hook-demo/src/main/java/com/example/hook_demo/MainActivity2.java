package com.example.hook_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import org.android.spdy.SessionCb;
import org.android.spdy.SessionInfo;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.SuperviseConnectInfo;

public class MainActivity2 extends ComponentActivity {

    private static final String TAG = MainActivity2.class.getSimpleName();

    private TextView simpleText;

    private Button submitRequestBtn;

    static {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        simpleText = findViewById(R.id.simpleText);
        submitRequestBtn = findViewById(R.id.submitRequestBtn);
        submitRequestBtn.setOnClickListener(v -> {
            this.submitRequest(this.getApplicationContext());
        });
    }

    private void submitRequest(Context context) {
        try {
            SpdyAgent spdyAgent = SpdyAgent.getInstance(context, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
            spdyAgent.switchAccsServer(1);
            //
            SessionInfo sessionInfo = new SessionInfo("2409:8c54:b010:3:3:0:0:3d6", 80, "https://hudong.alicdn.com_21646297", null, 0, 1777769554329L, new SessionCb() {
                @Override
                public void bioPingRecvCallback(SpdySession p0, int p1) {
                    Log.i(TAG, "bioPingRecvCallback p0 :" + p0 + ", p1 :" + p1);
                }

                @Override
                public byte[] getSSLMeta(SpdySession p0) {
                    Log.i(TAG, "getSSLMeta p0 :" + p0);
                    return new byte[0];
                }

                @Override
                public int putSSLMeta(SpdySession p0, byte[] p1) {
                    Log.i(TAG, "putSSLMeta p0 :" + p0 + ", p1 :" + p1);
                    return 0;
                }

                @Override
                public void spdyCustomControlFrameFailCallback(SpdySession p0, Object p1, int p2, int p3) {
                    Log.i(TAG, "spdyCustomControlFrameFailCallback p0 :" + p0 + ", p1 :" + p1 + ", p2 :" + p2 + ", p3 :" + p3);
                }

                @Override
                public void spdyCustomControlFrameRecvCallback(SpdySession p0, Object p1, int p2, int p3, int p4, int p5, byte[] p6) {
                    Log.i(TAG, "spdyCustomControlFrameRecvCallback p0 :" + p0 + ", p1 :" + p1 + ", p2 :" + p2 + ", p3 :" + p3 + ", p4 :" + p4 + ", p5 :" + p5 + ", p6 :" + p6);
                }

                @Override
                public void spdyPingRecvCallback(SpdySession p0, long p1, Object p2) {
                    Log.i(TAG, "spdyPingRecvCallback p0 :" + p0 + ", p1 :" + p1 + ", p2 :" + p2);
                }

                @Override
                public void spdySessionCloseCallback(SpdySession p0, Object p1, SuperviseConnectInfo p2, int p3) {
                    Log.i(TAG, "spdySessionCloseCallback p0 :" + p0 + ", p1 :" + p1 + ", p2 :" + p2 + ", p3 :" + p3);
                }

                @Override
                public void spdySessionConnectCB(SpdySession p0, SuperviseConnectInfo p1) {
                    Log.i(TAG, "spdySessionConnectCB p0 :" + p0 + ", p1 :" + p1);
                }

                @Override
                public void spdySessionFailedError(SpdySession p0, int p1, Object p2) {
                    Log.i(TAG, "spdySessionFailedError p0 :" + p0 + ", p1 :" + p1 + ", p2 :" + p2);
                }
            }, 4232);
            sessionInfo.setConnectionTimeoutMs(6000);
            sessionInfo.setConnectIndex(0);
            sessionInfo.setMultiPathCompensateEnable(true);
            sessionInfo.setMultiPathParallelAddSpeedEnable(true);
            sessionInfo.setPubKeySeqNum(1);
            sessionInfo.setTryForceCellular(false);
            SpdySession spdySession = spdyAgent.createSession(sessionInfo);
            int result = spdySession.submitRequestN(
                    487600548928L,
                    "https://hudong.alicdn.com/api/data/v2/15b808d1dfa24b65a8bad0d2df922638.js",
                    (byte) 3,
                    new String[]{":host","hudong.alicdn.com",":path","/api/data/v2/15b808d1dfa24b65a8bad0d2df922638.js",":method","GET",":scheme","https",":version","HTTP/1.1"},
                    new byte[0],
                    true,
                    1,
                    -1,
                    7200,
                    0,
                    0
            );
            String text = "crack submitRequestN result : " + result;
            simpleText.setText(text);
            Log.i(TAG, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}