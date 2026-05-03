package org.android.spdy;

import android.content.Context;
import android.text.TextUtils;

import org.android.netutil.UtilTool;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import tb.sab;
import tb.sac;
import tb.sad;
import tb.sae;

public class SpdyAgent {
    private AccsSSLCallback accsSSLCallback;
    private long agentNativePtr;
    private AtomicBoolean isAgentClosed;
    private String proxyPassword;
    private String proxyUsername;
    private HashMap sessionMgr;
    private LinkedList sessionQueue;
    private QuicCacher xqcCache;
    public static int ACCS_ONLINE_SERVER;
    public static int ACCS_TEST_SERVER;
    private static String CACHE_TIME_SPLIT;
    private static int JNI_ERR;
    private static int JNI_OK;
    private static int KB32;
    private static int KB8;
    private static int MAX_LONG_SESSION_COUNT;
    private static int MB5;
    private static String TAG;
    private static Context context;
    private static Object domainHashLock;
    private static HashMap domainHashMap;
    public static boolean enableDebug;
    public static boolean enableTimeGaurd;
    private static SpdyAgent gSingleInstance;
    private static Object loadSolock;
    private static boolean loadTnetSoSucc;
    private static int mSessionUniqueIndex;
    private static Map mStorageMap;
    private static Lock r;
    private static ReentrantReadWriteLock rwLock;
    private static Lock w;
    public static int wifiConsecutiveFailCount;

    static {
        SpdyAgent.loadSolock = new Object();
        SpdyAgent.gSingleInstance = null;
        SpdyAgent.wifiConsecutiveFailCount = 0;
        SpdyAgent.context = null;
        ReentrantReadWriteLock reentrantRea = new ReentrantReadWriteLock();
        SpdyAgent.rwLock = reentrantRea;
        SpdyAgent.r = reentrantRea.readLock();
        SpdyAgent.w = SpdyAgent.rwLock.writeLock();
        SpdyAgent.domainHashLock = new Object();
        SpdyAgent.domainHashMap = new HashMap();
        SpdyAgent.mSessionUniqueIndex = 0;
        SpdyAgent.mStorageMap = new ConcurrentHashMap();
        SpdyAgent.enableDebug = false;
        SpdyAgent.enableTimeGaurd = false;
    }

    private SpdyAgent(Context p0, AccsSSLCallback p1) {
        int i2;
        this.isAgentClosed = new AtomicBoolean();
        this.proxyUsername = null;
        this.proxyPassword = null;
        this.sessionMgr = new HashMap(5);
        this.sessionQueue = new LinkedList();
        SecurityGuardCacherImp securityGuar = new SecurityGuardCacherImp();
        try {
            this.xqcCache = securityGuar;
            SpdyAgent.setContext(p0);
            SpdyAgent.loadTnetSoSucc = SoInstallMgrSdk.loadTnetSo();
            sac.a(SpdyAgent.context);
            SoInstallMgrSdk.fetchLocalSoAndPluginLoad();
        } catch (Throwable e3) {
            e3.printStackTrace();
        }
        int i = 0;
        try {
            int i1 = sad.I();
            if (sad.q()) {
                i2 = 1;
                this.agentNativePtr = this.initAgent(i, i1, i2);
                this.accsSSLCallback = p1;
                if (sad.t()) {
                    sae.b(new SpdyAgent$1(this));
                }
            } else {
                i2 = 0;
                this.agentNativePtr = this.initAgent(i, i1, i2);
                this.accsSSLCallback = p1;
                if (sad.t()) {
                    sae.b(new SpdyAgent$1(this));
                }
            }
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
        }
        NetWorkStatusUtil.a(SpdyAgent.context);
        sab.a(SpdyAgent.context);
        this.isAgentClosed.set(false);
    }

    private int AndroidVerifyProof(String[] p0) {
        return QuicProofVerifier.VerifyProof(null, p0);
    }

    public static void InvlidCharJudge(byte[] p0, byte[] p1) {
        int i2;
        int i3;
        int i = 0;
        int i1 = 0;
        while (i1 < p0.length) {
            if ((i2 = p0[i1] & 0x00ff) < 32 || (i2 = p0[i1] & 0x00ff) > 126) {
                p0[i1] = 63;
            }
            i1 = i1 + 1;
        }
        while (i < p1.length) {
            if ((i3 = p1[i] & 0x00ff) < 32 || (i3 = p1[i] & 0x00ff) > 126) {
                p1[i] = 63;
            }
            i = i + 1;
        }
    }

    public static QuicCacher access$000(SpdyAgent p0) {
        return p0.xqcCache;
    }

    public static Map access$100() {
        return SpdyAgent.mStorageMap;
    }

    private void agentIsOpen() {
        if (!this.isAgentClosed.get()) {
            this.checkLoadSo();
            return;
        } else {
            throw new SpdyErrorException("SPDY_JNI_ERR_ASYNC_CLOSE", -1104);
        }
    }

    private int bindSocketFd2NetworkInterfaceN(int p0, int p1) {
        int i = -1;
        if (p1 == NetWorkStatusUtil.InterfaceStatus.ACTIVE_INTERFACE_WIFI.getInterfaceStatus()) {
            i = NetWorkStatusUtil.b(p0);
        } else if (p1 == NetWorkStatusUtil.InterfaceStatus.ACTIVE_INTERFACE_CELLULAR.getInterfaceStatus()) {
            i = NetWorkStatusUtil.a(p0);
        }
        return i;
    }

    private void bioPingRecvCallback(SpdySession p0, int p1) {
        String str = "tnetsdk.SpdyAgent";
        if (p0 != null && p0.intenalcb != null) {
            p0.intenalcb.bioPingRecvCallback(p0, p1);
            return;
        }
    }

    private void checkLoadSo() {
        int i = 1;
        int i1 = 0;
        if ((SoInstallMgrSdk.fetchLocalSOStartTime - i1) != 0) {
            SoInstallMgrSdk.fetchLocalSoAndPluginLoad();
        }
        if ((SoInstallMgrSdk.fetchRemoteSOStartTime - i1) != 0) {
            SoInstallMgrSdk.fetchRemoteSoAndPluginLoad();
        }
        if (SpdyAgent.loadTnetSoSucc) {
            return;
        } else {
            try {
                Object loadSolock = SpdyAgent.loadSolock;
                synchronized (loadSolock) {
                    if (SpdyAgent.loadTnetSoSucc) {
                        return;
                    } else {
                        SpdyAgent.loadTnetSoSucc = SoInstallMgrSdk.loadTnetSo();
                        int i2 = sad.I();
                        if (!sad.q()) {
                            i = 0;
                        }
                        this.agentNativePtr = this.initAgent(0, i2, i);
                    }
                }
            } catch (Throwable e0) {
                e0.printStackTrace();
            }
            if (SpdyAgent.loadTnetSoSucc) {
                return;
            } else {
                throw new SpdyErrorException("So load fail", -1108);
            }
        }
    }

    public static boolean checkLoadSucc() {
        return SpdyAgent.loadTnetSoSucc;
    }

    private native int closeSessionN(long p0);

    private byte[] commonCacheLoad(String p0, int p1) {
        byte[] uobyteArray1;
        int i = 1;
        int i1 = 0;
        byte[] uobyteArray = null;
        try {
            if ((uobyteArray1 = (byte[]) SpdyAgent.mStorageMap.get(p0)) != null) {
                return uobyteArray1;
            }
            if ((uobyteArray1 = this.xqcCache.load(p0)) == null) {
                return uobyteArray;
            }
            if (p1 == i) {
                return uobyteArray1;
            }
            long l = System.currentTimeMillis();
            String str = new String(uobyteArray1, i1, uobyteArray1.length);
            int i2 = str.indexOf("@@@");
            if (-1 == i2) {
                return uobyteArray;
            }
            if (((sad.o() + Long.parseLong(str.substring(i1, i2))) - l) < 0) {
                sae.b(new SpdyAgent$3(this, p0));
            }
            return (str.substring((i2 + 3))).getBytes();
        } catch (Throwable e12) {
            e12.printStackTrace();
            return uobyteArray;
        }
    }

    private void commonCacheRemove(String p0, int p1) {
        try {
            sae.b(new SpdyAgent$4(this, p0));
            return;
        } catch (Throwable e0) {
        }
    }

    private boolean commonCacheStore(String p0, String p1, int p2) {
        boolean b = false;
        if (!TextUtils.isEmpty(p0) && !TextUtils.isEmpty(p1)) {
            try {
                SpdyAgent.mStorageMap.put(p0, p1.getBytes());
                String str = String.valueOf(System.currentTimeMillis());
                if (1 != p2) {
                    p1 = str + "@@@" + p1;
                }
                sae.b(new SpdyAgent$2(this, p0, p1));
            } catch (Throwable e0) {
                b = false;
            }
            return b;
        } else {
            return false;
        }
    }

    public static int configIpStackMode(int p0) {
        if (SpdyAgent.loadTnetSoSucc) {
            return SpdyAgent.configIpStackModeN(p0);
        } else {
            return -1;
        }
    }

    private static native int configIpStackModeN(int p0);

    private native int configLogFileN(String p0, int p1, int p2);

    private native int configLogFileN(String p0, int p1, int p2, int p3);

    public static void configSwitchValueByKey(long p0, int p1, double p2, String p3) {
        if (SpdyAgent.loadTnetSoSucc) {
            SpdyAgent.configSwitchValueByKeyN(p0, p1, p2, p3);
        }
        return;
    }

    private static native int configSwitchValueByKeyN(long p0, int p1, double p2, String p3);

    private SpdySession createSession(String p0, String p1, Object p2, SessionCb p3, SessionCustomExtraCb p4, int p5, int p6, int p7, String p8, int p9, int p10, int p11, String p12, ArrayList p13, boolean p14, boolean p15, boolean p16, int p17) {
        boolean b1;
        char c;
        byte[] uobyteArray;
        SpdySession spdySession;
        String str6;
        SpdyAgent spdyAgent;
        byte[] uobyteArray2;
        byte[] uobyteArray3;
        byte[] uobyteArray6;
        SpdySession spdySession3;
        SpdyAgent oobject = this;
        String oobject1 = p0;
        Object oobject2 = p1;
        int i = p5;
        int i1 = p7;
        boolean b = p16;
        int i2 = 19;
        int i3 = 4;
        int i4 = 1;
        int i5 = 0;
        if (oobject1 != null) {
            if ((i & 0x02) != 0) {
                throw new SpdyErrorException("SPDY disable", -1107);
            }
            String hostFromDoma = SpdySession.getHostFromDomain(p1);
            boolean i6 = (!(b1 = sad.y(hostFromDoma))) ? false : (p17 != 0 ? true : false);
            int i7 = (sad.u() && b) ? 1 : 0;
            String[] stringArray = oobject1.split("/");
            i3 = stringArray[i5].lastIndexOf(58);
            String str = stringArray[i5].substring(i5, i3);
            String str1 = stringArray[i5].substring((i3 + i4));
            byte[] bytes = "0.0.0.0".getBytes();
            if (stringArray.length != i4 && ((i & 0x0104) == 0)) {
                stringArray = stringArray[i4].split(":");
                c = (char) Integer.parseInt(stringArray[i4]);
                uobyteArray = stringArray[i5].getBytes();
            } else {
                oobject1 = oobject1 + "/0.0.0.0:0";
                uobyteArray = bytes;
                c = 0;
            }
            String str2 = "_";
            String str3 = oobject1 + oobject2 + str2 + i + str2 + i7 + str2 + i6;
            if ((spdySession = oobject.getSpdySession(str3)) != null) {
                return spdySession;
            } else {
                SpdyAgent.w.lock();
                SpdySession spdySession1 = spdySession;
                boolean i8 = i6;
                String str4 = str;
                spdySession1 = new SpdySession(0, this, oobject1, p1, hostFromDoma, p8, p3, p4, p5, p6, p2, p12, p13, p14, p15, i7);
                SpdySession spdySession2 = spdySession1;
                String str5 = str3;
                spdySession2.setSessionPoolUniqueKey(str5);
                i1 = spdySession2.getConnectFastTimeout(p7);
                byte[] tunnelInfoBy = spdySession2.getTunnelInfoBytes();
                if (((p5 & 0x0100) == 0) && ((p5 & 0x04) == 0)) {
                    str6 = str4;
                } else {
                    str4 = sad.u(p8);
                    str6 = str4;
                }
                spdySession2.preProcessProtocol(tunnelInfoBy, str6);
                if (((spdySession2.getUsedProtocolMode() & 0x0200) == 0)) {
                    i6 = p16;
                    i2 = 0;
                } else {
                    byte[] uobyteArray7 = tunnelInfoBy;
                    i6 = p16;
                }
                boolean b2 = spdySession2.isConnectTryForceCellular(i6);
                byte[] uobyteArray1 = (p8 == null) ? null : p8.getBytes();
                int i9 = (p12 == null) ? 0 : p12.getBytes().length;
                int tmp;
                if (b1 && i8) {
                    tmp = SpdyAgent.mSessionUniqueIndex = SpdyAgent.mSessionUniqueIndex + 1;
                    spdyAgent = this;
                } else {
                    spdyAgent = this;
                    tmp = spdyAgent.getDomainHashIndex(p1 + b);
                }
                int i10 = tmp;
                if (spdyAgent.proxyUsername != null && spdyAgent.proxyPassword != null) {
                    uobyteArray2 = spdyAgent.proxyUsername.getBytes();
                    uobyteArray3 = spdyAgent.proxyPassword.getBytes();
                } else {
                    uobyteArray2 = null;
                    uobyteArray3 = uobyteArray2;
                }
                long agentNativeP = spdyAgent.agentNativePtr;
                byte[] bytes1 = str6.getBytes();
                char c1 = (char) Integer.parseInt(str1);
                int mode = spdySession2.getUsedProtocolMode();
                int sessionParam = spdySession2.getSessionParameter();
                byte[] uobyteArray4 = (str4 == null) ? null : str4.getBytes();
                byte[] uobyteArray5 = new byte[]{(byte) i2};
                int i11 = i1;
                p2 = spdySession2;
                String str7 = str5;
                long l = this.createSessionN(agentNativeP, spdySession2, i10, bytes1, c1, uobyteArray, c, uobyteArray2, uobyteArray3, p2, mode, p6, i11, uobyteArray1, p9, sessionParam, p10, p11, b2 ? 1 : 0, new byte[]{(byte) i9}, uobyteArray5, uobyteArray4);
                if (((1 & l) - 1) == 0) {
                    i5 = (int) (l >> 1);
                    l = 0;
                } else {
                    i5 = 0;
                }
                if (l > 0) {
                    spdySession3 = (SpdySession) p2;
                    spdySession3.setSessionNativePtr(l);
                    SpdyAgent l1 = this;
                    l1.sessionMgr.put(str7, spdySession3);
                    l1.sessionQueue.add(spdySession3);
                } else if (i5 != 0) {
                    spdySession3 = null;
                } else {
                    throw new SpdyErrorException("create session error: " + i5, i5);
                }
                SpdyAgent.w.unlock();
                return spdySession3;
            }
        } else {
            throw new SpdyErrorException("invalid param, authority null,", -1102);
        }
    }

    private native long createSessionN(long p0, SpdySession p1, int p2, byte[] p3, char p4, byte[] p5, char p6, byte[] p7, byte[] p8, Object p9, int p10, int p11, int p12, byte[] p13, int p14, int p15, int p16, int p17, int p18, byte[] p19, byte[] p20, byte[] p21);

    public static byte[] dataproviderToByteArray(SpdyRequest p0, SpdyDataProvider p1) {
        String str;
        SpdyAgent.headJudge(p0.getHeaders());
        if (p1 == null) {
            return null;
        }
        byte[] bytes = ((str = SpdyAgent.mapBodyToString(p1.postBody)) != null) ? str.getBytes() : p1.data;
        if (bytes != null && bytes.length >= 0x500000) {
            throw new SpdyErrorException("INVALID_PARAM:total=" + bytes.length, -1102);
        } else {
            return bytes;
        }
    }

    private native int freeAgent(long p0);

    private int getActiveInterfaceType() {
        return NetWorkStatusUtil.f.getInterfaceStatus();
    }

    public static Context getContext() {
        if (SpdyAgent.context == null) {
            SpdyAgent.context = UtilTool.b();
        }
        return SpdyAgent.context;
    }

    private int getDomainHashIndex(String p0) {
        Integer integer;
        int i = 1;
        synchronized (SpdyAgent.domainHashLock) {
            if ((integer = (Integer) SpdyAgent.domainHashMap.get(p0)) == null) {
                int i1 = SpdyAgent.mSessionUniqueIndex + i;
                SpdyAgent.mSessionUniqueIndex = i1;
                SpdyAgent.domainHashMap.put(p0, Integer.valueOf(i1));
                integer = Integer.valueOf(SpdyAgent.mSessionUniqueIndex);
            }
        }
        return integer.intValue();
    }

    public static SpdyAgent getInstance(Context p0, SpdyVersion p1, SpdySessionKind p2) {
        if (p0 != null && SpdyAgent.context == null) {
            SpdyAgent.context = p0;
        }
        return SpdyAgent.getInstance(p0, p1, p2, null);
    }

    public static SpdyAgent getInstance(Context p0, SpdyVersion p1, SpdySessionKind p2, AccsSSLCallback p3) {
        if (SpdyAgent.gSingleInstance == null) {
            synchronized (SpdyAgent.loadSolock) {
                if (SpdyAgent.gSingleInstance == null) {
                    SpdyAgent.gSingleInstance = new SpdyAgent(p0, p3);
                }
            }
        }
        return SpdyAgent.gSingleInstance;
    }

    private int getNetWorkStatus() {
        int i = 1;
        if (NetWorkStatusUtil.a()) {
            i = 0;
        }
        if (NetWorkStatusUtil.b()) {
            i = i | 0x02;
        }
        return i;
    }

    private byte[] getSSLMeta(SpdySession p0) {
        if (p0 != null && p0.intenalcb != null) {
            return p0.intenalcb.getSSLMeta(p0);
        } else {
            return null;
        }
    }

    private byte[] getSSLPublicKey(int p0, byte[] p1) {
        AccsSSLCallback taccsSSLCall;
        if ((taccsSSLCall = this.accsSSLCallback) == null) {
            return null;
        } else {
            return taccsSSLCall.getSSLPublicKey(p0, p1);
        }
    }

    private SpdySession getSpdySession(String p0) {
        SpdySession spdySession1;
        int i = 1;
        this.agentIsOpen();
        SpdyAgent.r.lock();
        SpdySession spdySession = (SpdySession) this.sessionMgr.get(p0);
        if (this.sessionMgr.size() < 150) {
            i = 0;
        }
        SpdyAgent.r.unlock();
        if (i == 0) {
            if (spdySession != null) {
                spdySession.increRefCount();
                return spdySession;
            } else {
                SpdyAgent.w.lock();
                try {
                    spdySession1 = (SpdySession) this.sessionMgr.get(p0);
                    SpdyAgent.w.unlock();
                } catch (Throwable e0) {
                    SpdyAgent.w.unlock();
                    spdySession1 = null;
                }
                if (spdySession1 != null) {
                    spdySession1.increRefCount();
                    return spdySession1;
                } else {
                    return null;
                }
            }
        } else {
            throw new SpdyErrorException("SPDY_SESSION_EXCEED_MAXED", -1105);
        }
    }

    public static void headJudge(Map p0) {
        int i = 0;
        if (p0 != null) {
            Iterator<Map.Entry<String, String>> iterator = p0.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> uEntry = (Map.Entry) iterator.next();
                String key = uEntry.getKey();
                String value = uEntry.getValue();
                SpdyAgent.InvlidCharJudge(key.getBytes(), value.getBytes());
                int i1 = key.length() + 1;
                i1 = i1 + value.length();
                i = i + i1;
                SpdyAgent.securityCheck(i, value.length());
            }
        }
        return;
    }

    private native long initAgent(int p0, int p1, int p2);

    public static boolean isQuicReady() {
        return SoInstallMgrSdk.loadQuicSucc;
    }

    private native void logFileCloseN();

    private native void logFileFlushN();

    private int logOutput(int p0, String p1, String p2, String p3) {
        int i = 0;
        if (!sad.a()) {
            return -1;
        } else {
            try {
                sae.a(new SpdyAgent$5(this, p0, p1, p2, p3));
                return i;
            } catch (Throwable e0) {
                return 0;
            }
        }
    }

    public static String mapBodyToString(Map p0) {
        int i = 0;
        StringBuilder str = new StringBuilder("");
        if (p0 == null) {
            return null;
        }
        Iterator<Map.Entry<String, String>> iterator = p0.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> uEntry = iterator.next();
            String key = uEntry.getKey();
            String value = uEntry.getValue();
            str = str.append(key).append('=').append(value).append('&');
            int i1 = key.length() + 1;
            i1 = i1 + value.length();
            i = i + i1;
            SpdyAgent.tableListJudge(i);
        }
        if (str.length() > 0) {
            str.setLength((str.length() - 1));
        }
        return str.toString();
    }

    public static String[] mapToByteArray(Map p0) {
        int i = 0;
        int i1 = 1;
        if (p0 != null && p0.size() > 0) {
            String[] stringArray = new String[(p0.size() << i1)];
            Iterator<Map.Entry<String, String>> iterator = p0.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> uEntry = iterator.next();
                stringArray[i] = uEntry.getKey();
                i1 = i + 1;
                stringArray[i1] = uEntry.getValue();
                i = i + 2;
            }
            return stringArray;
        } else {
            return null;
        }
    }

    public static boolean pluginLoadQuicSo(String p0) {
        if (SpdyAgent.loadTnetSoSucc && SpdyAgent.pluginLoadQuicSoN(p0) != 0) {
            return true;
        } else {
            return false;
        }
    }

    private static native int pluginLoadQuicSoN(String p0);

    public static boolean pluginLoadZstdSo(String p0) {
        if (SpdyAgent.loadTnetSoSucc && SpdyAgent.pluginLoadZstdSoN(p0) != 0) {
            return true;
        } else {
            return false;
        }
    }

    private static native int pluginLoadZstdSoN(String p0);

    private int putSSLMeta(SpdySession p0, byte[] p1) {
        if (p0 != null && p0.intenalcb != null) {
            return p0.intenalcb.putSSLMeta(p0, p1);
        } else {
            return -1;
        }
    }

    public static void registerQuicListener(IPluginFetchCallback p0) {
        SoInstallMgrSdk.registerQuicListener(p0);
    }

    public static void securityCheck(int p0, int p1) {
        if (p0 < 0x8000) {
            if (p1 < 8192) {
                return;
            }
            throw new SpdyErrorException("INVALID_PARAM:value=" + p1, -1102);
        } else {
            throw new SpdyErrorException("INVALID_PARAM:total1=" + p0, -1102);
        }
    }

    public static void setContext(Context p0) {
        if (p0 == null) {
            p0 = UtilTool.b();
        }
        SpdyAgent.context = p0;
        return;
    }

    private void spdyCustomControlFrameFailCallback(SpdySession p0, Object p1, int p2, int p3) {
        if (p0 != null && p0.intenalcb != null) {
            p0.intenalcb.spdyCustomControlFrameFailCallback(p0, p1, p2, p3);
            return;
        }
    }

    private void spdyCustomControlFrameRecvCallback(SpdySession p0, Object p1, int p2, int p3, int p4, int p5, byte[] p6, SuperviseData p7) {
        SpdySession oobject = p0;
        if (oobject != null && (oobject.intenalcb != null && oobject.customExtraCb == null)) {
            if (!p0.isForceUseCellular()) {
                SpdyAgent.wifiConsecutiveFailCount = 0;
            }
            long l = System.currentTimeMillis();
            if (oobject.customExtraCb != null) {
                oobject.customExtraCb.onCustomFrameRecvCallback(p0, p1, p2, p3, p4, p5, p6, p7, null);
            } else {
                oobject.intenalcb.spdyCustomControlFrameRecvCallback(p0, p1, p2, p3, p4, p5, p6);
            }
        }
    }

    private void spdyDataChunkRecvCB(SpdySession p0, boolean p1, int p2, SpdyByteArray p3, ByteBuffer p4, int p5, int p6) {
        SpdySession oobject = p0;
        int i = p2;
        SpdyByteArray oobject1 = p3;
        int i1 = p5;
        int i2 = 1;
        String str = "tnetsdk.SpdyAgent";
        if (i1 == i2) {
            oobject1.setDirectBufferMode(true);
            p3.setDirectByteBuffer(p4);
        }
        long l = (long) i & 0xffffffff;
        if (oobject != null && oobject.intenalcb != null) {
            oobject.intenalcb.spdyDataChunkRecvCB(p0, p1, l, p3, p6);
            return;
        }
    }

    private void spdyDataRecvCallback(SpdySession p0, boolean p1, int p2, int p3, int p4) {
        String str = "tnetsdk.SpdyAgent";
        long l = (long) p2 & 0xffffffff;
        if (p0 != null && p0.intenalcb != null) {
            p0.intenalcb.spdyDataRecvCallback(p0, p1, l, p3, p4);
            return;
        }
    }

    private void spdyDataSendCallback(SpdySession p0, boolean p1, int p2, int p3, int p4) {
        long l = (long) p2 & 0xffffffff;
        if (p0 != null && p0.intenalcb != null) {
            p0.intenalcb.spdyDataSendCallback(p0, p1, l, p3, p4);
            return;
        }
    }

    private void spdyPingRecvCallback(SpdySession p0, int p1, Object p2) {
        int i = 0;
        String str = "tnetsdk.SpdyAgent";
        if (p0 != null && p0.intenalcb != null) {
            if (!p0.isForceUseCellular() && p1 > 0) {
                SpdyAgent.wifiConsecutiveFailCount = i;
            }
            p0.intenalcb.spdyPingRecvCallback(p0, (long) p1, p2);
            return;
        }
    }

    private void spdyRequestRecvCallback(SpdySession p0, int p1, int p2) {
        long l = (long) p1 & 0xffffffff;
        if (p0 != null && p0.intenalcb != null) {
            p0.intenalcb.spdyRequestRecvCallback(p0, l, p2);
            return;
        }
    }

    private void spdySessionCloseCallback(SpdySession p0, Object p1, SuperviseConnectInfo p2, int p3) {
        int i = 2;
        int i1 = 0;
        if (p0 == null) {
            return;
        } else {
            p0.setSuperviseConnectInfoOnDisconnectedCB(p2);
            p0.checkWifiConsecutiveFailStatus(p3);
            if (p0.intenalcb != null) {
                p0.intenalcb.spdySessionCloseCallback(p0, p1, p2, p3);
            }
            p0.cleanUp();
            p0.releasePptr();
            return;
        }
    }

    private void spdySessionConnectCB(SpdySession p0, SuperviseConnectInfo p1) {
        int i = 2;
        int i1 = 1;
        int i2 = 0;
        String str = "tnetsdk.SpdyAgent";
        if (p0 != null && p0.intenalcb != null) {
            p0.setSuperviseConnectInfoOnConnectedCB(p1);
            p0.intenalcb.spdySessionConnectCB(p0, p1);
            return;
        }
    }

    private void spdySessionFailedError(SpdySession p0, int p1, Object p2, SuperviseConnectInfo p3) {
        int i = 4;
        int i1 = 2;
        int i2 = 1;
        int i3 = 0;
        if (p0 == null) {
            return;
        } else {
            p0.setSuperviseConnectInfoOnConnectedCB(p3);
            p0.setSuperviseConnectInfoOnDisconnectedCB(p3);
            p0.checkWifiConsecutiveFailStatus(p1);
            if (p0.intenalcb != null) {
                p0.intenalcb.spdySessionFailedError(p0, p1, p2);
            }
            p0.cleanUp();
            p0.releasePptr();
            return;
        }
    }

    private void spdySessionOnWritable(SpdySession p0, Object p1, int p2) {
        if (p0 != null && p0.intenalcb != null) {
            try {
                p0.intenalcb.spdySessionOnWritable(p0, p1, p2);
                return;
            } catch (Throwable e4) {
                e4.printStackTrace();
                return;
            }
        }
    }

    private void spdyStreamCloseCallback(SpdySession p0, int p1, int p2, int p3, SuperviseData p4) {
        SpdySession oobject = p0;
        int i = p1;
        int i1 = p2;
        SuperviseData oobject1 = p4;
        int i2 = 1;
        String str = "tnetsdk.SpdyAgent";
        long l = (long) i & 0xffffffff;
        if (oobject != null && oobject.intenalcb != null) {
            if (!p0.isForceUseCellular() && i1 != 0) {
                SpdyAgent.wifiConsecutiveFailCount = 0;
            }
            try {
                if (oobject1 != null) {
                    oobject1.spdySession = oobject;
                    if (p0.isQUIC() && oobject1.unreliableChannelMss != oobject.unreliableChannelMss) {
                        oobject.unreliableChannelMss = oobject1.unreliableChannelMss;
                    }
                    if (p0.isTunnel()) {
                        oobject1.tunnelType = oobject.mSuperviseConnectInfo.tunnelType;
                        if (p0.isTunnelProxyClose()) {
                            oobject1.tunnelDegraded = i2;
                            oobject1.tunnelErrorCode = oobject.mSuperviseConnectInfo.tunnelErrorCode;
                        }
                    }
                }
                oobject.intenalcb.spdyStreamCloseCallback(p0, l, p2, p3, p4);
                return;
            } catch (Exception e0) {
            }
        }
    }

    private void spdyStreamResponseRecv(SpdySession p0, int p1, byte[] p2, int[] p3, int p4) {
        int i = 0;
        if (p0 != null && p0.intenalcb != null) {
            String[] stringArray = new String[p3.length];
            c uoc = c.a();
            int i1 = 0;
            for (; i < p3.length; i = i + 2) {
                stringArray[i] = uoc.a(ByteBuffer.wrap(p2, i1, p3[i]));
                i1 = i1 + p3[i];
                int i2 = i + 1;
                stringArray[i2] = (p3[i2] > 32) ? new String(p2, i1, p3[i2]) : uoc.a(ByteBuffer.wrap(p2, i1, p3[i2]));
                i1 = i1 + p3[i2];
            }
            p0.intenalcb.spdyOnStreamResponse(p0, ((long) p1 & 0xffffffff), SpdyAgent.stringArrayToMap(stringArray), p4);
            return;
        }
    }

    public static Map stringArrayToMap(String[] p0) {
        int i1;
        List list;
        int i = 0;
        if (p0 == null) {
            return null;
        } else {
            HashMap hashMap = new HashMap(5);
            while (true) {
                if ((i1 = i + 2) > p0.length) {
                    return hashMap;
                }
                if (p0[i] != null) {
                    int i2 = i + 1;
                    if (p0[i2] != null) {
                        if ((list = (List) hashMap.get(p0[i])) == null) {
                            list = new ArrayList(1);
                            hashMap.put(p0[i], list);
                        }
                        list.add(p0[i2]);
                        i = i1;
                    }
                }
                break;
            }
            return null;
        }
    }

    public static void tableListJudge(int p0) {
        if (p0 < 0x500000) {
            return;
        } else {
            throw new SpdyErrorException("INVALID_PARAM:total2=" + p0, -1102);
        }
    }

    public static void unregisterQuicListener(IPluginFetchCallback p0) {
        SoInstallMgrSdk.unregisterQuicListener(p0);
    }

    public void InitializeSecurityStuff() {
        this.xqcCache.init(SpdyAgent.context);
        b.a().b();
    }

    public native String ResolveHost(String p0, String p1, int p2);

    public void clearSpdySession(String p0) {
        if (p0 == null) {
            return;
        } else {
            Lock w = SpdyAgent.w;
            try {
                w.lock();
                this.sessionMgr.remove(p0);
                SpdyAgent.w.unlock();
                return;
            } catch (Throwable e4) {
                e4.printStackTrace();
                SpdyAgent.w.unlock();
                return;
            }
        }
    }

    public void close() {
        int i = 1;
        if (!sad.p()) {
            return;
        } else if (!this.isAgentClosed.getAndSet(true)) {
            Lock w = SpdyAgent.w;
            try {
                w.lock();
                for (SpdySession spdySession = (SpdySession) this.sessionQueue.poll(); spdySession != null; spdySession = (SpdySession) this.sessionQueue.poll()) {
                    spdySession.closeInternal();
                }
            } catch (Throwable e0) {
                e0.printStackTrace();
            }
            SpdyAgent.w.unlock();
            long tagentNative = this.agentNativePtr;
            if (tagentNative != 0) {
                try {
                    this.freeAgent(tagentNative);
                } catch (UnsatisfiedLinkError e0) {
                    e0.printStackTrace();
                }
                this.agentNativePtr = 0;
            }
            w = SpdyAgent.w;
            try {
                w.lock();
                this.sessionMgr.clear();
                SpdyAgent.w.unlock();
                return;
            } catch (Throwable e0) {
                e0.printStackTrace();
                SpdyAgent.w.unlock();
                return;
            }
        } else {
            return;
        }
    }

    public int closeSession(long p0) {
        return this.closeSessionN(p0);
    }

    public int configLogFile(String p0, int p1, int p2) {
        if (SpdyAgent.loadTnetSoSucc) {
            return this.configLogFileN(p0, p1, p2);
        } else {
            return -1;
        }
    }

    public int configLogFile(String p0, int p1, int p2, int p3) {
        if (SpdyAgent.loadTnetSoSucc) {
            return this.configLogFileN(p0, p1, p2, p3);
        } else {
            return -1;
        }
    }

    public SpdySession createSession(String p0, Object p1, SessionCb p2, int p3) {
        return this.createSession(p0, "", p1, p2, null, p3, 0);
    }

    public SpdySession createSession(String p0, Object p1, SessionCb p2, SslCertcb p3, int p4) {
        return this.createSession(p0, "", p1, p2, null, p4, 0);
    }

    public SpdySession createSession(String p0, String p1, Object p2, SessionCb p3, int p4) {
        return this.createSession(p0, p1, p2, p3, null, p4, 0);
    }

    public SpdySession createSession(String p0, String p1, Object p2, SessionCb p3, SslCertcb p4, int p5, int p6) {
        return this.createSession(p0, p1, p2, p3, null, p5, p6, -1);
    }

    public SpdySession createSession(String p0, String p1, Object p2, SessionCb p3, SslCertcb p4, int p5, int p6, int p7) {
        return this.createSession(p0, p1, p2, p3, null, p5, p6, p7, null, 0, 0, -1, null, null, false, false, false, 0);
    }

    public SpdySession createSession(SessionInfo p0) {
        return this.createSession(p0.getAuthority(), p0.getDomain(), p0.getSessonUserData(), p0.getSessionCb(), p0.getSessionCustomExtraCb(), p0.getMode(), p0.getPubKeySeqNum(), p0.getConnectionTimeoutMs(), p0.getCertHost(), p0.getXquicCongControl(), p0.getRecvRateBps(), p0.getMss(), p0.getTunnelDomain(), p0.getTunnelStrategyList(), p0.getMultiPathCompensateEnable(), p0.getMultiPathParallelAddSpeedEnable(), p0.isTryForceCellular(), p0.getConnectIndex());

    }

    public void disableHeaderCache() {
        return;
    }

    public HashMap getAllSession() {
        return this.sessionMgr;
    }

    public void logFileClose() {
        if (SpdyAgent.loadTnetSoSucc) {
            this.logFileFlushN();
            this.logFileCloseN();
        }
        return;
    }

    public void logFileFlush() {
        if (SpdyAgent.loadTnetSoSucc) {
            this.logFileFlushN();
        }
        return;
    }

    public void removeSession(SpdySession p0) {
        SpdyAgent.w.lock();
        this.sessionQueue.remove(p0);
        SpdyAgent.w.unlock();
    }

    public void setAccsSslCallback(AccsSSLCallback p0) {
        this.accsSSLCallback = p0;
    }

    public void setProxyUsernamePassword(String p0, String p1) {
        this.proxyUsername = p0;
        this.proxyPassword = p1;
    }

    public SpdySession submitRequest(SpdyRequest p0, SpdyDataProvider p1, Object p2, Object p3, Spdycb p4, SessionCb p5, int p6) {
        return this.submitRequest(p0, p1, p2, p3, p4, p5, null, p6);
    }

    public SpdySession submitRequest(SpdyRequest p0, SpdyDataProvider p1, Object p2, Object p3, Spdycb p4, SessionCb p5, int p6, int p7) {
        return this.submitRequest(p0, p1, p2, p3, p4, p5, null, p6, p7);
    }

    public SpdySession submitRequest(SpdyRequest p0, SpdyDataProvider p1, Object p2, Object p3, Spdycb p4, SessionCb p5, SslCertcb p6, int p7) {
        SpdyRequest oobject = p0;
        SpdyDataProvider oobject1 = p1;
        Object oobject2 = p3;
        Spdycb oobject3 = p4;
        SpdySession spdySession = this.createSession(p0.getAuthority(), p0.getDomain(), p2, p5, p6, p7, 0, p0.getConnectionTimeoutMs());
        spdySession.submitRequest(oobject, oobject1, oobject2, oobject3);
        return spdySession;
    }

    public SpdySession submitRequest(SpdyRequest p0, SpdyDataProvider p1, Object p2, Object p3, Spdycb p4, SessionCb p5, SslCertcb p6, int p7, int p8) {
        SpdyRequest oobject = p0;
        SpdyDataProvider oobject1 = p1;
        Object oobject2 = p3;
        Spdycb oobject3 = p4;
        SpdySession spdySession = this.createSession(p0.getAuthority(), p0.getDomain(), p2, p5, p6, p7, p8, p0.getConnectionTimeoutMs());
        spdySession.submitRequest(oobject, oobject1, oobject2, oobject3);
        return spdySession;
    }

    public void switchAccsServer(int p0) {
        return;
    }


}
