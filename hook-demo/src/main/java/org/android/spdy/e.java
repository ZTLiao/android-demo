package org.android.spdy;

import java.util.Map;

public class e implements Intenalcb {
    public void bioPingRecvCallback(SpdySession p0,int p1){
        if (p0.sessionCallBack != null) {
            p0.sessionCallBack.bioPingRecvCallback(p0, p1);
            return;
        }
    }
    public byte[] getSSLMeta(SpdySession p0){
        if (p0.sessionCallBack != null) {
            return p0.sessionCallBack.getSSLMeta(p0);
        }
        return null;
    }
    public int putSSLMeta(SpdySession p0,byte[] p1){
        if (p0.sessionCallBack != null) {
            return p0.sessionCallBack.putSSLMeta(p0, p1);
        }
        return -1;
    }
    public void spdyCustomControlFrameFailCallback(SpdySession p0,Object p1,int p2,int p3){
        if (p0.sessionCallBack != null) {
            p0.sessionCallBack.spdyCustomControlFrameFailCallback(p0, p1, p2, p3);
            return;
        }
    }
    public void spdyCustomControlFrameRecvCallback(SpdySession p0,Object p1,int p2,int p3,int p4,int p5,byte[] p6){
        if (p0.sessionCallBack != null) {
            p0.sessionCallBack.spdyCustomControlFrameRecvCallback(p0, p1, p2, p3, p4, p5, p6);
            return;
        }
    }
    public void spdyDataChunkRecvCB(SpdySession p0,boolean p1,long p2,SpdyByteArray p3,int p4){
        SpdyStreamContext spdyStream;
        int i = p4;
        long l = NetTimeGaurd.begin();
        if ((spdyStream = p0.getSpdyStream(i)) != null && spdyStream.callBack != null) {
            spdyStream.callBack.spdyDataChunkRecvCB(p0, p1, p2, p3, spdyStream.streamContext);
        }
        NetTimeGaurd.end("spdyDataChunkRecvCB", 3, l);
    }
    public void spdyDataRecvCallback(SpdySession p0,boolean p1,long p2,int p3,int p4){
        SpdyStreamContext spdyStream;
        int i = p4;
        long l = NetTimeGaurd.begin();
        if ((spdyStream = p0.getSpdyStream(i)) != null && spdyStream.callBack != null) {
            spdyStream.callBack.spdyDataRecvCallback(p0, p1, p2, p3, spdyStream.streamContext);
        }
        NetTimeGaurd.end("spdyDataRecvCallback", 3, l);
    }
    public void spdyDataSendCallback(SpdySession p0,boolean p1,long p2,int p3,int p4){
        SpdyStreamContext spdyStream;
        if ((spdyStream = p0.getSpdyStream(p4)) != null && spdyStream.callBack != null) {
            spdyStream.callBack.spdyDataSendCallback(p0, p1, p2, p3, spdyStream.streamContext);
            return;
        }
    }
    public void spdyOnStreamResponse(SpdySession p0,long p1,Map p2,int p3){
        SpdyStreamContext spdyStream;
        int i = p3;
        NetTimeGaurd.start(3);
        long l = NetTimeGaurd.begin();
        if ((spdyStream = p0.getSpdyStream(i)) != null && spdyStream.callBack != null) {
            spdyStream.callBack.spdyOnStreamResponse(p0, p1, p2, spdyStream.streamContext);
        }
        NetTimeGaurd.end("spdyOnStreamResponse", 3, l);
    }
    public void spdyPingRecvCallback(SpdySession p0,long p1,Object p2){
        int i = 1;
        NetTimeGaurd.start(i);
        if (p0.sessionCallBack != null) {
            p0.sessionCallBack.spdyPingRecvCallback(p0, p1, p2);
            NetTimeGaurd.end("spdyPingRecvCallback", i, NetTimeGaurd.begin());
        }
        NetTimeGaurd.finish(i);
    }
    public void spdyRequestRecvCallback(SpdySession p0,long p1,int p2){
        SpdyStreamContext spdyStream;
        long l = NetTimeGaurd.begin();
        if ((spdyStream = p0.getSpdyStream(p2)) != null && spdyStream.callBack != null) {
            spdyStream.callBack.spdyRequestRecvCallback(p0, p1, spdyStream.streamContext);
        }
        NetTimeGaurd.end("spdyPingRecvCallback", 3, l);
    }
    public void spdySessionCloseCallback(SpdySession p0,Object p1,SuperviseConnectInfo p2,int p3){
        if (p0.sessionCallBack != null) {
            p0.sessionCallBack.spdySessionCloseCallback(p0, p1, p2, p3);
            return;
        }
    }
    public void spdySessionConnectCB(SpdySession p0,SuperviseConnectInfo p1){
        int i = 0;
        NetTimeGaurd.start(i);
        if (p0.sessionCallBack != null) {
            p0.sessionCallBack.spdySessionConnectCB(p0, p1);
            NetTimeGaurd.end("spdySessionConnectCB", i, NetTimeGaurd.begin());
        }
        NetTimeGaurd.finish(i);
    }
    public void spdySessionFailedError(SpdySession p0,int p1,Object p2){
        NetTimeGaurd.start(2);
        if (p0.sessionCallBack != null) {
            p0.sessionCallBack.spdySessionFailedError(p0, p1, p2);
            p0.clearAllStreamCb();
            NetTimeGaurd.end("spdySessionFailedError", 2, NetTimeGaurd.begin());
        }
        NetTimeGaurd.finish(2);
    }
    public void spdySessionOnWritable(SpdySession p0,Object p1,int p2){
        NetTimeGaurd.start(2);
        if (p0.sessionCallBack != null && p0.sessionCallBack instanceof SessionExtraCb) {
            p0.sessionCallBack.spdySessionOnWritable(p0, p1, p2);
            NetTimeGaurd.end("spdySessionOnWritable", 2, NetTimeGaurd.begin());
        }
        NetTimeGaurd.finish(2);
    }
    public void spdyStreamCloseCallback(SpdySession p0,long p1,int p2,int p3,SuperviseData p4){
        SpdyStreamContext spdyStream;
        int i = p3;
        long l = NetTimeGaurd.begin();
        if ((spdyStream = p0.getSpdyStream(i)) != null && spdyStream.callBack != null) {
            spdyStream.callBack.spdyStreamCloseCallback(p0, p1, p2, spdyStream.streamContext, p4);
            p0.removeSpdyStream(i);
        }
        NetTimeGaurd.end("spdyStreamCloseCallback", 3, l);
        NetTimeGaurd.finish(3);
    }

}
