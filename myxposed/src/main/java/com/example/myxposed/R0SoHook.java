package com.example.myxposed;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class R0SoHook implements IXposedHookLoadPackage {

    //public native void my_hook_strstr();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        String packageName = loadPackageParam.packageName;
        if (!packageName.contains("com.roysue.r0so")) {
            return;
        }
        ClassLoader classLoader = loadPackageParam.classLoader;
        Log.e("r0ysue", loadPackageParam.classLoader.toString());
        Class<?> systemClass = loadPackageParam.classLoader.loadClass("java.lang.System");
        Class<?> runtimeClass = loadPackageParam.classLoader.loadClass("java.lang.Runtime");
        XposedBridge.hookAllMethods(runtimeClass, "loadLibrary0", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                if (((String) (param.args[1])).indexOf("libnative-lib.so") > 0) {
                    //System.load("/data/local/tmp/a.so");
                    System.load("/data/local/tmp/b.so");
                    System.load("/data/local/tmp/c.so");
                }
            }
        });
        //my_hook_strstr();
    }
}
