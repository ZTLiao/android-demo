package org.android.netutil;

public class NetListener {

    public long native_ptr;
    public NetListenerType netListenerType;

    public NetListener(NetListenerType p0){
        super();
        this.netListenerType = p0;
        this.native_ptr = 0;
    }
}
