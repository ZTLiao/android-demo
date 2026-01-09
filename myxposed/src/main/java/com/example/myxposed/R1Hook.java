package com.example.myxposed;

import android.util.Log;

import java.util.Objects;

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
    }
}
