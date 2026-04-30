package org.android.spdy;

import java.util.Map;

public interface Intenalcb {

    void bioPingRecvCallback(SpdySession p0,int p1);
    byte[] getSSLMeta(SpdySession p0);
    int putSSLMeta(SpdySession p0,byte[] p1);
    void spdyCustomControlFrameFailCallback(SpdySession p0,Object p1,int p2,int p3);
    void spdyCustomControlFrameRecvCallback(SpdySession p0,Object p1,int p2,int p3,int p4,int p5,byte[] p6);
    void spdyDataChunkRecvCB(SpdySession p0,boolean p1,long p2,SpdyByteArray p3,int p4);
    void spdyDataRecvCallback(SpdySession p0,boolean p1,long p2,int p3,int p4);
    void spdyDataSendCallback(SpdySession p0,boolean p1,long p2,int p3,int p4);
    void spdyOnStreamResponse(SpdySession p0, long p1, Map p2, int p3);
    void spdyPingRecvCallback(SpdySession p0,long p1,Object p2);
    void spdyRequestRecvCallback(SpdySession p0,long p1,int p2);
    void spdySessionCloseCallback(SpdySession p0,Object p1,SuperviseConnectInfo p2,int p3);
    void spdySessionConnectCB(SpdySession p0,SuperviseConnectInfo p1);
    void spdySessionFailedError(SpdySession p0,int p1,Object p2);
    void spdySessionOnWritable(SpdySession p0,Object p1,int p2);
    void spdyStreamCloseCallback(SpdySession p0,long p1,int p2,int p3,SuperviseData p4);
}
