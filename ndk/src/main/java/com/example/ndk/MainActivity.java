package com.example.ndk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private final static int MY_PERMISSION_REQUEST_WRITE_CODE = 11;

    String TAG = this.getClass().getSimpleName();

    static {
        System.loadLibrary("ndk");
    }

    public int testFun(String a, double b, long c) {
        return 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testFun("aa", 4.5, 5);
        TextView tv = findViewById(R.id.tv_simpleText);
        tv.setText(stringFromJNI());
        tv.setOnClickListener(v -> {
            int ret = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (ret == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "grant permission");
                String fc = readSDCardFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                Log.i(TAG, "fc : " + fc);
            } else {
                Log.i(TAG, "not permission");
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_WRITE_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
        switch(requestCode) {
            case MY_PERMISSION_REQUEST_WRITE_CODE:
            {
                if (grantResults.length > 0) {
                    Log.i(TAG, "write sd card permission success");
                } else {
                    Log.i(TAG, "write sd card permission fail");
                }
                break;
            }
            default: {
                Log.i(TAG, "other permission");
                break;
            }
        }
    }

    public native String stringFromJNI();

    public native int getLength(String str);

    public native String readSDCardFile(String filePath);

}