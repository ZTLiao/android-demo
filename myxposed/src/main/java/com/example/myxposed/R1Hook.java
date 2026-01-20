package com.example.myxposed;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;

import dalvik.system.DexFile;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class R1Hook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        Log.i("R1Hook", loadPackageParam.processName);
        Log.i("R1Hook", loadPackageParam.packageName);
        Log.i("R1Hook", loadPackageParam.appInfo.toString());
        Log.i("R1Hook", loadPackageParam.classLoader.toString());
        String packageName = loadPackageParam.packageName;
        if (!packageName.equals(Objects.requireNonNull("com.example.hook_demo"))) {
            return;
        }
        String teacherClassName = "com.example.hook_demo.Teacher";
        Class<?> clazz = loadPackageParam.classLoader.loadClass(teacherClassName);
        XposedHelpers.findAndHookConstructor(clazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("beforeHookedMethod is call");
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("afterHookedMethod is call");
            }
        });

        XposedHelpers.findAndHookConstructor(clazz, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("one constructor beforeHookedMethod is call");
                Object[] args = param.args;
                args[0] = 99;
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("one constructor afterHookedMethod is call");
            }
        });

        XposedHelpers.findAndHookConstructor(clazz, int.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("two constructor beforeHookedMethod is call");
                Object[] args = param.args;
                args[0] = 100;
                args[1] = "john";
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("two constructor afterHookedMethod is call");
            }
        });

        XposedHelpers.findAndHookMethod("com.example.hook_demo.MainActivity", loadPackageParam.classLoader, "stringFromJNI", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                String str = "test hook jni method...";
                param.setResult(str);
            }
        });
        classByClassloader(loadPackageParam.classLoader);
        Class<?> dexClassloaderClass = loadPackageParam.classLoader.loadClass("dalvik.system.DexClassLoader");
        XposedHelpers.findAndHookConstructor(dexClassloaderClass, String.class, String.class, String.class, ClassLoader.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.e("R1Hook", param.thisObject.toString());
                classByClassloader((ClassLoader) param.thisObject);
                Class<?> testDexClass = ((ClassLoader) param.thisObject).loadClass("com.example.myplugin.TestDex");
                XposedHelpers.findAndHookMethod(testDexClass, "printf", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Log.e("R1Hook", "testDexClass beforeHookedMethod");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.e("R1Hook", "testDexClass afterHookedMethod");
                    }
                });
            }
        });
    }

    public void classByClassloader(ClassLoader classLoader) {
        try {
            Class<?> baseDexClass = classLoader.loadClass("dalvik.system.BaseDexClassLoader");
            Field pathList = baseDexClass.getDeclaredField("pathList");
            pathList.setAccessible(true);
            Object pathListObj = pathList.get(classLoader);
            Class<?> dexPathListClass = classLoader.loadClass("dalvik.system.DexPathList");
            Field dexElements = dexPathListClass.getDeclaredField("dexElements");
            Object[] elements = (Object[]) dexElements.get(pathListObj);
            for (Object o : elements) {
                Class<?> elementClass = classLoader.loadClass("dalvik.system.DexPathList$Element");
                Field dexFile = elementClass.getDeclaredField("dexFile");
                dexFile.setAccessible(true);
                DexFile dexFileObj = (DexFile) dexFile.get(o);
                Enumeration<String> entries = dexFileObj.entries();
                while (entries.hasMoreElements()) {
                    String nextElement = entries.nextElement();
                    Log.e("R1Hook", nextElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
