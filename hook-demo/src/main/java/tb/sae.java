package tb;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class sae {
    private static ScheduledThreadPoolExecutor a;
    private static AtomicInteger b;
    private static ThreadPoolExecutor c;

    static {
        sae.b = new AtomicInteger();
        ThreadPoolExecutor v0 = new ThreadPoolExecutor(1, 1, 3, TimeUnit.SECONDS, new LinkedBlockingDeque(), new sae$a("tnet-thread"));
        sae.c = v0;
        v0.allowCoreThreadTimeOut(true);
    }

    public static Future a(Runnable p0){
        return sae.c.submit(p0);
    }

    public static ScheduledThreadPoolExecutor a(){
        if(sae.a == null){
            synchronized (sae.class) {
                if (sae.a == null) {
                    ScheduledThreadPoolExecutor scheduledThr = new ScheduledThreadPoolExecutor(1, new sae$a("TNET"));
                    sae.a = scheduledThr;
                    scheduledThr.setKeepAliveTime(3, TimeUnit.SECONDS);
                    sae.a.allowCoreThreadTimeOut(true);
                }
            }
        }
        return sae.a;
    }
    public static AtomicInteger b(){
        return sae.b;
    }
    public static void b(Runnable p0){
        try{
            sae.a().execute(p0);
            return;
        }catch(java.lang.Throwable e3){
            e3.printStackTrace();
        }
    }

}
