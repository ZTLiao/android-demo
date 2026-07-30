package com.example.hook_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import com.alibaba.fastjson.JSONObject;

import org.android.spdy.SessionCb;
import org.android.spdy.SessionInfo;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyStreamContext;
import org.android.spdy.SpdyVersion;
import org.android.spdy.Spdycb;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;

import java.util.Map;

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
            SpdyAgent spdyAgent = SpdyAgent.getInstance(context, SpdyVersion.SPDY3, SpdySessionKind.WIFI_SESSION);
            spdyAgent.switchAccsServer(1);
            SessionCb sessionCb = new SessionCb() {
                @Override
                public void bioPingRecvCallback(SpdySession p0, int p1) {
                    Log.i(TAG, "bioPingRecvCallback p0 :" + p0 + ", p1 :" + p1);
                }

                @Override
                public byte[] getSSLMeta(SpdySession p0) {
                    Log.i(TAG, "getSSLMeta p0 :" + p0);
                    return new byte[0];
//                    return new byte[] {
//                            (byte) -124, (byte) -33, (byte) -10, (byte) 105, 0, 0, 0, 0, (byte) 116, 0, 0, 0, 0, 0, 56, 64, 1, 0, 26, 0, 9, 0, (byte) 114, 0, (byte) 87, 0, 42, 0, (byte) -114, 0, (byte) -37, 0, (byte) -18, (byte) -104, (byte) -61, (byte) 117, (byte) 83, (byte) 78, (byte) -86, (byte) -45, (byte) 81, (byte) -90, 0, (byte) 127, (byte) -75, 56, (byte) -83, (byte) -125, 11, 42, 47, (byte) 102, (byte) -112, 55, 9, 20, (byte) -15, 40, (byte) 82, (byte) -90, (byte) -59, (byte) 110, 35, (byte) -92, 2, (byte) -29, (byte) -67, (byte) -41, (byte) -81, (byte) 108, (byte) 76, 35, 26, (byte) -48, (byte) 115, (byte) -33, (byte) -20, 67, 50, (byte) -100, (byte) -104, (byte) -73, 14, (byte) -109, 15, (byte) 92, (byte) 76, (byte) -18, (byte) -27, (byte) 81, (byte) 113, 31, (byte) -110, 68, (byte) 126, (byte) 122, 8, (byte) 104, (byte) -22, (byte) 76, (byte) 94, (byte) 104, (byte) -91, (byte) -102, (byte) 108, (byte) -92, (byte) 111, (byte) -102, (byte) -7, 37, 69, 9, 53, (byte) -99, (byte) -12, 46, (byte) -72, 36, 8, (byte) 124, (byte) 123, (byte) -127, (byte) -64, (byte) -104, 69, (byte) 98, 39, 59, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte) -107, 33, 37, 49, 46, 10, (byte) -42, (byte) 110, 5, (byte) 118, (byte) 88, (byte) -98, (byte) 121, (byte) -109, (byte) -118, (byte) -33, (byte) -28, (byte) -5, (byte) 92, (byte) -61, 17, (byte) -84, (byte) -127, 59, (byte) -9, (byte) -124, (byte) -57, 13, (byte) -69, 1, (byte) -22, (byte) -10, 0, 0, 0, 0
//                    };
                }

                @Override
                public int putSSLMeta(SpdySession p0, byte[] p1) {
                    Log.i(TAG, "putSSLMeta p0 :" + p0 + ", p1 :" + p1);
                    return 1;
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
                    Log.e(TAG, "spdySessionFailedError p0 : " + JSONObject.toJSONString(p0) + ", p1 :" + JSONObject.toJSONString(p2));
                    Log.e(TAG, "spdySessionFailedError p0 :" + p0 + ", p1 :" + p1 + ", p2 :" + p2);
                }
            };
            SessionInfo sessionInfo = new SessionInfo("2409:8c54:b010:3:3:0:0:3d6", 80, "https://hudong.alicdn.com_21646297", null, 0, System.currentTimeMillis(), sessionCb, 4232);
            sessionInfo.setConnectionTimeoutMs(6000);
            sessionInfo.setConnectIndex(0);
            sessionInfo.setMultiPathCompensateEnable(true);
            sessionInfo.setMultiPathParallelAddSpeedEnable(true);
            sessionInfo.setPubKeySeqNum(1);
            sessionInfo.setTryForceCellular(false);
            SpdySession spdySession = spdyAgent.createSession(sessionInfo);
            spdySession.putSpdyStreamCtx(new SpdyStreamContext(sessionCb, new Spdycb() {
                @Override
                public void spdyDataChunkRecvCB(SpdySession p0, boolean p1, long p2, SpdyByteArray p3, Object p4) {
                    Log.i(TAG, "spdyDataChunkRecvCB");
                }

                @Override
                public void spdyDataRecvCallback(SpdySession p0, boolean p1, long p2, int p3, Object p4) {
                    Log.i(TAG, "spdyDataRecvCallback");
                }

                @Override
                public void spdyDataSendCallback(SpdySession p0, boolean p1, long p2, int p3, Object p4) {
                    Log.i(TAG, "spdyDataSendCallback");
                }

                @Override
                public void spdyOnStreamResponse(SpdySession p0, long p1, Map p2, Object p3) {
                    Log.i(TAG, "spdyOnStreamResponse");
                }

                @Override
                public void spdyRequestRecvCallback(SpdySession p0, long p1, Object p2) {
                    Log.i(TAG, "spdyRequestRecvCallback");
                }

                @Override
                public void spdyStreamCloseCallback(SpdySession p0, long p1, int p2, Object p3, SuperviseData p4) {
                    Log.i(TAG, "spdyStreamCloseCallback");
                }
            }));
            int result = spdySession.submitRequestN(
                    543310043744L,
                    "https://guide-acs.m.taobao.com/gw/mtop.taobao.volvo.secondfloor.getconfig/1.0/?data=%7B%22containerId%22%3A%22main%22%2C%22features%22%3A%22%7B%5C%22deviceLevel%5C%22%3A%5C%22m%5C%22%2C%5C%22requestType%5C%22%3A%5C%22coldStart%5C%22%7D%22%2C%22keys%22%3A%22%5B%5C%22home_pull_down%5C%22%2C%5C%22refresh_config%5C%22%5D%22%2C%22utdid%22%3A%22ad900VuLB5YDAIeb%2B7UXne7D%22%7D",
                    (byte) 3,
                    new String[]{
                            ":host","guide-acs.m.taobao.com","x-sgext","JBOJMCQUtL3B8TaqT1n4HTC4ALkHsRO%2FCL0CqgSqE7gHvgexBbEFsAmqALkAuQC5ALkAuQC5ALkAuRO7E7kTuQCqALkAuRO5E7gTuBO4E7gTuxO4E7kTuRO5E7kTuRO5E7kTuRO5E7kTqgKqAKoAqlbvUe8AuRO5ALkAuRO5E7wJvRO5E6oDqgGqA6oAqkrnZ98TuROpEKkCqRCpAKoA1nHuQ8hxyHHOccsEyHjucbRv7W%2B%2BW7Bd2QLxYbFGxQTWAbpvyFfIcchxyHHIcchxyHHIcchxyHHIcchxyHHIcdYBv2%2B5b7sI1nG%2FCM9xv1u0b7sJ1nHbZO5j7X7NH75DxlPgAeJ45mngVNoE8EDQfbRv","x-social-attr","3","x-sign","azYBCM007xAAiacZto5wlHbobjHnWacZqi7q7zCHaDD2zLcJ130U0bmBpzB4HAKgkwxupHeHYeGMo4NdBFuTZf6l8YmnEgUplwmnGa","x-pv","6.3","x-region-channel","CN","x-features","27",":scheme","https","x-app-conf-v","0","x-mini-wua","aCQRToBNxvqCfpPdKs8R%2BzLES1N1iLmX9xY3AWk6%2FQUAfyTc7nbrXmQJXTjvAxj63H7zbD1wnXMFS5s2EC8sV9r3JGFLKO%2FSZol7gdoGt63X7WFASTerVaru5ubrDqr4Kv7%2BTdAHoIVSLmzY8aY8l%2FMCIVFl4wkYy3vmL4Nl4FAjILk1ZCRW1xTgSoaQhSdCm2hMX6YBOgPJYvQ%3D%3D","content-type","application/x-www-form-urlencoded;charset=UTF-8","cache-control","no-cache","x-t","1777858599","Cookie","thw=cn; cna=M/ZtIoB06TkCAQ6RNOBR8wcJ; isg=BDU14YR1sBr39NSm8797eoE4T7PvsunEJIQxbbda8az7jlWAfwL5lEON3ES4mQF8; tfstk=gbXtRrfcexNTiI2QyAVhnuuKzLsYtWjNYNSSnEYiGwQdVaE2GGjXcKQCol8GcP7AHe_voEv6oIhfSa1MSd6gDZQAkKA1sixjHiQAIxfMIsBd2NCiSd6gDZpNK1DDSPJAcavYrz2uEGSwUKaur1fFDJ9Xc-YbtCT7Rekarz2uKGSw3KaljgYyWwKJVKi6fKZpOe-mhKT6G2GBVeTXhZOjOJtXqAgj1fOQvn8BlK9flMZpmeTXhC_fAQfseE3W-xCQHbo3CDoSnxXpXCL-NeHxHox96Us6JmoVpEO9PGTKnPzqJyY9VTan_3dOfTpGKPNhXe11hdX_-xQRJ_9dpBmbkh112TRPhyIPCTX-95ukYph_vkhqgCtUSY6vVX68whtprk7qgjRTuHLuvkhqgCtevUqLvjl21H5..; hng=CN%7Czh-CN%7CCNY%7C156","x-bx-version","6.7.260101.52320099","f-refer","mtop","x-extdata","openappkey%3DDEFAULT_AUTH","x-ttid","1585202335753%40taobao_android_10.36.10","x-app-ver","10.36.10",":version","HTTP/1.1","x-c-traceid","null1777858599190000416842","x-regid","reg0fi6VV88vgReRbfDyyvnK0BKbBZLD",":method","GET","x-umt","HVEBBwZLPIHpgAKd8Fpbk0Fcm90J7G%2BC","x-utdid","ad900VuLB5YDAIeb%2B7UXne7D","c-launch-info","0,0,1777858599162,1777858596335,2","elderHome","0","x-appkey","21646297",":path","/gw/mtop.taobao.volvo.secondfloor.getconfig/1.0/?data=%7B%22containerId%22%3A%22main%22%2C%22features%22%3A%22%7B%5C%22deviceLevel%5C%22%3A%5C%22m%5C%22%2C%5C%22requestType%5C%22%3A%5C%22coldStart%5C%22%7D%22%2C%22keys%22%3A%22%5B%5C%22home_pull_down%5C%22%2C%5C%22refresh_config%5C%22%5D%22%2C%22utdid%22%3A%22ad900VuLB5YDAIeb%2B7UXne7D%22%7D","x-falco-id","413ebed95a8f43edb37c8abccace569f","user-agent","MTOPSDK%2F3.1.1.7+%28Android%3B8.1.0%3Bgoogle%3BAOSP+on+msm8996%29+DeviceType%28Phone%29"
                    },
                    new byte[0],
                    true,
                    1,
                    -1,
                    15000,
                    0,
                    0
            );
            String text = "crack submitRequestN result : " + result;
            simpleText.setText(text);
            Log.i(TAG, text);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

}