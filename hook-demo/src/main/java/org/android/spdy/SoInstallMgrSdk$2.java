package org.android.spdy;

import android.text.TextUtils;

import tb.ihu;
import tb.ihw;

public class SoInstallMgrSdk$2 implements ihu {
    @Override
    public void onFetchFinished(ihw p0) {
        boolean b;
        int i = 0;
        int i1 = 2;
        SoInstallMgrSdk.access$302((System.currentTimeMillis() - SoInstallMgrSdk.fetchRemoteSOStartTime));
        if (!TextUtils.isEmpty(p0.b())) {
            SoInstallMgrSdk.access$402(p0.b());
            int i2 = (b = SpdyAgent.pluginLoadZstdSo(SoInstallMgrSdk.access$400()))? 1: 2;
            SoInstallMgrSdk.loadZstdStat = i2;
        }else {
            SoInstallMgrSdk.loadZstdStat = p0.f().getErrorCode();
        }
    }
}
