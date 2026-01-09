package com.example.myxposed;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class R0Hook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        Log.e("R0Hook", loadPackageParam.processName);
        Log.e("R0Hook", loadPackageParam.packageName);
        Log.e("R0Hook", loadPackageParam.appInfo.toString());
        Log.e("R0Hook", loadPackageParam.classLoader.toString());
        String packageName = loadPackageParam.packageName;
        if (!packageName.equals(Objects.requireNonNull("com.roysue.myr0"))) {
            return;
        }
        String teacherClassName = "com.roysue.myr0.teacher";
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

        XposedHelpers.findAndHookConstructor(clazz, int.class, String.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("two constructor beforeHookedMethod is call");
                Object[] args = param.args;
                args[0] = 100;
                args[1] = "john";
                args[2] = true;
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("two constructor afterHookedMethod is call");
            }
        });

        XposedHelpers.findAndHookConstructor(clazz, loadPackageParam.classLoader,int.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Field ageField = clazz.getDeclaredField("age");
                ageField.setAccessible(true);
                ageField.set(param.thisObject, 666);
                Field nameField = clazz.getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(param.thisObject, "tom");
                Field genderField = clazz.getDeclaredField("gender");
                genderField.setAccessible(true);
                genderField.set(param.thisObject, true);
                XposedHelpers.setIntField(param.thisObject, "age", 456);
                XposedHelpers.setObjectField(param.thisObject, "name", "tom1");
                XposedHelpers.setBooleanField(param.thisObject, "gender", false);
                Method toStringMethod = clazz.getDeclaredMethod("toString");
                String toString = (String) toStringMethod.invoke(param.thisObject, null);
                Log.i("R0Hook afterHookedMethod", toString);
            }
        });

        XposedHelpers.findAndHookMethod(teacherClassName, loadPackageParam.classLoader, "toString", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                String str = "Teacher{" +
                        "age=" + 100 +
                        ", name='" + "john" + '\'' +
                        ", gender=" + true +
                        '}';
                param.setResult(str);
            }
        });
    }
}
