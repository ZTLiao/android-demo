package org.android.spdy;

public class SpdyStreamContext {

    public Spdycb callBack;
    public Object streamContext;
    public int streamId;

    public SpdyStreamContext(Object p0,Spdycb p1){
        this.streamContext = p0;
        this.callBack = p1;
    }
}
