package org.android.spdy;

import tb.sab$a;

public class SpdySession$3 implements sab$a {

    public final SpdySession a;

    public SpdySession$3(SpdySession p0){
        this.a = p0;
    }

    @Override
    public void a() {
        this.a.notifyLifecycleEvent(true);
    }

    @Override
    public void b() {
        this.a.notifyLifecycleEvent(false);
    }
}
