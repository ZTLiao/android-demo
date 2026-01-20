package com.example.myxposed;

import android.util.Log;

import java.lang.reflect.Constructor;
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

//        XposedHelpers.findAndHookConstructor(clazz, loadPackageParam.classLoader, int.class, String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Field ageField = clazz.getDeclaredField("age");
//                ageField.setAccessible(true);
//                ageField.set(param.thisObject, 666);
//                Field nameField = clazz.getDeclaredField("name");
//                nameField.setAccessible(true);
//                nameField.set(param.thisObject, "tom");
//                Field genderField = clazz.getDeclaredField("gender");
//                genderField.setAccessible(true);
//                genderField.set(param.thisObject, true);
//                XposedHelpers.setIntField(param.thisObject, "age", 456);
//                XposedHelpers.setObjectField(param.thisObject, "name", "tom1");
//                XposedHelpers.setBooleanField(param.thisObject, "gender", false);
//                Method toStringMethod = clazz.getDeclaredMethod("toString");
//                String toString = (String) toStringMethod.invoke(param.thisObject, null);
//                Log.i("R0Hook afterHookedMethod", toString);
//            }
//        });

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

        XposedHelpers.findAndHookMethod("com.example.hook_demo.Teacher$Student", loadPackageParam.classLoader, "toString", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                String str = "Teacher$Student{" +
                        "age=" + 1000000 +
                        ", name='" + "libai" + '\'' +
                        ", gender=" + false +
                        '}';
                param.setResult(str);
            }
        });
        Class<?> teacherClass = loadPackageParam.classLoader.loadClass(teacherClassName);
        Object teacherObj = teacherClass.newInstance();
        Method publicStaticMethod = teacherClass.getDeclaredMethod("publicStatic");
        Object publicStaticResult1 = publicStaticMethod.invoke(teacherClass);
        XposedBridge.log(publicStaticResult1.toString());
        Object publicStaticResult2 = XposedHelpers.callStaticMethod(teacherClass, "publicStatic");
        Log.e("R0Hook", "publicStaticResult1 : " + publicStaticResult1.toString() + ", publicStaticResult2 : " + publicStaticResult2.toString());
        Method publicMethod = teacherClass.getDeclaredMethod("publicMethod");
        Object publicMethodResult1 = publicMethod.invoke(teacherObj);
        Constructor<?> constructor = teacherClass.getConstructor(int.class);
        teacherObj = constructor.newInstance(1);
        //teacherObj = XposedHelpers.newInstance(teacherClass, 1);
        Object publicMethodResult2 = XposedHelpers.callMethod(teacherObj, "publicMethod");
        Log.e("R0Hook", "publicMethodResult1 : " + publicMethodResult1.toString() + ", publicMethodResult2 : " + publicMethodResult2.toString());
        Method privateMethod = teacherClass.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);
        Object privateMethodResult1 = privateMethod.invoke(teacherObj);
        Object privateMethodResult2 = XposedHelpers.callMethod(teacherObj, "privateMethod");
        Log.e("R0Hook", "privateMethodResult1 : " + privateMethodResult1.toString() + ", privateMethodResult2 : " + privateMethodResult2.toString());
        XposedHelpers.findAndHookConstructor(teacherClass, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Method publicMethod1 = teacherClass.getDeclaredMethod("publicMethod");
                Method privateMethod1 = teacherClass.getDeclaredMethod("privateMethod");
                String publicStr = (String) publicMethod1.invoke(param.thisObject);
                privateMethod1.setAccessible(true);
                String privateStr = (String) privateMethod1.invoke(param.thisObject);
                Log.e("R0Hook", "publicStr : " + publicStr + ", privateStr : " + privateStr);
            }
        });

        XposedHelpers.findAndHookMethod(teacherClass, "getStr", teacherClass, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Method publicMethod1 = teacherClass.getDeclaredMethod("publicMethod");
                Method privateMethod1 = teacherClass.getDeclaredMethod("privateMethod");
                String publicStr = (String) publicMethod1.invoke(param.thisObject);
                privateMethod1.setAccessible(true);
                String privateStr = (String) privateMethod1.invoke(param.thisObject);
                Log.e("R0Hook", "getStr publicStr : " + publicStr + ", privateStr : " + privateStr);
            }
        });
    }
}
