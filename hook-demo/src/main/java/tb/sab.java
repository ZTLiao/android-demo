package tb;

import android.app.Application;
import android.content.Context;
import android.os.Build;

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
        if(p0 == null){
            return;
        }else {
            try{
                sab.b = p0;
                if (!sab.c && Build.VERSION.SDK_INT >= 14) {
                    Context b = (sab.b instanceof Application)? sab.b: sab.b.getApplicationContext();
                    if (b != null) {
                        ((Application) b).registerActivityLifecycleCallbacks(sab.f);
                        sab.c = true;
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
        if(p0 != null){
            sab.e.add(p0);
        }
        return;
    }
    private static void a(boolean p0){
        sae.b(new sab$2(p0));
    }
    public static boolean a() {
        boolean b = false;
        if (sab.c) {
            if (sab.b != null && !sab.d) {
                return b;
            }
            return true;
        } else {
            try {
                Class uClass = Class.forName("anet.channel.GlobalAppRuntimeInfo");
                Class[] uClassArray = new Class[0];
                Object[] objArray = new Object[0];
                b = ((Boolean) uClass.getMethod("isAppBackground", uClassArray).invoke(uClass, objArray)).booleanValue();
                return b;
            } catch (java.lang.Throwable e0) {
            }
        }
        return false;
    }
    public static void b(sab$a p0){
        sab.e.remove(p0);
    }
    public static boolean b(){
        int i = 0;
        if(sab.a() && ((sab.a) > 0 && ((System.currentTimeMillis() - sab.a) - 0xea60) > 0)){
            return true;
        }else {
            return false;
        }
    }
    public static void c(){
        if(sab.a()){
            sab.d = false;
            sab.a(true);
        }
        return;
    }
    public static void d(){
        int i = 0;
        if(!sab.a()){
            sab.d = true;
            sab.a = System.currentTimeMillis();
            sab.a(false);
        }
        return;
    }
    public static CopyOnWriteArraySet e(){
        return sab.e;
    }

}
