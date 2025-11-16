package com.example.easymd5;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.MessageDigest;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected native void onCreate(Bundle savedInstanceState);
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        TextView tv = findViewById(R.id.sample_text);
//        tv.setText(
//                Arrays.toString(Build.SUPPORTED_ABIS) + "\n " +
//                        "v7a so md5 => "+ mdString("123456") + "\n " +
//                        "java md5 => " + javaMd5("123456") + "\n " +
//                        "RefMd5 => " + Refmd5("123456") + "\n" +
//                        "RefMd5Sec => " + Refmd5sec("123456") + "\n");
//    }

    public static native String mdString(String string);

    public static native String mdFile(String filename);

    public static native String Refmd5(String string);
    public static native String Refmd5sec(String string);

    public static String javaMd5(String plainText) {
        if (TextUtils.isEmpty(plainText)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(plainText.getBytes());
            StringBuffer result = new StringBuffer();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() ==  1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}