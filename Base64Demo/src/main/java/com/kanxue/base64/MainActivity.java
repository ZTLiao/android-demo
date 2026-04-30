package com.kanxue.base64;

import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv_simpleText);
        String encode = Base64.encodeToString("A".getBytes(), Base64.DEFAULT);
        tv.setText(base64enc("kanxue"));
        tv.setText(rc4_enc("kanxue".getBytes()));
        tv.setText(md5("123456"));
    }

    public native String stringFromJNI();

    public native String base64enc(String str);

    public native String rc4_enc(byte[] data);

    public native String md5(String message);
}