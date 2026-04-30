package org.android.spdy;

public interface SslCertcb {

    void getPerformance(SpdySession p0,SslPermData p1);
    SslPublickey getPublicKey(SpdySession p0);
    int putCertificate(SpdySession p0,byte[] p1,int p2);
}
