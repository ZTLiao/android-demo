package org.android.spdy;

public class SpdyAgent$2 implements Runnable {
    public final String a;
    public final String b;
    public final SpdyAgent c;

    public SpdyAgent$2(SpdyAgent p0,String p1,String p2){
        this.c = p0;
        this.a = p1;
        this.b = p2;
    }
    public void run(){
        SpdyAgent.access$000(this.c).store(this.a, this.b);
    }
}
