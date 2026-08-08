package com.example.hook_demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends ComponentActivity {

    private Button simpleBtn;

    static {
        System.loadLibrary("hookdemo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleBtn = findViewById(R.id.simpleBtn);
        simpleBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity2.class));
        });
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher(2);
        Teacher teacher3 = new Teacher(3, "tom3");
        Teacher teacher4 = new Teacher(4, "tom4", false);
        Log.i("hook-demo MainActivity", teacher1.toString());
        Log.i("hook-demo MainActivity", teacher2.toString());
        Log.i("hook-demo MainActivity", teacher3.toString());
        Log.i("hook-demo MainActivity", teacher4.toString());
        Teacher.Student student = new Teacher.Student();
        Log.i("hook-demo MainActivity", student.toString());
        Log.e("hook-demo", Teacher.getStr(teacher4));
        try {
            DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/4.dex", this.getCacheDir().getAbsolutePath(), null, this.getClassLoader());
            Class<?> clazz = dexClassLoader.loadClass("com.example.myplugin.TestDex");
            Method printfMethod = clazz.getDeclaredMethod("printf", clazz);
            Object result = printfMethod.invoke(null);
            assert result != null;
            Log.e("Hook Demo", result.toString());
            testClassLoader();
        } catch (Exception ignored) {}
    }

    public static void testClassLoader() {
        ClassLoader thisClassLoader = MainActivity.class.getClassLoader();
        Log.e("Hook Demo", thisClassLoader.toString());
        ClassLoader parentClassLoader = thisClassLoader.getParent();
        while (parentClassLoader != null) {
            Log.e("Hook Demo", parentClassLoader.toString() + " this : " + thisClassLoader.getParent().toString());
            parentClassLoader = parentClassLoader.getParent();
        }
    }

    public native String stringFromJNI();

}