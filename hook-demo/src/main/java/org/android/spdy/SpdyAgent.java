package org.android.spdy;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    private SpdyAgent(Context p0,AccsSSLCallback p1){
        int i2;
        this.isAgentClosed = new AtomicBoolean();
        this.proxyUsername = null;
        this.proxyPassword = null;
        this.sessionMgr = new HashMap(5);
        this.sessionQueue = new LinkedList();
        SecurityGuardCacherImp securityGuar = new SecurityGuardCacherImp();
        try{
            this.xqcCache = securityGuar;
            SpdyAgent.setContext(p0);
            SpdyAgent.loadTnetSoSucc = SoInstallMgrSdk.loadTnetSo();
            sac.a(SpdyAgent.context);
            SoInstallMgrSdk.fetchLocalSoAndPluginLoad();
        }catch(java.lang.Throwable e3){
            e3.printStackTrace();
        }
        int i = 0;
        try{
            int i1 = sad.I();
            if (sad.q()) {
                i2 = 1;
                label_0049 :
                this.agentNativePtr = this.initAgent(i, i1, i2);
                this.accsSSLCallback = p1;
                if (sad.t()) {
                    sae.b(new SpdyAgent$1(this));
                }
            }else {
                i2 = 0;
             goto label_0049 ;
            }
        }catch(java.lang.UnsatisfiedLinkError e4){
            e4.printStackTrace();
        }
        NetWorkStatusUtil.a(SpdyAgent.context);
        sab.a(SpdyAgent.context);
        this.isAgentClosed.set(i);
        return;
    }
    private int AndroidVerifyProof(String[] p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return QuicProofVerifier.VerifyProof(null, p0);
        }
        Object[] objArray = new Object[]{this,p0};
        return $ipChange.ipc$dispatch("67b1eb2f", objArray).intValue();
    }
    public static void InvlidCharJudge(byte[] p0,byte[] p1){
        int i2;
        int i3;
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0,p1};
            $ipChange.ipc$dispatch("f65686c0", objArray);
            return;
        }else {
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
            return;
        }
    }
    public static QuicCacher access$000(SpdyAgent p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return p0.xqcCache;
        }
        Object[] objArray = new Object[]{p0};
        return $ipChange.ipc$dispatch("8cd6c1f6", objArray);
    }
    public static Map access$100(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return SpdyAgent.mStorageMap;
        }
        Object[] objArray = new Object[0];
        return $ipChange.ipc$dispatch("d58732ed", objArray);
    }
    private void agentIsOpen(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("f569fa00", objArray);
            return;
        }else if(!this.isAgentClosed.get()){
            this.checkLoadSo();
            return;
        }else {
            throw new SpdyErrorException("SPDY_JNI_ERR_ASYNC_CLOSE", -1104);
        }
    }
    private int bindSocketFd2NetworkInterfaceN(int p0,int p1){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0),new Integer(p1)};
            return $ipChange.ipc$dispatch("2138c839", objArray).intValue();
        }else {
            int i = -1;
            if (p1 == NetWorkStatusUtil$InterfaceStatus.ACTIVE_INTERFACE_WIFI.getInterfaceStatus()) {
                i = NetWorkStatusUtil.b(p0);
            }else if(p1 == NetWorkStatusUtil$InterfaceStatus.ACTIVE_INTERFACE_CELLULAR.getInterfaceStatus()){
                i = NetWorkStatusUtil.a(p0);
            }
            return i;
        }
    }
    private void bioPingRecvCallback(SpdySession p0,int p1){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1)};
            $ipChange.ipc$dispatch("8b6dabda", objArray);
            return;
        }else {
            String str = "tnetsdk.SpdyAgent";
            spduLog.Logi(str, "[bioPingRecvCallback] - ");
            if (p0 != null && p0.intenalcb != null) {
                p0.intenalcb.bioPingRecvCallback(p0, p1);
                return;
            }else {
                spduLog.Logi(str, "[bioPingRecvCallback] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void checkLoadSo(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[0] = this;
            $ipChange.ipc$dispatch("3a7e0891", objArray);
            return;
        }else {
            int i1 = 0;
            if (!(SoInstallMgrSdk.fetchLocalSOStartTime - i1)) {
                SoInstallMgrSdk.fetchLocalSoAndPluginLoad();
            }
            if (!(SoInstallMgrSdk.fetchRemoteSOStartTime - i1)) {
                SoInstallMgrSdk.fetchRemoteSoAndPluginLoad();
            }
            if (SpdyAgent.loadTnetSoSucc) {
                return;
            }else {
                try{
                    Object loadSolock = SpdyAgent.loadSolock;
                    _monitor_enter(loadSolock);
                    if (SpdyAgent.loadTnetSoSucc) {
                        _monitor_exit(loadSolock);
                        return;
                    }else {
                        SpdyAgent.loadTnetSoSucc = SoInstallMgrSdk.loadTnetSo();
                        int i2 = sad.I();
                        if (!sad.q()) {
                            i = 0;
                        }
                        this.agentNativePtr = this.initAgent(0, i2, i);
                        _monitor_exit(loadSolock);
                    }
                }catch(java.lang.Throwable e0){
                    e0.printStackTrace();
                }
                if (SpdyAgent.loadTnetSoSucc) {
                    return;
                }else {
                    throw new SpdyErrorException("So load fail", -1108);
                }
            }
        }
    }
    public static boolean checkLoadSucc(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return SpdyAgent.loadTnetSoSucc;
        }
        Object[] objArray = new Object[0];
        return $ipChange.ipc$dispatch("2e356dfb", objArray).booleanValue();
    }
    private native int closeSessionN(long p0);
    private byte[] commonCacheLoad(String p0,int p1){
        byte[] uobyteArray1;
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 1;
        int i1 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1)};
            return $ipChange.ipc$dispatch("15118768", objArray);
        }else {
            byte[] uobyteArray = null;
            try{
                if ((uobyteArray1 = SpdyAgent.mStorageMap.get(p0)) != null) {
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
                    spduLog.Logd("tnet-jni", "xquic cache is expired");
                    sae.b(new SpdyAgent$3(this, p0));
                }
                return (str.substring((i2 + 3))).getBytes();
            }catch(java.lang.Throwable e12){
                e12.printStackTrace();
                return uobyteArray;
            }
        }
    }
    private void commonCacheRemove(String p0,int p1){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1)};
            $ipChange.ipc$dispatch("2cd0f077", objArray);
            return;
        }else {
            try{
                sae.b(new SpdyAgent$4(this, p0));
                return;
            }catch(java.lang.Throwable e0){
            }
        }
    }
    private boolean commonCacheStore(String p0,String p1,int p2){
        boolean b;
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,p1,new Integer(p2)};
            return $ipChange.ipc$dispatch("d2e988c2", objArray).booleanValue();
        }else if(!TextUtils.isEmpty(p0) && !TextUtils.isEmpty(p1)){
            try{
                SpdyAgent.mStorageMap.put(p0, p1.getBytes());
                String str = String.valueOf(System.currentTimeMillis());
                if (1 != p2) {
                    p1 = str+"@@@"+p1;
                }
                sae.b(new SpdyAgent$2(this, p0, p1));
            }catch(java.lang.Throwable e0){
                b = false;
            }
            return b;
        }else {
            return 0;
        }
    }
    public static int configIpStackMode(int p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{new Integer(p0)};
            return $ipChange.ipc$dispatch("9011d64d", objArray).intValue();
        }else if(SpdyAgent.loadTnetSoSucc){
            return SpdyAgent.configIpStackModeN(p0);
        }else {
            return -1;
        }
    }
    private static native int configIpStackModeN(int p0);
    private native int configLogFileN(String p0,int p1,int p2);
    private native int configLogFileN(String p0,int p1,int p2,int p3);
    public static void configSwitchValueByKey(long p0,int p1,double p2,String p3){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{new Long(p0),new Integer(p1),new Double(p2),p3};
            $ipChange.ipc$dispatch("6e175095", objArray);
            return;
        }else if(SpdyAgent.loadTnetSoSucc){
            SpdyAgent.configSwitchValueByKeyN(p0, p1, p2, p3);
        }
        return;
    }
    private static native int configSwitchValueByKeyN(long p0,int p1,double p2,String p3);
    private SpdySession createSession(String p0,String p1,Object p2,SessionCb p3,SessionCustomExtraCb p4,int p5,int p6,int p7,String p8,int p9,int p10,int p11,String p12,ArrayList p13,boolean p14,boolean p15,boolean p16,int p17){
        int b1;
        char c;
        byte[] uobyteArray;
        SpdySession spdySession;
        String str6;
        SpdyAgent spdyAgent;
        byte[] uobyteArray2;
        byte[] uobyteArray3;
        byte[] uobyteArray6;
        SpdySession spdySession3;
        object oobject = this;
        object oobject1 = p0;
        object oobject2 = p1;
        int i = p5;
        int i1 = p7;
        int b = p16;
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i2 = 19;
        int i3 = 4;
        int i4 = 1;
        boolean i5 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i2];
            objArray[i5] = oobject;
            objArray[i4] = oobject1;
            objArray[2] = oobject2;
            objArray[3] = p2;
            objArray[i3] = p3;
            objArray[5] = p4;
            objArray[6] = new Integer(i);
            objArray[7] = new Integer(p6);
            objArray[8] = new Integer(i1);
            objArray[9] = p8;
            objArray[10] = new Integer(p9);
            objArray[11] = new Integer(p10);
            objArray[12] = new Integer(p11);
            objArray[13] = p12;
            objArray[14] = p13;
            objArray[15] = new Boolean(p14);
            objArray[16] = new Boolean(p15);
            objArray[17] = new Boolean(b);
            objArray[18] = new Integer(p17);
            return $ipChange.ipc$dispatch("a61ed224", objArray);
        }else if(oobject1 != null){
            if ((i & 0x02)) {
                throw new SpdyErrorException("SPDY disable", -1107);
            }
            String hostFromDoma = SpdySession.getHostFromDomain(p1);
            boolean i6 = (!(b1 = sad.y(hostFromDoma)))? 0: p17;
            int i7 = (sad.u() && b)? 1: 0;
            String[] stringArray = oobject1.split("/");
            i3 = stringArray[i5].lastIndexOf(58);
            String str = stringArray[i5].substring(i5, i3);
            String str1 = stringArray[i5].substring((i3 + i4));
            byte[] bytes = "0.0.0.0".getBytes();
            if (stringArray.length != i4 && !((i & 0x0104))) {
                stringArray = stringArray[i4].split(":");
                c = (char)Integer.parseInt(stringArray[i4]);
                uobyteArray = stringArray[i5].getBytes();
            }else {
                oobject1 = oobject1+"/0.0.0.0:0";
                uobyteArray = bytes;
                c = 0;
            }
            String str2 = "_";
            String str3 = oobject1+oobject2+str2+i+str2+i7+str2+i6;
            if ((spdySession = oobject.getSpdySession(str3)) != null) {
                Object[] objArray1 = new Object[]{"ref",Integer.valueOf(spdySession.getRefCount()),"uniqueKey",str3};
                spduLog.Tloge("tnetsdk.SpdyAgent", spdySession.getSessionSeq(), "old session", objArray1);
                return spdySession;
            }else {
                SpdyAgent.w.lock();
                SpdySession SpdySession spdySession1 = spdySession;
                int i8 = i6;
                String str4 = str;
                spdySession1 = new SpdySession(0, this, oobject1, p1, hostFromDoma, p8, p3, p4, p5, p6, p2, p12, p13, p14, p15, i7);
                SpdySession spdySession2 = spdySession1;
                String str5 = str3;
                spdySession2.setSessionPoolUniqueKey(str5);
                i1 = spdySession2.getConnectFastTimeout(p7);
                byte[] tunnelInfoBy = spdySession2.getTunnelInfoBytes();
                b = p5;
                if (!((b & 0x0100)) && !((b & 0x04))) {
                    str6 = str4;
                    object oobject4 = null;
                }else {
                    str4 = sad.u(p8);
                    str6 = str4;
                }
                spdySession2.preProcessProtocol(tunnelInfoBy, str6);
                if (!((spdySession2.getUsedProtocolMode() & 0x0200))) {
                    i6 = p16;
                    i2 = 0;
                }else {
                    byte[] uobyteArray7 = tunnelInfoBy;
                    i6 = p16;
                }
                boolean b2 = spdySession2.isConnectTryForceCellular(i6);
                byte[] uobyteArray1 = (p8 == null)? null: p8.getBytes();
                int i9 = (p12 == null)? 0: p12.getBytes();
                if (b1 && i8) {
                    i6 = SpdyAgent.mSessionUniqueIndex + 1;
                    SpdyAgent.mSessionUniqueIndex = i6;
                    spdyAgent = this;
                    i3 = p1;
                }else {
                    spdyAgent = this;
                    i6 = spdyAgent.getDomainHashIndex(p1+b);
                }
                int i10 = i6;
                if (spdyAgent.proxyUsername != null && spdyAgent.proxyPassword != null) {
                    uobyteArray2 = spdyAgent.proxyUsername.getBytes();
                    uobyteArray3 = spdyAgent.proxyPassword.getBytes();
                }else {
                    uobyteArray2 = 0;
                    uobyteArray3 = uobyteArray2;
                }
                SpdyAgent agentNativeP = spdyAgent.agentNativePtr;
                byte[] bytes1 = str6.getBytes();
                char c1 = (char)Integer.parseInt(str1);
                b1 = spdySession2.getUsedProtocolMode();
                int sessionParam = spdySession2.getSessionParameter();
                byte[] uobyteArray4 = (str4 == null)? null: str4.getBytes();
                byte[] uobyteArray5 = i2;
                int i11 = i1;
                p2 = spdySession2;
                String str7 = str5;
                long l = this.createSessionN(agentNativeP, spdySession2, i10, bytes1, c1, uobyteArray, c, uobyteArray2, uobyteArray3, p2, b1, p6, i11, uobyteArray1, p9, sessionParam, p10, p11, b2, i9, uobyteArray5, uobyteArray4);
                str6 = "tnetsdk.SpdyAgent";
                str2 = p2.getSessionSeq();
                String str8 = "create new session";
                Object[] objArray2 = new Object[30];
                objArray2[0] = "authority";
                objArray2[1] = oobject1;
                objArray2[2] = "domain";
                objArray2[3] = p1;
                objArray2[4] = "certHost";
                objArray2[5] = p8;
                objArray2[6] = "protocol";
                objArray2[7] = Integer.valueOf(p5);
                objArray2[8] = "timeOut";
                objArray2[9] = Integer.valueOf(i11);
                objArray2[10] = "tunnel";
                object oobject3 = ((uobyteArray6 = uobyteArray5) == null)? null: new String(uobyteArray6);
                objArray2[11] = oobject3;
                objArray2[12] = "cellular";
                objArray2[13] = Boolean.valueOf(b2);
                objArray2[14] = "path";
                int i12 = 15;
                i5 = ((p2.getUsedProtocolMode() & 0x0800))? true: false;
                objArray2[i12] = Boolean.valueOf(i5);
                objArray2[16] = "timeoutChange";
                i12 = 17;
                i5 = (p7 != i11)? true: false;
                objArray2[i12] = Boolean.valueOf(i5);
                objArray2[18] = "wifiFailCnt";
                objArray2[19] = Integer.valueOf(SpdyAgent.wifiConsecutiveFailCount);
                objArray2[20] = "para";
                objArray2[21] = Integer.valueOf(p2.getSessionParameter());
                objArray2[22] = "option";
                objArray2[23] = str4;
                objArray2[24] = "ref";
                objArray2[25] = Integer.valueOf(p2.getRefCount());
                objArray2[26] = "index";
                objArray2[27] = Integer.valueOf(i10);
                objArray2[28] = "cIndex";
                objArray2[29] = Integer.valueOf(i8);
                spduLog.Tloge(str6, str2, str8, objArray2);
                if (!((1 & l) - 1)) {
                    i5 = (int)(l >> 1);
                    l = 0;
                }else {
                    i5 = 0;
                }
                if (l) {
                    spdySession3 = p2;
                    spdySession3.setSessionNativePtr(l);
                    SpdyAgent l1 = this;
                    l1.sessionMgr.put(str7, spdySession3);
                    l1.sessionQueue.add(spdySession3);
                }else if(!i5){
                    spdySession3 = null;
                }else {
                    throw new SpdyErrorException("create session error: "+i5, i5);
                }
                SpdyAgent.w.unlock();
                return spdySession3;
            }
        }else {
            throw new SpdyErrorException("invalid param, authority null,", -1102);
        }
    }
    private native long createSessionN(long p0,SpdySession p1,int p2,byte[] p3,char p4,byte[] p5,char p6,byte[] p7,byte[] p8,Object p9,int p10,int p11,int p12,byte[] p13,int p14,int p15,int p16,int p17,int p18,byte[] p19,byte[] p20,byte[] p21);
    public static byte[] dataproviderToByteArray(SpdyRequest p0,SpdyDataProvider p1){
        String str;
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0,p1};
            return $ipChange.ipc$dispatch("503d4861", objArray);
        }else {
            SpdyAgent.headJudge(p0.getHeaders());
            if (p1 == null) {
                return null;
            }
            byte[] bytes = ((str = SpdyAgent.mapBodyToString(p1.postBody)) != null)? str.getBytes(): p1.data;
            if (bytes != null && bytes.length >= 0x500000) {
                throw new SpdyErrorException("INVALID_PARAM:total="+bytes.length, -1102);
            }else {
                return bytes;
            }
        }
    }
    private native int freeAgent(long p0);
    private int getActiveInterfaceType(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return NetWorkStatusUtil.f.getInterfaceStatus();
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("48fbc471", objArray).intValue();
    }
    public static Context getContext(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[0];
            return $ipChange.ipc$dispatch("e1727078", objArray);
        }else if(SpdyAgent.context == null){
            SpdyAgent.context = UtilTool.b();
        }
        return SpdyAgent.context;
    }
    private int getDomainHashIndex(String p0){
        Integer integer;
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            return $ipChange.ipc$dispatch("812e90ce", objArray).intValue();
        }else {
            Object domainHashLo = SpdyAgent.domainHashLock;
            _monitor_enter(domainHashLo);
            if ((integer = SpdyAgent.domainHashMap.get(p0)) == null) {
                int i1 = SpdyAgent.mSessionUniqueIndex + i;
                SpdyAgent.mSessionUniqueIndex = i1;
                SpdyAgent.domainHashMap.put(p0, Integer.valueOf(i1));
                integer = Integer.valueOf(SpdyAgent.mSessionUniqueIndex);
            }
            _monitor_exit(domainHashLo);
            return integer.intValue();
        }
    }
    public static SpdyAgent getInstance(Context p0,SpdyVersion p1,SpdySessionKind p2){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0,p1,p2};
            return $ipChange.ipc$dispatch("3365208a", objArray);
        }else if(p0 != null && SpdyAgent.context == null){
            SpdyAgent.context = p0;
        }
        return SpdyAgent.getInstance(p0, p1, p2, null);
    }
    public static SpdyAgent getInstance(Context p0,SpdyVersion p1,SpdySessionKind p2,AccsSSLCallback p3){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0,p1,p2,p3};
            return $ipChange.ipc$dispatch("2e985968", objArray);
        }else if(SpdyAgent.gSingleInstance == null){
            p1 = SpdyAgent.loadSolock;
            _monitor_enter(p1);
            if (SpdyAgent.gSingleInstance == null) {
                SpdyAgent.gSingleInstance = new SpdyAgent(p0, p3);
            }
            _monitor_exit(p1);
        }
        return SpdyAgent.gSingleInstance;
    }
    private int getNetWorkStatus(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[0] = this;
            return $ipChange.ipc$dispatch("d9433a4", objArray).intValue();
        }else if(NetWorkStatusUtil.a()){
            i = 0;
        }
        if (NetWorkStatusUtil.b()) {
            i = i | 0x02;
        }
        return i;
    }
    private byte[] getSSLMeta(SpdySession p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            return $ipChange.ipc$dispatch("c9362ffe", objArray);
        }else if(p0 != null && p0.intenalcb != null){
            return p0.intenalcb.getSSLMeta(p0);
        }else {
            spduLog.Logi("tnetsdk.SpdyAgent", "[getSSLMeta] - session|session.intenalcb is null");
            return null;
        }
    }
    private byte[] getSSLPublicKey(int p0,byte[] p1){
        SpdyAgent taccsSSLCall;
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0),p1};
            return $ipChange.ipc$dispatch("13408d1a", objArray);
        }else if((taccsSSLCall = this.accsSSLCallback) == null){
            spduLog.Logd("tnetsdk.SpdyAgent", "[getSSLPublicKey] - accsSSLCallback is null.");
            return null;
        }else {
            return taccsSSLCall.getSSLPublicKey(p0, p1);
        }
    }
    private SpdySession getSpdySession(String p0){
        SpdySession spdySession1;
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            return $ipChange.ipc$dispatch("9ff52554", objArray);
        }else {
            this.agentIsOpen();
            SpdyAgent.r.lock();
            SpdySession spdySession = this.sessionMgr.get(p0);
            if (this.sessionMgr.size() < 150) {
                i = 0;
            }
            SpdyAgent.r.unlock();
            if (!i) {
                if (spdySession != null) {
                    spdySession.increRefCount();
                    return spdySession;
                }else {
                    SpdyAgent.w.lock();
                    try{
                        spdySession = 0;
                        spdySession1 = this.sessionMgr.get(p0);
                        SpdyAgent.w.unlock();
                    }catch(java.lang.Throwable e0){
                        SpdyAgent.w.unlock();
                        spdySession1 = e0;
                    }
                    if (spdySession1 != null) {
                        spdySession1.increRefCount();
                        return spdySession1;
                    }else {
                        return e0;
                    }
                }
            }else {
                throw new SpdyErrorException("SPDY_SESSION_EXCEED_MAXED", -1105);
            }
        }
    }
    public static void headJudge(Map p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            $ipChange.ipc$dispatch("1fcc9da7", objArray);
            return;
        }else if(p0 != null){
            Iterator iterator = p0.entrySet().iterator();
            while (iterator.hasNext()) {
                Map$Entry uEntry = iterator.next();
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
    private native long initAgent(int p0,int p1,int p2);
    public static boolean isQuicReady(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return SoInstallMgrSdk.loadQuicSucc;
        }
        Object[] objArray = new Object[0];
        return $ipChange.ipc$dispatch("ddbcf06", objArray).booleanValue();
    }
    private native void logFileCloseN();
    private native void logFileFlushN();
    private int logOutput(int p0,String p1,String p2,String p3){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0),p1,p2,p3};
            return $ipChange.ipc$dispatch("b3949ba8", objArray).intValue();
        }else if(!sad.a()){
            return -1;
        }else {
            try{
                SpdyAgent$5 $ipChange1 = new SpdyAgent$5(this, p0, p1, p2, p3);
                sae.a($ipChange);
                return i;
            }catch(java.lang.Throwable e0){
                return v1;
            }
        }
    }
    public static String mapBodyToString(Map p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            return $ipChange.ipc$dispatch("7fbd8cec", objArray);
        }else {
            StringBuilder str = "";
            if (p0 == null) {
                return null;
            }
            Iterator iterator = p0.entrySet().iterator();
            while (iterator.hasNext()) {
                Map$Entry uEntry = iterator.next();
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
            return str;
        }
    }
    public static String[] mapToByteArray(Map p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 0;
        int i1 = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i1];
            objArray[i] = p0;
            return $ipChange.ipc$dispatch("f2df51b3", objArray);
        }else if(p0 != null && p0.size() > 0){
            String[] stringArray = new String[(p0.size() << i1)];
            Iterator iterator = p0.entrySet().iterator();
            while (iterator.hasNext()) {
                Map$Entry uEntry = iterator.next();
                stringArray[i] = uEntry.getKey();
                i1 = i + 1;
                stringArray[i1] = uEntry.getValue();
                i = i + 2;
            }
            return stringArray;
        }else {
            return null;
        }
    }
    public static boolean pluginLoadQuicSo(String p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            return $ipChange.ipc$dispatch("6588d3a8", objArray).booleanValue();
        }else if(SpdyAgent.loadTnetSoSucc && !SpdyAgent.pluginLoadQuicSoN(p0)){
            return 1;
        }else {
            return 0;
        }
    }
    private static native int pluginLoadQuicSoN(String p0);
    public static boolean pluginLoadZstdSo(String p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            return $ipChange.ipc$dispatch("2c6d3d3", objArray).booleanValue();
        }else if(SpdyAgent.loadTnetSoSucc && !SpdyAgent.pluginLoadZstdSoN(p0)){
            return 1;
        }else {
            return 0;
        }
    }
    private static native int pluginLoadZstdSoN(String p0);
    private int putSSLMeta(SpdySession p0,byte[] p1){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,p1};
            return $ipChange.ipc$dispatch("8ce64c60", objArray).intValue();
        }else if(p0 != null && p0.intenalcb != null){
            return p0.intenalcb.putSSLMeta(p0, p1);
        }else {
            spduLog.Logi("tnetsdk.SpdyAgent", "[putSSLMeta] - session|session.intenalcb is null");
            return -1;
        }
    }
    public static void registerQuicListener(IPluginFetchCallback p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            $ipChange.ipc$dispatch("6577c030", objArray);
            return;
        }else {
            SoInstallMgrSdk.registerQuicListener(p0);
            return;
        }
    }
    public static void securityCheck(int p0,int p1){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{new Integer(p0),new Integer(p1)};
            $ipChange.ipc$dispatch("e64b06cf", objArray);
            return;
        }else if(p0 < 0x8000){
            if (p1 < 8192) {
                return;
            }
            throw new SpdyErrorException("INVALID_PARAM:value="+p1, -1102);
        }else {
            throw new SpdyErrorException("INVALID_PARAM:total1="+p0, -1102);
        }
    }
    public static void setContext(Context p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            $ipChange.ipc$dispatch("1a164154", objArray);
            return;
        }else if(p0 == null){
            p0 = UtilTool.b();
        }
        SpdyAgent.context = p0;
        return;
    }
    private void spdyCustomControlFrameFailCallback(SpdySession p0,Object p1,int p2,int p3){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,p1,new Integer(p2),new Integer(p3)};
            $ipChange.ipc$dispatch("7576ccd6", objArray);
            return;
        }else {
            String str = "tnetsdk.SpdyAgent";
            spduLog.Logi(str, "[spdyCustomControlFrameFailCallback] - ");
            if (p0 != null && p0.intenalcb != null) {
                p0.intenalcb.spdyCustomControlFrameFailCallback(p0, p1, p2, p3);
                return;
            }else {
                spduLog.Logi(str, "[spdyCustomControlFrameFailCallback] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdyCustomControlFrameRecvCallback(SpdySession p0,Object p1,int p2,int p3,int p4,int p5,byte[] p6,SuperviseData p7){
        object oobject = p0;
        object oobject1 = p7;
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[9];
            objArray[0] = this;
            objArray[1] = oobject;
            objArray[2] = p1;
            objArray[3] = new Integer(p2);
            objArray[4] = new Integer(p3);
            objArray[5] = new Integer(p4);
            objArray[6] = new Integer(p5);
            objArray[7] = p6;
            objArray[8] = oobject1;
            $ipChange.ipc$dispatch("9bd9c77c", objArray);
            return;
        }else {
            StringBuilder str = "tnet-jni";
            spduLog.Logi(str, "[spdyCustomControlFrameRecvCallback] - ");
            if (oobject != null && (oobject.intenalcb != null && oobject.customExtraCb == null)) {
                if (!p0.isForceUseCellular()) {
                    SpdyAgent.wifiConsecutiveFailCount = 0;
                }
                long l = System.currentTimeMillis();
                if (oobject.customExtraCb != null) {
                    oobject.customExtraCb.onCustomFrameRecvCallback(p0, p1, p2, p3, p4, p5, p6, p7, 0);
                }else {
                    oobject.intenalcb.spdyCustomControlFrameRecvCallback(p0, p1, p2, p3, p4, p5, p6);
                }
                if (((p0.getMode() & 0x0100)) && ((p0.getMode() & 0x10))) {
                    str = "";
                    if (oobject1 != null) {
                        str = str+"quicTime="+(oobject1.responseBodyStart - oobject1.responseStart)+",processTime="+(oobject1.responseEnd - oobject1.responseBodyStart)+",netDownTime="+oobject1.streamFinRecvTime+",peerSendTime="+oobject1.sendStart+",cbTime="+(System.currentTimeMillis() - l);
                        if (oobject1.unreliableChannelMss > oobject.unreliableChannelMss) {
                            oobject.unreliableChannelMss = oobject1.unreliableChannelMss;
                            str = str+",mss="+oobject.unreliableChannelMss;
                        }
                        str = str+",connInfo="+p7.getConnInfo();
                    }
                    Object[] objArray1 = new Object[]{"type",Integer.valueOf(p3),"len",Integer.valueOf(p5),"stat",str};
                    spduLog.Tloge("tnetsdk.SpdyAgent", p0.getSessionSeq(), "[accs2][frameRecv]", objArray1);
                }
                return;
            }else {
                spduLog.Logi(str, "[spdyCustomControlFrameRecvCallback] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdyDataChunkRecvCB(SpdySession p0,boolean p1,int p2,SpdyByteArray p3,ByteBuffer p4,int p5,int p6){
        object oobject = p0;
        int i = p2;
        object oobject1 = p3;
        int i1 = p5;
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i2 = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,oobject,new Boolean(p1),new Integer(i),oobject1,p4,new Integer(i1),new Integer(p6)};
            $ipChange.ipc$dispatch("ea6aa066", objArray);
            return;
        }else {
            String str = "tnetsdk.SpdyAgent";
            spduLog.Logi(str, "[spdyDataChunkRecvCB] - ");
            if (i1 == i2) {
                oobject1.setDirectBufferMode(i2);
                p3.setDirectByteBuffer(p4);
            }
            long l = (long)i & 0xffffffff;
            if (oobject != null && oobject.intenalcb != null) {
                oobject.intenalcb.spdyDataChunkRecvCB(p0, p1, l, p3, p6);
                return;
            }else {
                spduLog.Logi(str, "[spdyDataChunkRecvCB] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdyDataRecvCallback(SpdySession p0,boolean p1,int p2,int p3,int p4){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Boolean(p1),new Integer(p2),new Integer(p3),new Integer(p4)};
            $ipChange.ipc$dispatch("5cd9167a", objArray);
            return;
        }else {
            String str = "tnetsdk.SpdyAgent";
            spduLog.Logi(str, "[spdyDataRecvCallback] - ");
            long l = (long)p2 & 0xffffffff;
            if (p0 != null && p0.intenalcb != null) {
                p0.intenalcb.spdyDataRecvCallback(p0, p1, l, p3, p4);
                return;
            }else {
                spduLog.Logi(str, "[spdyDataRecvCallback] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdyDataSendCallback(SpdySession p0,boolean p1,int p2,int p3,int p4){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Boolean(p1),new Integer(p2),new Integer(p3),new Integer(p4)};
            $ipChange.ipc$dispatch("87a1009c", objArray);
            return;
        }else {
            long l = (long)p2 & 0xffffffff;
            if (p0 != null && p0.intenalcb != null) {
                p0.intenalcb.spdyDataSendCallback(p0, p1, l, p3, p4);
                return;
            }else {
                spduLog.Logi("tnetsdk.SpdyAgent", "[spdyDataSendCallback] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdyPingRecvCallback(SpdySession p0,int p1,Object p2){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1),p2};
            $ipChange.ipc$dispatch("e52c08cc", objArray);
            return;
        }else {
            String str = "tnetsdk.SpdyAgent";
            spduLog.Logi(str, "[spdyPingRecvCallback] - ");
            if (p0 != null && p0.intenalcb != null) {
                if (!p0.isForceUseCellular() && p1 > 0) {
                    SpdyAgent.wifiConsecutiveFailCount = i;
                }
                p0.intenalcb.spdyPingRecvCallback(p0, (long)p1, p2);
                return;
            }else {
                spduLog.Logi(str, "[spdyPingRecvCallback] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdyRequestRecvCallback(SpdySession p0,int p1,int p2){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1),new Integer(p2)};
            $ipChange.ipc$dispatch("ad5f084c", objArray);
            return;
        }else {
            long l = (long)p1 & 0xffffffff;
            if (p0 != null && p0.intenalcb != null) {
                p0.intenalcb.spdyRequestRecvCallback(p0, l, p2);
                return;
            }else {
                spduLog.Logi("tnetsdk.SpdyAgent", "[spdyRequestRecvCallback] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdySessionCloseCallback(SpdySession p0,Object p1,SuperviseConnectInfo p2,int p3){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 2;
        int i1 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,p1,p2,new Integer(p3)};
            $ipChange.ipc$dispatch("f76348e5", objArray);
            return;
        }else {
            spduLog.Logi("tnetsdk.SpdyAgent", "[spdySessionCloseCallback] - errorCode = ", (long)p3);
            if (p0 == null) {
                spduLog.Logi("tnetsdk.SpdyAgent", "[spdySessionCloseCallback] - session|session.intenalcb is null");
                return;
            }else {
                p0.setSuperviseConnectInfoOnDisconnectedCB(p2);
                p0.checkWifiConsecutiveFailStatus(p3);
                Object[] objArray1 = new Object[i];
                objArray1[i1] = "errcode";
                objArray1[1] = Integer.valueOf(p3);
                spduLog.Tloge("tnetsdk.SpdyAgent", p0.getSessionSeq(), "[SessionCloseCallback]", objArray1);
                if (p0.intenalcb != null) {
                    p0.intenalcb.spdySessionCloseCallback(p0, p1, p2, p3);
                }
                p0.cleanUp();
                p0.releasePptr();
                return;
            }
        }
    }
    private void spdySessionConnectCB(SpdySession p0,SuperviseConnectInfo p1){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 2;
        int i1 = 1;
        int i2 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,p1};
            $ipChange.ipc$dispatch("9d2bcf0e", objArray);
            return;
        }else {
            String str = "tnetsdk.SpdyAgent";
            spduLog.Logi(str, "[spdySessionConnectCB] - ");
            if (p0 != null && p0.intenalcb != null) {
                p0.setSuperviseConnectInfoOnConnectedCB(p1);
                Object[] objArray1 = new Object[i];
                objArray1[i2] = "stat";
                objArray1[i1] = p0.getConnectInfoOnConnected();
                spduLog.Tloge(str, p0.getSessionSeq(), "[SessionConnectCB]", objArray1);
                p0.intenalcb.spdySessionConnectCB(p0, p1);
                return;
            }else {
                spduLog.Logi(str, "[spdySessionConnectCB] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdySessionFailedError(SpdySession p0,int p1,Object p2,SuperviseConnectInfo p3){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 4;
        int i1 = 2;
        int i2 = 1;
        int i3 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1),p2,p3};
            $ipChange.ipc$dispatch("ed7421c3", objArray);
            return;
        }else {
            spduLog.Logi("tnetsdk.SpdyAgent", "[spdySessionFailedError] - ");
            if (p0 == null) {
                spduLog.Logi("tnetsdk.SpdyAgent", "[spdySessionFailedError] - session|session.intenalcb is null");
                return;
            }else {
                p0.setSuperviseConnectInfoOnConnectedCB(p3);
                p0.setSuperviseConnectInfoOnDisconnectedCB(p3);
                Object[] objArray1 = new Object[i];
                objArray1[i3] = "errcode";
                objArray1[i2] = Integer.valueOf(p1);
                objArray1[i1] = "stat";
                objArray1[3] = p0.getConnectInfoOnDisConnected();
                spduLog.Tloge("tnetsdk.SpdyAgent", p0.getSessionSeq(), "spdySessionFailedError", objArray1);
                p0.checkWifiConsecutiveFailStatus(p1);
                if (p0.intenalcb != null) {
                    p0.intenalcb.spdySessionFailedError(p0, p1, p2);
                }
                p0.cleanUp();
                p0.releasePptr();
                return;
            }
        }
    }
    private void spdySessionOnWritable(SpdySession p0,Object p1,int p2){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,p1,new Integer(p2)};
            $ipChange.ipc$dispatch("4f5c8a40", objArray);
            return;
        }else {
            spduLog.Logi("tnetsdk.SpdyAgent", "[spdySessionOnWritable] - ");
            if (p0 != null && p0.intenalcb != null) {
                try{
                    p0.intenalcb.spdySessionOnWritable(p0, p1, p2);
                    return;
                }catch(java.lang.Throwable e4){
                    spduLog.Loge($ipChange, "[spdySessionOnWritable] - exception:", e4);
                    return;
                }
            }else {
                spduLog.Logi("tnetsdk.SpdyAgent", "[spdySessionOnWritable] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdyStreamCloseCallback(SpdySession p0,int p1,int p2,int p3,SuperviseData p4){
        object oobject = p0;
        int i = p1;
        int i1 = p2;
        object oobject1 = p4;
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i2 = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,oobject,new Integer(p1),new Integer(i1),new Integer(p3),oobject1};
            $ipChange.ipc$dispatch("6ce3a38b", objArray);
            return;
        }else {
            String str = "tnetsdk.SpdyAgent";
            spduLog.Logi(str, "[spdyStreamCloseCallback] - ");
            long l = (long)i & 0xffffffff;
            if (oobject != null && oobject.intenalcb != null) {
                if (!p0.isForceUseCellular() && !i1) {
                    SpdyAgent.wifiConsecutiveFailCount = 0;
                }
                try{
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
                }catch(java.lang.Exception e0){
                }
            }else {
                spduLog.Logi(str, "[spdyStreamCloseCallback] - session|session.intenalcb is null");
                return;
            }
        }
    }
    private void spdyStreamResponseRecv(SpdySession p0,int p1,byte[] p2,int[] p3,int p4){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1),p2,p3,new Integer(p4)};
            $ipChange.ipc$dispatch("7a81d4a8", objArray);
            return;
        }else {
            String str = "tnetsdk.SpdyAgent";
            spduLog.Logi(str, "[spdyStreamResponseRecv] - ");
            if (p0 != null && p0.intenalcb != null) {
                String[] stringArray = new String[p3.length];
                c uoc = c.a();
                int i1 = 0;
                for (; i < p3.length; i = i + 2) {
                    stringArray[i] = uoc.a(ByteBuffer.wrap(p2, i1, p3[i]));
                    i1 = i1 + p3[i];
                    int i2 = i + 1;
                    stringArray[i2] = (p3[i2] > 32)? new String(p2, i1, p3[i2]): uoc.a(ByteBuffer.wrap(p2, i1, p3[i2]));
                    i1 = i1 + p3[i2];
                }
                p0.intenalcb.spdyOnStreamResponse(p0, ((long)p1 & 0xffffffff), SpdyAgent.stringArrayToMap(stringArray), p4);
                return;
            }else {
                spduLog.Logi(str, "[spdyStreamResponseRecv] - session|session.intenalcb is null");
                return;
            }
        }
    }
    public static Map stringArrayToMap(String[] p0){
        int i1;
        List list;
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            return $ipChange.ipc$dispatch("e6b9a414", objArray);
        }else if(p0 == null){
            return null;
        }else {
            HashMap hashMap = new HashMap(5);
            while (true) {
                if ((i1 = i + 2) > p0.length) {
                    return hashMap;
                }
                if (p0[i] != null) {
                    int i2 = i + 1;
                    if (p0[i2] != null) {
                        if ((list = hashMap.get(p0[i])) == null) {
                            list = new ArrayList(1);
                            hashMap.put(p0[i], list);
                        }
                        list.add(p0[i2]);
                        i = i1;
                    }
                }
                break ;
            }
            return null;
        }
    }
    public static void tableListJudge(int p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{new Integer(p0)};
            $ipChange.ipc$dispatch("e4ae0d51", objArray);
            return;
        }else if(p0 < 0x500000){
            return;
        }else {
            throw new SpdyErrorException("INVALID_PARAM:total2="+p0, -1102);
        }
    }
    public static void unregisterQuicListener(IPluginFetchCallback p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            $ipChange.ipc$dispatch("3ba94277", objArray);
            return;
        }else {
            SoInstallMgrSdk.unregisterQuicListener(p0);
            return;
        }
    }
    public void InitializeSecurityStuff(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("4747374b", objArray);
            return;
        }else {
            this.xqcCache.init(SpdyAgent.context);
            b.a().b();
            return;
        }
    }
    public native String ResolveHost(String p0,String p1,int p2);
    public void clearSpdySession(String p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            $ipChange.ipc$dispatch("9a1bd48", objArray);
            return;
        }else if(p0 == null){
            return;
        }else {
            Lock w = SpdyAgent.w;
            try{
                w.lock();
                this.sessionMgr.remove(p0);
                SpdyAgent.w.unlock();
                return;
            }catch(java.lang.Throwable e4){
                e4.printStackTrace();
                SpdyAgent.w.unlock();
                return;
            }
        }
    }
    public void close(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[0] = this;
            $ipChange.ipc$dispatch("e32ba67f", objArray);
            return;
        }else if(!sad.p()){
            return;
        }else if(!this.isAgentClosed.getAndSet(i)){
            Lock w = SpdyAgent.w;
            try{
                w.lock();
                for (SpdySession spdySession = this.sessionQueue.poll(); spdySession != null; spdySession = this.sessionQueue.poll()) {
                    spdySession.closeInternal();
                }
            }catch(java.lang.Throwable e0){
                e0.printStackTrace();
            }
            SpdyAgent.w.unlock();
            SpdyAgent tagentNative = this.agentNativePtr;
            if (tagentNative) {
                try{
                    this.freeAgent(tagentNative);
                }catch(java.lang.UnsatisfiedLinkError e0){
                    e0.printStackTrace();
                }
                this.agentNativePtr = 0;
            }
            w = SpdyAgent.w;
            try{
                w.lock();
                this.sessionMgr.clear();
                SpdyAgent.w.unlock();
                return;
            }catch(java.lang.Throwable e0){
                e0.printStackTrace();
                SpdyAgent.w.unlock();
                return;
            }
        }else {
            return;
        }
    }
    public int closeSession(long p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.closeSessionN(p0);
        }
        Object[] objArray = new Object[]{this,new Long(p0)};
        return $ipChange.ipc$dispatch("bfbf2c52", objArray).intValue();
    }
    public int configLogFile(String p0,int p1,int p2){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1),new Integer(p2)};
            return $ipChange.ipc$dispatch("ec49f1e2", objArray).intValue();
        }else if(SpdyAgent.loadTnetSoSucc){
            return this.configLogFileN(p0, p1, p2);
        }else {
            return -1;
        }
    }
    public int configLogFile(String p0,int p1,int p2,int p3){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,new Integer(p1),new Integer(p2),new Integer(p3)};
            return $ipChange.ipc$dispatch("9cf4bee7", objArray).intValue();
        }else if(SpdyAgent.loadTnetSoSucc){
            return this.configLogFileN(p0, p1, p2, p3);
        }else {
            return -1;
        }
    }
    public SpdySession createSession(String p0,Object p1,SessionCb p2,int p3){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.createSession(p0, "", p1, p2, null, p3, 0);
        }
        Object[] objArray = new Object[]{this,p0,p1,p2,new Integer(p3)};
        return $ipChange.ipc$dispatch("770cab63", objArray);
    }
    public SpdySession createSession(String p0,Object p1,SessionCb p2,SslCertcb p3,int p4){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.createSession(p0, "", p1, p2, null, p4, 0);
        }
        Object[] objArray = new Object[]{this,p0,p1,p2,p3,new Integer(p4)};
        return $ipChange.ipc$dispatch("d1e90ad5", objArray);
    }
    public SpdySession createSession(String p0,String p1,Object p2,SessionCb p3,int p4){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.createSession(p0, p1, p2, p3, null, p4, 0);
        }
        Object[] objArray = new Object[]{this,p0,p1,p2,p3,new Integer(p4)};
        return $ipChange.ipc$dispatch("cec9396d", objArray);
    }
    public SpdySession createSession(String p0,String p1,Object p2,SessionCb p3,SslCertcb p4,int p5,int p6){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.createSession(p0, p1, p2, p3, null, p5, p6, -1);
        }
        Object[] objArray = new Object[]{this,p0,p1,p2,p3,p4,new Integer(p5),new Integer(p6)};
        return $ipChange.ipc$dispatch("4f35824", objArray);
    }
    public SpdySession createSession(String p0,String p1,Object p2,SessionCb p3,SslCertcb p4,int p5,int p6,int p7){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.createSession(p0, p1, p2, p3, null, p5, p6, p7, null, 0, 0, -1, null, null, false, false, false, 0);
        }
        Object[] objArray = new Object[9];
        objArray[0] = this;
        objArray[1] = p0;
        objArray[2] = p1;
        objArray[3] = p2;
        objArray[4] = p3;
        objArray[5] = p4;
        objArray[6] = new Integer(p5);
        objArray[7] = new Integer(p6);
        objArray[8] = new Integer(p7);
        return $ipChange.ipc$dispatch("537dce7f", objArray);
    }
    public SpdySession createSession(SessionInfo p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.createSession(p0.getAuthority(), p0.getDomain(), p0.getSessonUserData(), p0.getSessionCb(), p0.getSessionCustomExtraCb(), p0.getMode(), p0.getPubKeySeqNum(), p0.getConnectionTimeoutMs(), p0.getCertHost(), p0.getXquicCongControl(), p0.getRecvRateBps(), p0.getMss(), p0.getTunnelDomain(), p0.getTunnelStrategyList(), p0.getMultiPathCompensateEnable(), p0.getMultiPathParallelAddSpeedEnable(), p0.isTryForceCellular(), p0.getConnectIndex());
        }
        Object[] objArray = new Object[]{this,p0};
        return $ipChange.ipc$dispatch("b29f7bf5", objArray);
    }
    public void disableHeaderCache(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("98dad6f4", objArray);
        }
        return;
    }
    public HashMap getAllSession(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.sessionMgr;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("2eb35595", objArray);
    }
    public void logFileClose(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("ebac377f", objArray);
            return;
        }else if(SpdyAgent.loadTnetSoSucc){
            this.logFileFlushN();
            this.logFileCloseN();
        }
        return;
    }
    public void logFileFlush(){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("e6092e8b", objArray);
            return;
        }else if(SpdyAgent.loadTnetSoSucc){
            this.logFileFlushN();
        }
        return;
    }
    public void removeSession(SpdySession p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            $ipChange.ipc$dispatch("545ff676", objArray);
            return;
        }else {
            SpdyAgent.w.lock();
            this.sessionQueue.remove(p0);
            SpdyAgent.w.unlock();
            return;
        }
    }
    public void setAccsSslCallback(AccsSSLCallback p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            $ipChange.ipc$dispatch("8dae67a6", objArray);
            return;
        }else {
            spduLog.Logi("tnetsdk.SpdyAgent", "[setAccsSslCallback] - ", p0.getClass());
            this.accsSSLCallback = p0;
            return;
        }
    }
    public void setProxyUsernamePassword(String p0,String p1){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,p1};
            $ipChange.ipc$dispatch("5e0ac9f8", objArray);
            return;
        }else {
            this.proxyUsername = p0;
            this.proxyPassword = p1;
            return;
        }
    }
    public SpdySession submitRequest(SpdyRequest p0,SpdyDataProvider p1,Object p2,Object p3,Spdycb p4,SessionCb p5,int p6){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.submitRequest(p0, p1, p2, p3, p4, p5, null, p6);
        }
        Object[] objArray = new Object[]{this,p0,p1,p2,p3,p4,p5,new Integer(p6)};
        return $ipChange.ipc$dispatch("aebf77c2", objArray);
    }
    public SpdySession submitRequest(SpdyRequest p0,SpdyDataProvider p1,Object p2,Object p3,Spdycb p4,SessionCb p5,int p6,int p7){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.submitRequest(p0, p1, p2, p3, p4, p5, null, p6, p7);
        }
        Object[] objArray = new Object[9];
        objArray[0] = this;
        objArray[1] = p0;
        objArray[2] = p1;
        objArray[3] = p2;
        objArray[4] = p3;
        objArray[5] = p4;
        objArray[6] = p5;
        objArray[7] = new Integer(p6);
        objArray[8] = new Integer(p7);
        return $ipChange.ipc$dispatch("e335a2a1", objArray);
    }
    public SpdySession submitRequest(SpdyRequest p0,SpdyDataProvider p1,Object p2,Object p3,Spdycb p4,SessionCb p5,SslCertcb p6,int p7){
        object oobject = p0;
        object oobject1 = p1;
        object oobject2 = p3;
        object oobject3 = p4;
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[9];
            objArray[0] = this;
            objArray[1] = oobject;
            objArray[2] = oobject1;
            objArray[3] = p2;
            objArray[4] = oobject2;
            objArray[5] = oobject3;
            objArray[6] = p5;
            objArray[7] = p6;
            objArray[8] = new Integer(p7);
            return $ipChange.ipc$dispatch("6957a2b4", objArray);
        }else {
            SpdySession spdySession = this.createSession(p0.getAuthority(), p0.getDomain(), p2, p5, p6, p7, 0, p0.getConnectionTimeoutMs());
            spdySession.submitRequest(oobject, oobject1, oobject2, oobject3);
            return spdySession;
        }
    }
    public SpdySession submitRequest(SpdyRequest p0,SpdyDataProvider p1,Object p2,Object p3,Spdycb p4,SessionCb p5,SslCertcb p6,int p7,int p8){
        object oobject = p0;
        object oobject1 = p1;
        object oobject2 = p3;
        object oobject3 = p4;
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[10];
            objArray[0] = this;
            objArray[1] = oobject;
            objArray[2] = oobject1;
            objArray[3] = p2;
            objArray[4] = oobject2;
            objArray[5] = oobject3;
            objArray[6] = p5;
            objArray[7] = p6;
            objArray[8] = new Integer(p7);
            objArray[9] = new Integer(p8);
            return $ipChange.ipc$dispatch("7ba2d5ef", objArray);
        }else {
            SpdySession spdySession = this.createSession(p0.getAuthority(), p0.getDomain(), p2, p5, p6, p7, p8, p0.getConnectionTimeoutMs());
            spdySession.submitRequest(oobject, oobject1, oobject2, oobject3);
            return spdySession;
        }
    }
    public void switchAccsServer(int p0){
        IpChange $ipChange = SpdyAgent.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0)};
            $ipChange.ipc$dispatch("a53144b3", objArray);
        }
        return;
    }


}
