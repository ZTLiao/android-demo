package org.android.spdy;

import android.net.ConnectivityManager;
import android.net.Network;

public class NetWorkStatusUtil$2 extends ConnectivityManager.NetworkCallback {

    public void onAvailable(Network p0){
        super.onAvailable(p0);
        NetWorkStatusUtil.b = p0;
        NetWorkStatusUtil.a(NetWorkStatusUtil.InterfaceStatus.ACTIVE_INTERFACE_WIFI, true);
    }
    public void onLost(Network p0){
        super.onLost(p0);
        NetWorkStatusUtil.b = null;
        NetWorkStatusUtil.a(NetWorkStatusUtil.InterfaceStatus.ACTIVE_INTERFACE_WIFI, false);
    }

}
