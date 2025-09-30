package com.example.hook_shell;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("hookshell");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        stringFromJNI();
        secondShell();
        testDexClassLoader(this, "/sdcard/5/4.dex");
        testDexClassLoader(this, "/sdcard/5/5.dex");
    }

    private void testDexClassLoader(Context context, String dexFilePath) {
        String OPT_DEX = "opt_dex";
        String LIB_PATH = "lib_path";
        File optDexFile = context.getDir(OPT_DEX, 0);
        File libPathFile = context.getDir(LIB_PATH, 0);
        ClassLoader parentClassLoader = MainActivity.class.getClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(dexFilePath, optDexFile.getAbsolutePath(), libPathFile.getAbsolutePath(), parentClassLoader);
        try {
            Class<?> clazz = dexClassLoader.loadClass("com.roysue.test.test02");
            Method testFuncMethod = clazz.getDeclaredMethod("testFunc");
            Object obj = clazz.newInstance();
            testFuncMethod.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public native String stringFromJNI();

    public native void secondShell();

}