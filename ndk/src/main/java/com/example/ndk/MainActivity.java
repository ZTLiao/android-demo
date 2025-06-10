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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int MY_PERMISSION_REQUEST_WRITE_CODE = 11;

    String TAG = this.getClass().getSimpleName();

    TextView tv;

    Student one = new Student("tom", 34);

    MyHandler myHandler;

    List<String> al;

    String fp;

    static {
        System.loadLibrary("ndk");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test02();
        myHandler = new MyHandler(this);
        al = new ArrayList<>();
        tv = findViewById(R.id.tv_simpleText);
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            tv.setText("");
            al.clear();
            String tempPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            new MyThread(myHandler, tempPath).start();
        });
    }

    public void updateUI01(String fp) {
        fp = fp.replace(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "");
        if (al.size() < 20) {
            al.add(fp);
        } else {
            al.remove(0);
            al.add(fp);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < al.size(); i++) {
            sb.append(al.get(i) + "\r\n");
        }
        tv.setText(sb.toString());
    }

    public int testFun(String a, double b, long c) {
        return 1;
    }

    public void fun1() {
        try {
            Class<?> clazz = Class.forName("com.example.ndk.Student");
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                String methodName = method.getName();
                String returnType = method.getReturnType().getSimpleName();
                Log.i(TAG, "methodName : " + methodName + "," + returnType);
            }
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                String methodName = constructor.getName();
                String returnType = constructor.toGenericString();
                Log.i(TAG, "constructor : " + methodName + "," + returnType);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void fun2() {
        try {
            Class<?> clazz = com.example.ndk.Student.class;
            Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
            Object obj = constructor.newInstance("tom", 25);
            Method method = clazz.getMethod("study", int.class);
            Object invoke = method.invoke(obj, 1);
            Log.i(TAG, "study ret : " + invoke);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void fun3() {
        try {
            Class<? extends Student> clazz = one.getClass();
            Method method = clazz.getDeclaredMethod("getAge");
            method.setAccessible(true);
            Object obj = method.invoke(one);
            Log.i(TAG, "fun3 obj : " + obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void test02() {
        one.study(1);
        testFun("aa", 4.5, 5);
        TextView tv = findViewById(R.id.tv_simpleText);
        tv.setText(stringFromJNI());
        tv.setOnClickListener(v -> {
            fun1();
            fun2();
            fun3();
            Log.i(TAG, "callJavaFunFromJNI ret : " + callJavaFunFromJNI(one));
            Log.i(TAG, "callStaticJavaFunFromJNI ret : " + callStaticJavaFunFromJNI());
            Log.i(TAG, "getPackageNameFromJNI ret : " + getPackageNameFromJNI());
            Log.i(TAG, "getPackageNameFromJNI2 ret : " + getPackageNameFromJNI2());
            test01();
        });
        Log.i(TAG, this.getPackageName());
        Log.i(TAG, getApplicationContext().getPackageName());
        Log.i(TAG, getPackageNameFromJNI());
    }

    private void test01() {
        int ret = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ret == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "grant permission");
            String fc = readSDCardFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/b.txt");
            Log.i(TAG, "fc : " + fc);
        } else {
            Log.i(TAG, "not permission");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_WRITE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_WRITE_CODE: {
                if (grantResults.length > 0 && grantResults[0] != -1) {
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

    public native int callJavaFunFromJNI(Student param);

    public native String callStaticJavaFunFromJNI();

    public native String getPackageNameFromJNI();

    public native String getPackageNameFromJNI2();

}