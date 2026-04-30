package org.android.spdy;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

public class SoInstallMgrSdk {
    private static String QUIC_SO_NAME;
    private static String TAG;
    private static String TNET_SO_NAME;
    private static String ZSTD_SO_NAME;
    public static long fetchLocalSOStartTime;
    private static long fetchQuicTime;
    public static long fetchRemoteSOStartTime;
    private static long fetchZstdTime;
    private static AtomicBoolean isLocalSOInit;
    private static AtomicBoolean isRemoteSOInit;
    public static int loadQuicStat;
    public static boolean loadQuicSucc;
    public static int loadZstdStat;
    private static String mQuicSoPath;
    private static String mZstdSoPath;
    private static CopyOnWriteArraySet quicListeners;

    static {
        SoInstallMgrSdk.isLocalSOInit = new AtomicBoolean(false);
        SoInstallMgrSdk.quicListeners = new CopyOnWriteArraySet();
        SoInstallMgrSdk.fetchLocalSOStartTime = 0;
        SoInstallMgrSdk.fetchQuicTime = 0;
        SoInstallMgrSdk.loadQuicSucc = false;
        SoInstallMgrSdk.loadQuicStat = -1;
        SoInstallMgrSdk.isRemoteSOInit = new AtomicBoolean(false);
        SoInstallMgrSdk.fetchRemoteSOStartTime = 0;
        SoInstallMgrSdk.fetchZstdTime = 0;
        SoInstallMgrSdk.loadZstdStat = -1;
    }

    public static long access$002(long p0) {
        SoInstallMgrSdk.fetchQuicTime = p0;
        return p0;
    }

    public static String access$100() {
        return SoInstallMgrSdk.mQuicSoPath;
    }

    public static String access$102(String p0) {
        SoInstallMgrSdk.mQuicSoPath = p0;
        return p0;
    }

    public static CopyOnWriteArraySet access$200() {
        return SoInstallMgrSdk.quicListeners;
    }

    public static long access$302(long p0) {
        SoInstallMgrSdk.fetchZstdTime = p0;
        return p0;
    }

    public static String access$400() {
        return SoInstallMgrSdk.mZstdSoPath;
    }

    public static String access$402(String p0) {
        SoInstallMgrSdk.mZstdSoPath = p0;
        return p0;
    }

    public static void fetchLocalSoAndPluginLoad() {
        if (SoInstallMgrSdk.isLocalSOInit.compareAndSet(false, true)) {
            SoInstallMgrSdk.fetchLocalSOStartTime = System.currentTimeMillis();
            SoInstallMgrSdk.fetchQuicSoAndPluginLoad();
        }
    }

    public static void fetchQuicSoAndPluginLoad() {
        try {
            //TODO
            //ihq.b().a("xquic", new SoInstallMgrSdk$1());
        } catch (java.lang.Throwable e0) {
            e0.printStackTrace();
        }
    }

    public static void fetchRemoteSoAndPluginLoad() {
        if (SoInstallMgrSdk.isRemoteSOInit.compareAndSet(false, true)) {
            SoInstallMgrSdk.fetchRemoteSOStartTime = System.currentTimeMillis();
            SoInstallMgrSdk.fetchZstdSoAndPluginLoad();
        }
    }

    public static void fetchZstdSoAndPluginLoad() {
        try {
            //TODO
            //ihq.b().a("zstd", new SoInstallMgrSdk$2());
            return;
        } catch (java.lang.Throwable e0) {
            e0.printStackTrace();
        }
    }

    public static Long getFetchQuicTime() {
        long l = SoInstallMgrSdk.fetchQuicTime;
        if ((l) <= 0) {
            l = System.currentTimeMillis() - SoInstallMgrSdk.fetchLocalSOStartTime;
        }
        return Long.valueOf(l);
    }

    public static Long getFetchZstdTime() {
        long l = SoInstallMgrSdk.fetchZstdTime;
        if ((l) <= 0) {
            l = System.currentTimeMillis() - SoInstallMgrSdk.fetchRemoteSOStartTime;
        }
        return Long.valueOf(l);
    }

    public static boolean loadTnetSo() {
        boolean i = false;
        try {
            System.loadLibrary("tnet-4.0.0");
            i = true;
        } catch (java.lang.Throwable e0) {
            e0.printStackTrace();
        }
        return i;
    }

    public static void registerQuicListener(IPluginFetchCallback p0) {
        if (p0 != null) {
            SoInstallMgrSdk.quicListeners.add(p0);
        }
    }

    public static void unregisterQuicListener(IPluginFetchCallback p0) {
        SoInstallMgrSdk.quicListeners.remove(p0);
    }

}
