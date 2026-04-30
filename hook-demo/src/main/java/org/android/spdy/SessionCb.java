package org.android.spdy;

public interface SessionCb {

    void bioPingRecvCallback(SpdySession p0,int p1);
    byte[] getSSLMeta(SpdySession p0);
    int putSSLMeta(SpdySession p0,byte[] p1);
    void spdyCustomControlFrameFailCallback(SpdySession p0,Object p1,int p2,int p3);
    void spdyCustomControlFrameRecvCallback(SpdySession p0,Object p1,int p2,int p3,int p4,int p5,byte[] p6);
    void spdyPingRecvCallback(SpdySession p0,long p1,Object p2);
    void spdySessionCloseCallback(SpdySession p0,Object p1,SuperviseConnectInfo p2,int p3);
    void spdySessionConnectCB(SpdySession p0,SuperviseConnectInfo p1);
    void spdySessionFailedError(SpdySession p0,int p1,Object p2);
}
