package org.android.spdy;

import java.util.Map;

public interface Spdycb {

    void spdyDataChunkRecvCB(SpdySession p0,boolean p1,long p2,SpdyByteArray p3,Object p4);
    void spdyDataRecvCallback(SpdySession p0,boolean p1,long p2,int p3,Object p4);
    void spdyDataSendCallback(SpdySession p0,boolean p1,long p2,int p3,Object p4);
    void spdyOnStreamResponse(SpdySession p0, long p1, Map p2, Object p3);
    void spdyRequestRecvCallback(SpdySession p0,long p1,Object p2);
    void spdyStreamCloseCallback(SpdySession p0,long p1,int p2,Object p3,SuperviseData p4);
}
