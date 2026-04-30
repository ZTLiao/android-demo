package org.android.spdy;

public class SpdyAgent$4 implements Runnable {

    public final String a;
    public final SpdyAgent b;

    public SpdyAgent$4(SpdyAgent p0,String p1){
        this.b = p0;
        this.a = p1;
    }
    public void run(){
        SpdyAgent.access$100().remove(this.a);
        SpdyAgent.access$000(this.b).remove(this.a);
    }
}
