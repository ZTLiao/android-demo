package org.android.netutil;

import org.android.spdy.SpdyAgent;

public class UdpConnectType {

    public UdpConnectType(){
        super();
    }
    public static boolean a(){
        int i = 0;
        if(SpdyAgent.checkLoadSucc() && UdpConnectType.nativeTestUdpConnectIpv4() != 0){
            return true;
        }else {
            return false;
        }
    }
    public static boolean b(){
        int i = 0;
        if(SpdyAgent.checkLoadSucc() && UdpConnectType.nativeTestUdpConnectIpv6() != 0){
            return true;
        }else {
            return false;
        }
    }
    private static native int nativeTestUdpConnectIpv4();
    private static native int nativeTestUdpConnectIpv6();
}
