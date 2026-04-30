package org.android.spdy;

import java.util.Map;

public class SpdyDataProvider {

    public byte[] data;
    public boolean finished;
    public Map postBody;

    public SpdyDataProvider(Map p0){
        super();
        this.finished = true;
        this.data = null;
        this.postBody = p0;
    }
    public SpdyDataProvider(byte[] p0){
        super();
        this.finished = true;
        this.data = p0;
        this.postBody = null;
    }
}
