package org.android.netutil;

import java.util.concurrent.Future;

public class NetUtils {
    public static String getDefaultGateway(String p0){
        return NetUtils.native_GetDefaultGateway(p0);
    }
    public static String getPreferNextHop(String p0){
        int i = 1;
        return NetUtils.getPreferNextHop(p0, i);
    }
    public static String getPreferNextHop(String p0,int p1){
        Future uFuture;
        PingResponse pingResponse;
        try{
            PingTask v6 = new PingTask(p0, 0, 1, 0, p1);
            if ((uFuture = v6.launch()) != null && (pingResponse = (PingResponse) uFuture.get()) != null) {
                return pingResponse.b();
            }
        }catch(java.lang.Exception e7){
            e7.printStackTrace();
        }
        return null;
    }
    private static native long native_CreateAndRegister(long p0,NetListener p1);
    private static native String native_GetDefaultGateway(String p0);
    private static native void native_UnRegister(long p0);
    public static boolean registerNetListener(NetListener p0){
        if(p0 != null && ((p0.native_ptr = NetUtils.native_CreateAndRegister(p0.netListenerType.getValue(), p0))) != 0){
            return true;
        }else {
            return false;
        }
    }
    public static void unRegisterNetListener(NetListener p0){
        if(p0 != null && (p0.native_ptr) != 0){
            NetUtils.native_UnRegister(p0.native_ptr);
        }
        return;
    }

}
