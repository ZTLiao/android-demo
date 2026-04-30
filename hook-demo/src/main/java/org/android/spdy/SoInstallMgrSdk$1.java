package org.android.spdy;

import android.text.TextUtils;

import java.util.Iterator;

import tb.ihu;
import tb.ihw;

public class SoInstallMgrSdk$1 implements ihu {
    @Override
    public void onFetchFinished(ihw p0) {
        SoInstallMgrSdk.access$002((System.currentTimeMillis() - SoInstallMgrSdk.fetchLocalSOStartTime));
        if (!TextUtils.isEmpty(p0.b())) {
            SoInstallMgrSdk.access$102(p0.b());
            boolean b = SpdyAgent.pluginLoadQuicSo(SoInstallMgrSdk.access$100());
            SoInstallMgrSdk.loadQuicSucc = b;
            SoInstallMgrSdk.loadQuicStat = (b)? 1: 2;
        }else {
            SoInstallMgrSdk.loadQuicStat = p0.f().getErrorCode();
        }
        Iterator<IPluginFetchCallback> iterator = SoInstallMgrSdk.access$200().iterator();
        while (iterator.hasNext()) {
            IPluginFetchCallback iPluginFetch = iterator.next();
            try{
                iPluginFetch.onFetchFinished(SoInstallMgrSdk.loadQuicSucc, null);
            }catch(java.lang.Exception e0){
                e0.printStackTrace();
            }
        }
    }
}
