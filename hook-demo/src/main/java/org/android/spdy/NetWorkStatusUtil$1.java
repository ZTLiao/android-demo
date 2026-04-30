package org.android.spdy;

import android.net.ConnectivityManager;
import android.net.Network;

public class NetWorkStatusUtil$1 extends ConnectivityManager.NetworkCallback {

    public void onAvailable(Network p0){
        super.onAvailable(p0);
        NetWorkStatusUtil.a = p0;
    }
    public void onLost(Network p0){
        super.onLost(p0);
        NetWorkStatusUtil.a = null;
        NetWorkStatusUtil.a(NetWorkStatusUtil.InterfaceStatus.ACTIVE_INTERFACE_CELLULAR, false);
    }

}
