package org.android.netutil;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Method;

public class UtilTool {
    private static String HARMONY_OS;
    public static String TAG;
    private static int a;
    private static String b;
    private static boolean c;
    private static Context d;

    public static String a(){
        String obj = null;
        try{
            Class[] uClassArray = new Class[0];
            Method declaredMeth = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader()).getDeclaredMethod("currentProcessName", uClassArray);
            declaredMeth.setAccessible(true);
            Object[] objArray = new Object[0];
            Object obj1 = declaredMeth.invoke(obj, objArray);
            if (obj1 instanceof String) {
                obj = (String) obj1;
            }
            return obj;
        }catch(java.lang.Throwable e0){
        }
        return null;
    }
    public static Context b(){
        if (UtilTool.c) {
            return UtilTool.d;
        }
        int i1 = 0;
        try{
            Class uClass = Class.forName("android.app.ActivityThread");
            Class[] uClassArray = new Class[i1];
            Object[] objArray = new Object[i1];
            Object obj = uClass.getMethod("currentActivityThread", uClassArray).invoke(uClass, objArray);
            Class[] uClassArray1 = new Class[i1];
            objArray = new Object[i1];
            UtilTool.d = (Context) obj.getClass().getMethod("getApplication", uClassArray1).invoke(obj, objArray);
        }catch(java.lang.Throwable e2){
            e2.printStackTrace();
        }
        UtilTool.c = true;
        return UtilTool.d;
    }
    public static boolean c(){
        boolean b;
        if (UtilTool.a == -1) {
            try{
                Class uClass = Class.forName("com.huawei.system.BuildEx");
                Class[] uClassArray = new Class[0];
                Object[] objArray = new Object[0];
                b = "harmony".equals(uClass.getMethod("getOsBrand", uClassArray).invoke(uClass, objArray));
            }catch(java.lang.Exception e0){
                b = false;
            }
            UtilTool.a = (b)? 1: 0;
        }
        if (UtilTool.a == 1) {
            return true;
        }else {
            return false;
        }
    }
    public static String d(){
        String b;
        if ((b = UtilTool.b) != null) {
            return b;
        }
        try{
            Class uClass = Class.forName("android.os.SystemProperties");
            Class[] uClassArray = new Class[]{String.class};
            Object[] objArray = new Object[]{"hw_sc.build.platform.version"};
            b = (String) uClass.getDeclaredMethod("get", uClassArray).invoke(uClass, objArray);
            if (b == null) {
                b = "";
            }
            UtilTool.b = b;
            return b;
        }catch(java.lang.Throwable e0){
        }
        return null;
    }

}
