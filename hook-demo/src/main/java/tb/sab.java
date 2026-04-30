package tb;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.CopyOnWriteArraySet;

public class sab {

    public static long a;
    private static Context b;
    private static boolean c;
    private static boolean d;
    private static CopyOnWriteArraySet e;
    private static Application.ActivityLifecycleCallbacks f;

    static {
        sab.e = new CopyOnWriteArraySet();
        sab.f = new sab$1();
    }
    public static void a(Context p0){
        IpChange $ipChange = sab.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            $ipChange.ipc$dispatch("9e1d6460", objArray);
            return;
        }else if(p0 == null){
            return;
        }else {
            try{
                sab.b = p0;
                if (!sab.c && Build$VERSION.SDK_INT >= 14) {
                    Context b = (sab.b instanceof Application)? sab.b: sab.b.getApplicationContext();
                    if (b != null) {
                        b.registerActivityLifecycleCallbacks(sab.f);
                        sab.c = true;
                    }else {
                        Object[] objArray1 = new Object[]{"context",p0};
                        spduLog.Tloge("tnetsdk.AppLifeCycle", null, "AppLifecycle initialize fail", objArray1);
                    }
                }
                return;
            }catch(java.lang.Exception e7){
                e7.printStackTrace();
            }
            return;
        }
    }
    public static void a(sab$a p0){
        IpChange $ipChange = sab.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            $ipChange.ipc$dispatch("c24919a7", objArray);
            return;
        }else if(p0 != null){
            sab.e.add(p0);
        }
        return;
    }
    private static void a(boolean p0){
        IpChange $ipChange = sab.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{new Boolean(p0)};
            $ipChange.ipc$dispatch("a821d36c", objArray);
            return;
        }else {
            sae.b(new sab$2(p0));
            return;
        }
    }
    public static boolean a(){
        boolean b = false;
        if (sab.c) {
            if (sab.b != null && !sab.d) {
                return b;
            }
            return true;
        }else {
            try{
                Class uClass = Class.forName("anet.channel.GlobalAppRuntimeInfo");
                Class[] uClassArray = new Class[b];
                Object[] objArray = new Object[b];
                b = uClass.getMethod("isAppBackground", uClassArray).invoke(uClass, objArray).booleanValue();
                return b;
            }catch(java.lang.Throwable e0){
            }
        }
    }
    public static void b(sab$a p0){
        IpChange $ipChange = sab.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            $ipChange.ipc$dispatch("1c2483e8", objArray);
            return;
        }else {
            sab.e.remove(p0);
            return;
        }
    }
    public static boolean b(){
        IpChange $ipChange = sab.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            return $ipChange.ipc$dispatch("57a83ed", objArray).booleanValue();
        }else if(sab.a() && ((sab.a) > 0 && ((System.currentTimeMillis() - sab.a) - 0xea60) > 0)){
            return true;
        }else {
            return i;
        }
    }
    public static void c(){
        Object[] objArray;
        IpChange $ipChange = sab.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            objArray = new Object[i];
            $ipChange.ipc$dispatch("5889b6a", objArray);
            return;
        }else if(sab.a()){
            objArray = new Object[i];
            spduLog.Tloge("tnetsdk.AppLifeCycle", null, "[onForeground]", objArray);
            sab.d = i;
            sab.a(true);
        }
        return;
    }
    public static void d(){
        Object[] objArray;
        IpChange $ipChange = sab.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            objArray = new Object[i];
            $ipChange.ipc$dispatch("596b2eb", objArray);
            return;
        }else if(!sab.a()){
            objArray = new Object[i];
            spduLog.Tloge("tnetsdk.AppLifeCycle", null, "[onBackground]", objArray);
            sab.d = true;
            sab.a = System.currentTimeMillis();
            sab.a(i);
        }
        return;
    }
    public static CopyOnWriteArraySet e(){
        IpChange $ipChange = sab.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return sab.e;
        }
        Object[] objArray = new Object[0];
        return $ipChange.ipc$dispatch("8f15355d", objArray);
    }

}
