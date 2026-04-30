package org.android.spdy;

public class SpdyAgent$1 implements Runnable {

    public final SpdyAgent a;

    public SpdyAgent$1(SpdyAgent p0){
        this.a = p0;
    }


    @Override
    public void run() {
        int i = 0;
        this.a.InitializeSecurityStuff();
    }
}
