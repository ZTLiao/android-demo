package com.example.hook_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;

import android.util.ArrayMap;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

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
        LoadDex(this);
        startActivityForFirstMethod(this);
        startActivityForSecondMethod(this);
        startActivityForThirdMethod(this);
        replaceClassLoader(this);
    }

    public static void setDexElementsInClassLoader(ClassLoader classLoader, Object dexElements) {
        try {
            Class BaseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = BaseDexClassLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObj = pathListField.get(classLoader);
            Class DexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = DexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            dexElementsField.set(pathListObj, dexElements);
        } catch (Exception ignored) {}
    }

    public static Object getDexElementsInClassLoader(ClassLoader classLoader) {
        try {
            Class BaseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = BaseDexClassLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObj = pathListField.get(classLoader);
            Class DexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = DexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object dexElementsObj = dexElementsField.get(pathListObj);
            return dexElementsObj;
        } catch (Exception ignored) {}
        return null;
    }

    public static Object combineDexElements(Object dexElements1, Object dexElements2){
        Object result = null;
        int length1 = Array.getLength(dexElements1);
        int length2 = Array.getLength(dexElements2);
        int length = length1 + length2;
        result = Array.newInstance(dexElements1.getClass().getComponentType(), length);
        for (int i = 0; i < length; i++) {
            if (i < length1) {
                Array.set(result, i, Array.get(dexElements1, i));
            } else {
                Array.set(result, i, Array.get(dexElements2, i - length1));
            }
        }
        Log.e("hook demo", length + "--" + length1 + "--" + length2);
        Log.e("hook demo", result.toString());
        return result;
    }

    public static void startActivityForThirdMethod(Context context) {
        try {
            ClassLoader pathClassLoader = MainActivity.class.getClassLoader();
            DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/4.dex", context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
            Object dexElement1 = getDexElementsInClassLoader(pathClassLoader);
            Object dexElement2 = getDexElementsInClassLoader(dexClassLoader);
            Object newDexElements = combineDexElements(dexElement1, dexElement2);
            setDexElementsInClassLoader(pathClassLoader, newDexElements);
            getClassLoaderClasses(pathClassLoader);
            Field parentField = ClassLoader.class.getDeclaredField("parent");
            parentField.setAccessible(true);
            parentField.set(parentField, dexClassLoader);
            Class<?> TestActivityClass = dexClassLoader.loadClass("com.kanxue.test02.TestActivity");
            Log.i("hook demo", TestActivityClass.toString());
            context.startActivity(new Intent(context, TestActivityClass));
        } catch (Exception ignored) {}
    }

    public static void replaceClassLoader(Context context) {
        try {
            ClassLoader pathClassLoader = MainActivity.class.getClassLoader();
            Class ActivityTHreadClass = pathClassLoader.loadClass("android.app.ActivityThread");
            Field sCurrentActivityThreadField = ActivityTHreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object currentActivityThread = sCurrentActivityThreadField.get(null);
            Field mPackagesField = ActivityTHreadClass.getDeclaredField("mPackages");
            mPackagesField.setAccessible(true);
            ArrayMap mPackagesObj = (ArrayMap) mPackagesField.get(currentActivityThread);
            String packageName = context.getPackageName();
            WeakReference wr = (WeakReference) mPackagesObj.get(packageName);
            Object loadedApk = wr.get();
            Class<?> LoadedApkClass = pathClassLoader.loadClass("android.app.LoadedApk");
            Field mClassLoaderField = LoadedApkClass.getDeclaredField("mClassLoader");
            Object pathClassLoaderObj = mClassLoaderField.get(loadedApk);
            Log.e("mClassLoader", pathClassLoaderObj.toString());
            mClassLoaderField.set(loadedApk, pathClassLoaderObj);
        } catch (Exception e) {

        }
    }

    public static void startActivityForSecondMethod(Context context) {
        try {
            ClassLoader pathClassLoader = MainActivity.class.getClassLoader();
            ClassLoader bootClassLoader = pathClassLoader.getParent();
            DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/4.dex", context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
            Field parentField = ClassLoader.class.getDeclaredField("parent");
            parentField.setAccessible(true);
            parentField.set(parentField, dexClassLoader);
        } catch (Exception ignored) {}
    }

    public static void startActivityForFirstMethod(Context context) {
        try {
            DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/4.dex", context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
            Class<?> TestActivityClass = dexClassLoader.loadClass("com.kanxue.test02.TestActivity");
            Log.i("hook demo", TestActivityClass.toString());
            context.startActivity(new Intent(context, TestActivityClass));
        } catch (Exception ignored) {}
    }


    public static void LoadDex(Context context) {
        try {
            DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/4.dex", context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
            getClassLoaderClasses(dexClassLoader);
            Class<?> TestClass = dexClassLoader.loadClass("com.kanxue.test02.TestClass");
            Method testFuncMethod = TestClass.getDeclaredMethod("TestFunc", TestClass);
            testFuncMethod.setAccessible(true);
            testFuncMethod.invoke(null);
        } catch (Exception ignored) {}
    }

    public static void LoadDex1(Context context) {
        try {
            DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/4.dex", context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
            Class<?> clazz = dexClassLoader.loadClass("com.example.myplugin.TestDex");
            Method printfMethod = clazz.getDeclaredMethod("printf", clazz);
            Object result = printfMethod.invoke(null);
            assert result != null;
            Log.e("Hook Demo", result.toString());
            testClassLoader();
        } catch (Exception ignored) {}
    }

    public static String[] getClassLoaderClasses(ClassLoader classLoader) {
        try {
            Class<?> BaseDexClassLoaderClass = classLoader.loadClass("dalvik.system.BaseDexClassLoader");
            Field pathListField = BaseDexClassLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObj = pathListField.get(classLoader);
            Class<?> DexPathListClass = classLoader.loadClass("dalvik.system.DexPathList");
            Field dexElements = DexPathListClass.getDeclaredField("dexElements");
            Object[] elements = (Object[]) dexElements.get(pathListObj);
            Class<?> ElementClass = classLoader.loadClass("dalvik.system.DexPathList$Element");
            Field dexFileField = ElementClass.getDeclaredField("dexFile");
            dexFileField.setAccessible(true);
            Class<?> DexFileClass = classLoader.loadClass("dalvik.system.DexFile");
            Field mCookieField = DexFileClass.getDeclaredField("mCookie");
            mCookieField.setAccessible(true);
            Method[] methods = DexFileClass.getDeclaredMethods();
            Method getClassNameListMethod = null;
            for (Method method : methods) {
                if (method.getName().equals("getClassNameList")) {
                    getClassNameListMethod = method;
                    getClassNameListMethod.setAccessible(true);
                    break;
                }
            }
            for (Object o : elements) {
                Object dexFileObj = dexFileField.get(o);
                Object mCookieObj = mCookieField.get(o);
                String[] classList = (String[]) getClassNameListMethod.invoke(dexFileObj, mCookieObj);
                for (String className : classList) {
                    Log.i("Hook Demo", classLoader + "-->" + className);
                }
                return classList;
            }
        } catch (Exception ignored) {}
        return null;
    }

    public static void testClassLoader() {
        ClassLoader thisClassLoader = MainActivity.class.getClassLoader();
        getClassLoaderClasses(thisClassLoader);
        Log.e("Hook Demo", thisClassLoader.toString());
        ClassLoader parentClassLoader = thisClassLoader.getParent();
        while (parentClassLoader != null) {
            Log.e("Hook Demo", parentClassLoader.toString() + " this : " + thisClassLoader.getParent().toString());
            parentClassLoader = parentClassLoader.getParent();
        }
    }

    public native String stringFromJNI();

}