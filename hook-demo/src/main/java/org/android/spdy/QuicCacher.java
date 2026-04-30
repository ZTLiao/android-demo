package org.android.spdy;

import android.content.Context;

public interface QuicCacher {

    void init(Context p0);
    byte[] load(String p0);
    void remove(String p0);
    boolean store(String p0,String p1);
}
