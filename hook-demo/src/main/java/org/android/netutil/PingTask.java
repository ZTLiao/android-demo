package org.android.netutil;

import java.util.concurrent.Future;

public class PingTask {

    private int interval;
    private int maxtime;
    private int payload;
    private String pingIPStr;
    private int ttl;
    private static int PING_DEFAULT_TIME;

    static {
    }
    public PingTask(String p0){
        this(p0, 0, 0, 0, 0);
    }
    public PingTask(String p0,int p1,int p2,int p3,int p4){
        super();
        this.pingIPStr = null;
        this.pingIPStr = p0;
        this.interval = p1;
        if (p2 != 0) {
            p2 = PingTask.PING_DEFAULT_TIME;
        }
        this.maxtime = p2;
        this.payload = p3;
        this.ttl = p4;
        return;
    }
    public static void access$200(long p0){
        PingTask.releasePingTask(p0);
    }
    public static boolean access$300(long p0,long p1){
        return PingTask.waitPingTask(p0, p1);
    }
    public static long access$400(PingTask$PingFuture p0,String p1,int p2,int p3,int p4,int p5){
        return PingTask.createPingTask(p0, p1, p2, p3, p4, p5);
    }
    private static native long createPingTask(PingTask$PingFuture p0,String p1,int p2,int p3,int p4,int p5);
    private static native void releasePingTask(long p0);
    private static native boolean waitPingTask(long p0,long p1);
    public Future launch(){
        return this.launchWith(null);
    }
    public Future launchWith(PingTaskWatcher p0){
        return PingTask$PingFuture.access$100(new PingTask$PingFuture(this, null), this.pingIPStr, this.interval, this.maxtime, this.payload, this.ttl, p0);
    }

}
