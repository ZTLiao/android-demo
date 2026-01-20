package com.example.myxposed;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class R2Hook implements IXposedHookLoadPackage {

    public ClassLoader getLoader(ClassLoader classLoader) throws Exception {
        Class activityThreadClass = classLoader.loadClass("android.app.ActivityThread");
        Method currentActivityThread = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThread.setAccessible(true);
        Object activityThreadObj = currentActivityThread.invoke(null);
        Field mInitialApplication = activityThreadClass.getDeclaredField("mInitialApplication");
        mInitialApplication.setAccessible(true);
        Object applicationObj = mInitialApplication.get(activityThreadObj);
        Class<?> applicationClass = classLoader.loadClass("android.app.Application");
        Field mLoadedApk = applicationClass.getDeclaredField("mLoadedApk");
        mLoadedApk.setAccessible(true);
        Object loadedApkObj = mLoadedApk.get(applicationObj);
        Class<?> loadedApkClass = classLoader.loadClass("android.app.LoadedApk");
        Field mClassLoader = loadedApkClass.getDeclaredField("mClassLoader");
        ClassLoader classLoaderObj = (ClassLoader) mClassLoader.get(loadedApkObj);
        Log.e("getLoader", classLoaderObj.toString());
        return classLoaderObj;
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        boolean isPackage = loadPackageParam.packageName.contains("com.roysue.myr0");
        if (!isPackage) {
            return;
        }
        classByClassloader(loadPackageParam.classLoader);
        ClassLoader myClassLoader = getLoader(loadPackageParam.classLoader);
        Log.e("R2Hook", "myClassLoader : " + myClassLoader.toString());
        ClassLoader myClassLoader1 = getLoader1(loadPackageParam.classLoader);
        Log.e("R2Hook", "myClassLoader1 : " + myClassLoader1.toString());
        Class<?> teacherClass = loadPackageParam.classLoader.loadClass("com.roysue.myr0.teacher");
        XposedHelpers.findAndHookMethod(teacherClass, "getString", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("com.SecShell.SecShell.AW", loadPackageParam.classLoader, "onCreate", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                getLoader(loadPackageParam.classLoader);
                classByClassloader(loadPackageParam.classLoader);
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                getLoader(loadPackageParam.classLoader);
                classByClassloader(getLoader(loadPackageParam.classLoader));
                ClassLoader myLoader = getLoader(loadPackageParam.classLoader);
                classByClassloader1(loadPackageParam.classLoader);
                Class<?> teacherClass = myLoader.loadClass("com.roysue.myr0.teacher");
                XposedHelpers.findAndHookMethod(teacherClass, "getString", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        param.setResult("I am hooked.");
                    }
                });
            }
        });
    }

    public void classByClassloader1(ClassLoader classLoader) {
        try {
            Object pathListObj = XposedHelpers.getObjectField(classLoader, "pathList");
            Object[] dexElementsObj = (Object[]) XposedHelpers.getObjectField(pathListObj, "dexElements");
            for (Object o : dexElementsObj) {
                DexFile dexFileObj = (DexFile) XposedHelpers.getObjectField(o, "dexFile");
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

    public ClassLoader getLoader1(ClassLoader classLoader) throws Exception {
        Class<?> activiThreadClass = classLoader.loadClass("android.app.ActivityThread");
        Object activityObj = XposedHelpers.callStaticMethod(activiThreadClass, "currentActivityThread");
        Object mInitialApplication = XposedHelpers.getObjectField(activityObj, "mInitialApplication");
        Object mLoadedApk = XposedHelpers.getObjectField(mInitialApplication, "mLoadedApk");
        ClassLoader mClassLoader = (ClassLoader) XposedHelpers.getObjectField(mLoadedApk, "mClassLoader");
        return mClassLoader;
    }
}
