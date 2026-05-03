package org.android.spdy;

import android.os.Build;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import tb.sab;
import tb.sab$a;
import tb.sad;

public class SpdySession {
    private sab$a LifecycleListener;
    private NetWorkStatusUtil$a NetworkStatusChangeListener;
    private  SpdyAgent agent;
    private  String authority;
    private  String certHost;
    private  AtomicBoolean closed;
    public long connectTaskStartTime;
    public SessionCustomExtraCb customExtraCb;
    private  String domain;
    private JSONObject extra;
    public Intenalcb intenalcb;
    public AtomicBoolean isAddAppLifecycleListen;
    public AtomicBoolean isAddInterfaceListen;
    private boolean isMultiPathCompensateEnable;
    private boolean isMultiPathParallelAddSpeedEnable;
    private boolean isTunnelProxyClose;
    private Object lock;
    public String mHost;
    public SuperviseConnectInfo mSuperviseConnectInfo;
    private int mUsedProtocolMode;
    private int mode;
    public int options;
    public d pptr4sessionNativePtr;
    private int pubkey_seqnum;
    public int refcount;
    private String seq;
    public SessionCb sessionCallBack;
    private  AtomicBoolean sessionClearedFromSessionMgr;
    private long sessionNativePtr;
    private int sessionParameter;
    private String sessionPoolUniqueKey;
    private NetSparseArray spdyStream;
    private int streamcount;
    private String tunnelHost;
    private ArrayList tunnelInfoArrayList;
    public int unreliableChannelMss;
    private Object userData;
    private static  String APPKEY_SPLIT;
    public static  int CUSTOM_FRAME_QUIC_DGRAM_UNRELIABLE_CHANNEL_TYPE;
    public static  int CUSTOM_FRAME_QUIC_RELIABLE_CHANNEL_TYPE;
    private static  int DEFAULE_UNRELIABLECHANNEL_MSS;
    public static  int LIFECYCLE_CHANGED;
    public static  int NETWORK_CHANGED;
    private static  int P_CONNECT_QUIC_0RTT_FAST_TIMEOUT;
    private static  int P_CONNECT_RECV_BUFFER_RESIZE_ENABLE;
    private static  int P_MP_MIN_RTT;
    private static  int P_REQUEST_LIMIT_SPEED_ENABLE;
    private static  String SCHEME_SPLIT;
    private static  String TAG;

    public SpdySession(long p0,SpdyAgent p1,String p2,String p3,String p4,String p5,SessionCb p6,SessionCustomExtraCb p7,int p8,int p9,Object p10,String p11,ArrayList p12,boolean p13,boolean p14,int p15){
        SpdySession spdySession = this;
        int i = p8;
        spdySession.closed = new AtomicBoolean();
        spdySession.sessionClearedFromSessionMgr = new AtomicBoolean(false);
        spdySession.isTunnelProxyClose = false;
        spdySession.isAddInterfaceListen = new AtomicBoolean(false);
        spdySession.isAddAppLifecycleListen = new AtomicBoolean(false);
        spdySession.mHost = null;
        spdySession.isMultiPathCompensateEnable = false;
        spdySession.isMultiPathParallelAddSpeedEnable = false;
        spdySession.connectTaskStartTime = 0;
        spdySession.unreliableChannelMss = -1;
        spdySession.options = 0;
        spdySession.sessionParameter = 0;
        spdySession.extra = null;
        spdySession.customExtraCb = null;
        spdySession.sessionCallBack = null;
        spdySession.pubkey_seqnum = 0;
        spdySession.userData = 0;
        spdySession.mode = 0;
        spdySession.tunnelInfoArrayList = null;
        spdySession.lock = new Object();
        spdySession.spdyStream = null;
        spdySession.streamcount = 1;
        spdySession.refcount = 1;
        spdySession.mSuperviseConnectInfo = new SuperviseConnectInfo();
        spdySession.NetworkStatusChangeListener = new SpdySession$2(this);
        spdySession.LifecycleListener = new SpdySession$3(this);
        spdySession.sessionNativePtr = p0;
        spdySession.pptr4sessionNativePtr = new d(new SpdySession$1(this));
        spdySession.pptr4sessionNativePtr.a(new SpdySession$1(this));
        spdySession.spdyStream = new NetSparseArray(5);
        spdySession.intenalcb = new e();
        spdySession.agent = p1;
        spdySession.authority = p2;
        spdySession.domain = p3;
        spdySession.certHost = p5;
        spdySession.sessionCallBack = p6;
        spdySession.customExtraCb = p7;
        spdySession.mode = i;
        spdySession.mUsedProtocolMode = i;
        spdySession.pubkey_seqnum = p9;
        spdySession.userData = p10;
        spdySession.tunnelHost = p11;
        spdySession.tunnelInfoArrayList = p12;
        spdySession.mHost = p4;
        spdySession.isMultiPathParallelAddSpeedEnable = p14;
        spdySession.isMultiPathCompensateEnable = p13;
        spdySession.options = p15;
        spdySession.connectTaskStartTime = System.currentTimeMillis();
        spdySession.closed.set(false);
    }
    private native int NotifyNotInvokeAnyMoreN(long p0);
    public static long access$000(SpdySession p0){
        return p0.sessionNativePtr;
    }
    public static int access$100(SpdySession p0,long p1){
        return p0.NotifyNotInvokeAnyMoreN(p1);
    }
    private int closeprivate(){
        SpdyStreamContext[] allStreamCb;
        int i = 1;
        synchronized (this.lock) {
            if (!this.sessionClearedFromSessionMgr.getAndSet(true)) {
                this.agent.clearSpdySession(this.sessionPoolUniqueKey);
            }
            this.unRegisterNetworkStatusChangeListener();
            this.unRegisterAppLifecycleListener();
            if ((allStreamCb = this.getAllStreamCb()) != null) {
                int len = allStreamCb.length;
                for (int i1 = 0; i1 < len; i1 = i1 + 1) {
                    SpdyStreamContext oobject = allStreamCb[i1];
                    oobject.callBack.spdyStreamCloseCallback(this, (long)oobject.streamId, -2001, oobject.streamContext, null);
                }
            }
            this.spdyStream.clear();
        }
        return 0;
    }
    private String getAuthority(){
        return this.authority;
    }
    public static String getHostFromDomain(String p0){
        int i;
        if((i = p0.indexOf("_")) > 0){
            p0 = p0.substring((p0.indexOf("://") + 3), i);
        }
        return p0;
    }
    private StrategyInfo getStrategyInfoBySeq(int p0){
        ArrayList ttunnelInfoA;
        int i = 0;
        if((ttunnelInfoA = this.tunnelInfoArrayList) != null && ttunnelInfoA != Collections.EMPTY_LIST){
            while (i < this.tunnelInfoArrayList.size()) {
                StrategyInfo strategyInfo = (StrategyInfo) this.tunnelInfoArrayList.get(i);
                if (strategyInfo.getTunnelStrategySeq() == p0) {
                    return strategyInfo;
                }
                i = i + 1;
            }
        }
        return null;
    }
    private void putExtra(String p0,Object p1){
        if(this.extra == null){
            this.extra = new JSONObject();
        }
        try {
            this.extra.put(p0, p1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return;
    }
    private native int sendCustomControlFrameN(long p0,int p1,int p2,int p3,int p4,int p5,int p6,byte[] p7);
    private native int sendHeadersN(long p0,int p1,String[] p2,boolean p3);
    private void sessionIsOpen(){
        if(!this.closed.get()){
            return;
        }else {
            throw new SpdyErrorException("session is already closed: -1104", -1104);
        }
    }
    private native int setOptionN(long p0,int p1,int p2);
    private native int streamCloseN(long p0,int p1,int p2);
    private native int streamSendDataN(long p0,int p1,byte[] p2,int p3,int p4,boolean p5);
    private native int submitBioPingN(long p0);
    private native int submitPingN(long p0);
    public native int submitRequestN(long p0,String p1,byte p2,String[] p3,byte[] p4,boolean p5,int p6,int p7,int p8,int p9,int p10);
    public void checkWifiConsecutiveFailStatus(int p0){
        if(NetWorkStatusUtil.a()){
            if (p0 != -2003 && (p0 != -5004 && (!this.isTunnel() && p0 == -4993))) {
                if (p0 == -2002) {
                    int i = 0;
                    if ((this.mSuperviseConnectInfo.connRecvSize - i) == 0 || ((this.connectTaskStartTime - i) <= 0 || ((System.currentTimeMillis() - this.connectTaskStartTime) - 3000) <= 0)) {
                        return;
                    }
                }else {
                    return;
                }
            }
            SpdyAgent.wifiConsecutiveFailCount = SpdyAgent.wifiConsecutiveFailCount + 1;
            return;
        }else {
            return;
        }
    }
    public int cleanUp(){
        int i = 1;
        int i1 = 0;
        if (!this.closed.getAndSet(true)) {
            this.agent.removeSession(this);
            i1 = this.closeprivate();
        }
        return i1;
    }
    public void clearAllStreamCb(){
        synchronized (this.lock) {
            this.spdyStream.clear();
        }
    }
    public int closeInternal(){
        int i = 1;
        int i1 = 0;
        if(!this.closed.getAndSet(true)){
            i1 = this.closeprivate();
        }
        return i1;
    }
    public int closeSession(){
        d tpptr4sessio;
        int i = 1;
        int i1 = 0;
        this.unRegisterNetworkStatusChangeListener();
        this.unRegisterAppLifecycleListener();
        synchronized (this.lock) {
            if (!this.sessionClearedFromSessionMgr.getAndSet(true)) {
                this.agent.clearSpdySession(this.sessionPoolUniqueKey);
                if (this.pptr4sessionNativePtr.a()) {
                    try{
                        i1 = this.agent.closeSession(this.sessionNativePtr);
                        tpptr4sessio = this.pptr4sessionNativePtr;
                    }catch(UnsatisfiedLinkError e1){
                        e1.printStackTrace();
                        tpptr4sessio = this.pptr4sessionNativePtr;
                    }
                    tpptr4sessio.b();
                }else {
                    i1 = -2001;
                }
            }
        }
        return i1;
    }
    public SpdyStreamContext[] getAllStreamCb(){
        int i;
        SpdyStreamContext[] spdyStreamCo = null;
        synchronized (this.lock) {
            if ((i = this.spdyStream.size()) > 0) {
                spdyStreamCo = new SpdyStreamContext[i];
                this.spdyStream.toArray(spdyStreamCo);
            }
        }
        return spdyStreamCo;
    }
    public int getConnectFastTimeout(int p0){
        long l;
        int i2 = 0;
        int i = 1;
        int i1 = 0;
        if(sad.w() && sad.g(this.mHost)){
            if (this.isQUIC()) {
                if (p0 <= 0 || (sad.f() - (long)p0) < 0) {
                    l = sad.f();
                    p0 = i2;
                }
            }else if(p0 > 0 && (sad.g() - (long)p0) >= 0){
                l = sad.g();
            }
            i2 = p0;
        }
        return p0;
    }
    public String getConnectInfoOnConnected(){
        String str = "mode="+this.mode+",connectTime="+this.mSuperviseConnectInfo.connectTime+",handshakeTime="+this.mSuperviseConnectInfo.handshakeTime+",doHandshakeTime="+this.mSuperviseConnectInfo.doHandshakeTime+",isForceCellular="+this.mSuperviseConnectInfo.isForceCellular;
        if (this.isQUIC()) {
            str = str+",scid="+this.mSuperviseConnectInfo.scid+",dcid="+this.mSuperviseConnectInfo.dcid+",ccType="+this.mSuperviseConnectInfo.congControlKind+",try0RTT="+this.mSuperviseConnectInfo.isQuicTry0RTT;
            if (this.isTunnel()) {
                str = str+",isTlClose="+this.isTunnelProxyClose+",tlType="+this.mSuperviseConnectInfo.tunnelType+",tlScid="+this.mSuperviseConnectInfo.tunnelScid+",tlDcid="+this.mSuperviseConnectInfo.tunnelDcid+",tlConnectTime="+this.mSuperviseConnectInfo.tunnelConnectTime;
                if (this.mSuperviseConnectInfo.tunnelInfo != null) {
                    str = str+",tlIp="+this.mSuperviseConnectInfo.tunnelInfo.getTunnelStrategyHost()+",tlPort="+this.mSuperviseConnectInfo.tunnelInfo.getTunnelStrategyPort()+",tlStrategyStatus="+this.mSuperviseConnectInfo.currStrategyStatus;
                }
            }
        }
        return str;
    }
    public String getConnectInfoOnDisConnected(){
        String str = this.getConnectInfoOnConnected()+",reusedCnt="+this.mSuperviseConnectInfo.reused_counter+",keepAlive="+this.mSuperviseConnectInfo.keepalive_period_second+",srtt="+this.mSuperviseConnectInfo.srtt+",sendSz="+this.mSuperviseConnectInfo.connSendSize+",recvSz="+this.mSuperviseConnectInfo.connRecvSize+",recvMax="+this.mSuperviseConnectInfo.recvPacketMax+",sendPktCnt="+this.mSuperviseConnectInfo.sendPacketCount+",recvPktCnt="+this.mSuperviseConnectInfo.recvPacketCount+",connRdIdleTime="+this.mSuperviseConnectInfo.connLastRdEventIdleTime+",datagramMss="+this.unreliableChannelMss;
        if (this.isQUIC()) {
            str = str+",retransRate="+this.mSuperviseConnectInfo.retransmissionRate+",tlpCnt="+this.mSuperviseConnectInfo.tlpCount+",rtoCnt="+this.mSuperviseConnectInfo.rtoCount+",sendCnt="+this.mSuperviseConnectInfo.sendCount+",recvCnt="+this.mSuperviseConnectInfo.recvCount+",lossRate="+this.mSuperviseConnectInfo.lossRate+",0RttStatus="+this.mSuperviseConnectInfo.xqc0RttStatus+",multiNet="+this.mSuperviseConnectInfo.multiNetStatus+",mpStatus="+this.mSuperviseConnectInfo.mpquicStatus+",sendWeight="+this.mSuperviseConnectInfo.defaultPathSendWeight+",recvWeight="+this.mSuperviseConnectInfo.defaultPathRecvWeight+",mpPathInfo="+this.mSuperviseConnectInfo.mpquicPathInfo;
            if (this.isTunnel()) {
                str = str+",tl0RTTStatus="+this.mSuperviseConnectInfo.tunnel0RTTStatus+",tlRetryTimes="+this.mSuperviseConnectInfo.tunnelRetryTimes+",tlDegraded="+this.mSuperviseConnectInfo.tunnelDegraded+",tlErrorCode="+this.mSuperviseConnectInfo.tunnelErrorCode;
            }
        }
        return str+","+this.getExternStat();
    }
    public String getDomain(){
        return this.domain;
    }
    public String getExternStat(){
        JSONObject textra;
        this.putExtra("qcStat", Integer.valueOf(SoInstallMgrSdk.loadQuicStat));
        this.putExtra("net", Integer.valueOf(NetWorkStatusUtil.g()));
        this.putExtra("mpNetEnv", NetWorkStatusUtil.d());
        if ((textra = this.extra) != null) {
            return textra.toString();
        }
        return "null";
    }
    public int getMode(){
        return this.mode;
    }
    public int getRefCount(){
        return this.refcount;
    }
    public int getSessionParameter(){
        return this.sessionParameter;
    }
    public String getSessionPoolUniqueKey(){
        return this.sessionPoolUniqueKey;
    }
    public String getSessionSeq(){
        if(TextUtils.isEmpty(this.seq)){
            this.seq = Integer.toHexString(this.hashCode());
        }
        return this.seq;
    }
    public SpdyStreamContext getSpdyStream(int p0){
        SpdyStreamContext spdyStreamCo = null;
        if (p0 > 0) {
            synchronized (this.lock) {
                spdyStreamCo = (SpdyStreamContext) this.spdyStream.get(p0);
            }
        }
        return spdyStreamCo;
    }
    public byte[] getTunnelInfoBytes(){
        ArrayList ttunnelInfoA;
        int i = 0;
        if((ttunnelInfoA = this.tunnelInfoArrayList) != null && (ttunnelInfoA != Collections.EMPTY_LIST && this.tunnelInfoArrayList.size() > 0)){
            StringBuilder str = new StringBuilder();
            for (; i < this.tunnelInfoArrayList.size(); i = i + 1) {
                str = str.append(((StrategyInfo)this.tunnelInfoArrayList.get(i)).infoToString()).append("/");
            }
            return str.toString().getBytes();
        }else {
            return null;
        }
    }
    public int getUsedProtocolMode(){
        return this.mUsedProtocolMode;
    }
    public Object getUserData(){
        return this.userData;
    }
    public void increRefCount(){
        int i = 1;
        this.refcount = this.refcount + i;
    }
    public boolean isConnectTryForceCellular(boolean p0){
        int i = 2;
        if(sad.c() && (!this.isMultiPathCompensateEnable && (sad.e(this.mHost) && (NetWorkStatusUtil.a() && (NetWorkStatusUtil.a != null && (!sab.b() && sad.d())))))){
            int $ipChange = (sad.x() && SpdyAgent.wifiConsecutiveFailCount >= i)? 1: 0;
            if (!p0 && $ipChange != 0) {
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }
    public boolean isForceUseCellular(){
        int i = 0;
        if(this.mSuperviseConnectInfo.isForceCellular == 1){
            return true;
        }else {
            return false;
        }
    }
    public boolean isHTTP3(){
        int i = 0;
        if((this.mode & 0x0100) != 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean isMultiPathMode(){
        int i = 0;
        if(((this.mode & 0x0800)) == 0 && ((this.mUsedProtocolMode & 0x0800)) == 0){
            return false;
        }else {
            return true;
        }
    }
    public boolean isQUIC(){
        int i = 0;
        if(!this.isHTTP3() && ((this.mode & 0x04)) == 0){
            return false;
        }else {
            return true;
        }
    }
    public boolean isQuicTry0RTT(){
        int i = 0;
        if(this.mSuperviseConnectInfo.isQuicTry0RTT == 1){
            return true;
        }else {
            return false;
        }
    }
    public boolean isTunnel(){
        int i = 0;
        if((this.mode & 0x0200) != 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean isTunnelProxyClose(){
        return this.isTunnelProxyClose;
    }
    public void notifyLifecycleEvent(boolean p0){
        int i = 1;
        int i1 = 0;
        if(!sad.d()){
            return;
        }else if(this.isQUIC() && (this.isMultiPathMode() && NetWorkStatusUtil.f())){
            if (!p0) {
                i = 0;
            }
            this.setOption(5, i);
        }
        return;
    }
    public void preProcessProtocol(byte[] p0,String p1){
        Object[] objArray2;
        SuperviseConnectInfo mSuperviseCo;
        SpdySession oobject = this;
        String oobject1 = p1;
        int i = 3;
        int i1 = 0;
        int mode = oobject.mode;
        if ((mode & 0x0100) != 0 || (mode & 4) != 0) {
            String str = "tnetsdk.SpdySession";
            if (sad.r() && SoInstallMgrSdk.loadQuicSucc) {
                if (!"meizu".equalsIgnoreCase(Build.BRAND) && (!"xiaomi".equalsIgnoreCase(Build.BRAND) && (!"cmcc".equalsIgnoreCase(Build.BRAND) || (Build.VERSION.SDK_INT != 22 && (!"samsung".equalsIgnoreCase(Build.BRAND) && ("sm-a7160".equalsIgnoreCase(Build.MODEL) && Build.VERSION.SDK_INT == 31)))))) {
                    mode = oobject.mode;
                    if (((mode & 0x04) != 0) && ((mode & 0x10) == 0)) {
                        throw new SpdyErrorException("QUIC should with custom", -1110);
                    }else if(oobject.certHost != null){
                        String str1 = "";
                        boolean b = (!oobject.isMultiPathCompensateEnable && !oobject.isMultiPathParallelAddSpeedEnable)? false: true;
                        boolean b1 = NetWorkStatusUtil.c();
                        boolean b2 = oobject1.contains(":");
                        str1 = str1+"userOpen="+b+",netSpt="+b1+",isIpv6="+b2;
                        if (b1 && (b && !b2)) {
                            b2 = sad.C();
                            b = sad.A();
                            b1 = sad.B();
                            boolean b3 = sad.l(oobject.mHost);
                            boolean b4 = sad.n(oobject.mHost);
                            str1 = str1+",amdcDis="+b2+",compenAB="+b+",speedAB="+b1+",compenList="+b3+",speedList="+b4;
                            if (b2 || !sad.v()) {
                                oobject.mUsedProtocolMode = oobject.mUsedProtocolMode & 0xf7ff;
                            }
                            if (b && b3) {
                                oobject.mUsedProtocolMode = oobject.mUsedProtocolMode | 0x0800;
                            }
                            if (b1 && b4) {
                                oobject.mUsedProtocolMode = oobject.mUsedProtocolMode | 0x0800;
                            }
                        }else {
                            oobject.mUsedProtocolMode = oobject.mUsedProtocolMode & 0xf7ff;
                        }
                        if ((oobject.mUsedProtocolMode & 0x0800) != 0) {
                            i1 = 1;
                        }
                        oobject.putExtra("mpEnv", str1+i1);
                        if (((oobject.mUsedProtocolMode & 0x0200) != 0) && (p0 == null || (((oobject.mUsedProtocolMode & 0x0200) == 0) && p0 != null))) {
                            oobject.mUsedProtocolMode = oobject.mUsedProtocolMode & 0xfdff;
                            oobject.setTunnelProxyClose(true);
                            mSuperviseCo = oobject.mSuperviseConnectInfo;
                            mSuperviseCo.tunnelDegraded = 1;
                            mSuperviseCo.tunnelErrorCode = -1102;
                        }
                        if (sad.h() && !sad.y()) {
                            int mUsedProtocolMode = oobject.mUsedProtocolMode;
                            if (!((mUsedProtocolMode & 0x0200) != 0) || !((mUsedProtocolMode & 0x0800) != 0)) {
                                if ((oobject.mUsedProtocolMode & 0x0800) != 0) {
                                    this.registerNetworkStatusChangeListener();
                                    if (sad.d()) {
                                        this.registerAppLifecycleListenerListener();
                                    }
                                    if (sad.j(oobject.certHost)) {
                                        oobject.sessionParameter = oobject.sessionParameter | 1;
                                    }
                                }
                                if (sad.C(oobject.mHost)) {
                                    oobject.sessionParameter = oobject.sessionParameter | 0x08;
                                }
                            }
                        }
                        oobject.mUsedProtocolMode = oobject.mUsedProtocolMode & 0xfdff;
                        oobject.setTunnelProxyClose(true);
                        mSuperviseCo = oobject.mSuperviseConnectInfo;
                        mSuperviseCo.tunnelDegraded = 1;
                        mSuperviseCo.tunnelErrorCode = -4990;
                        if ((oobject.mUsedProtocolMode & 0x0800) != 0) {
                            this.registerNetworkStatusChangeListener();
                            if (sad.d()) {
                                this.registerAppLifecycleListenerListener();
                            }
                            if (sad.j(oobject.certHost)) {
                                oobject.sessionParameter = oobject.sessionParameter | 1;
                            }
                        }
                        if (sad.C(oobject.mHost)) {
                            oobject.sessionParameter = oobject.sessionParameter | 0x08;
                        }
                    }else {
                        throw new SpdyErrorException("certHost is null", -1102);
                    }
                }else {
                    throw new SpdyErrorException("platform not support", -1107);
                }
            }
        }
        if (sad.s(oobject.mHost)) {
            oobject.sessionParameter = oobject.sessionParameter | 2;
        }
        if (sad.v(oobject.mHost)) {
            oobject.sessionParameter = oobject.sessionParameter | 4;
        }
        return;
    }
    public int putSpdyStreamCtx(SpdyStreamContext p0){
        int tstreamcount = 0;
        synchronized (this.lock) {
            tstreamcount = this.streamcount;
            this.streamcount = tstreamcount + 1;
            this.spdyStream.put(tstreamcount, p0);
        }
        return tstreamcount;
    }
    public void registerAppLifecycleListenerListener(){
        int i = 1;
        if(!this.isAddAppLifecycleListen.getAndSet(true)){
            sab.a(this.LifecycleListener);
        }
        return;
    }
    public void registerNetworkStatusChangeListener(){
        int i = 1;
        if(!this.isAddInterfaceListen.getAndSet(true)){
            NetWorkStatusUtil.a(this.NetworkStatusChangeListener);
        }
        return;
    }
    public void releasePptr(){
        this.pptr4sessionNativePtr.c();
    }
    public void removeSpdyStream(int p0){
        if(p0 > 0){
            synchronized (this.lock) {
                this.spdyStream.remove(p0);
            }
            return;
        }else {
            return;
        }
    }
    public int sendCustomControlFrame(int p0,int p1,int p2,int p3,byte[] p4){
        return this.sendCustomControlFrame(SpdyProtocol.DataChannel.ReliableChannel, SpdySession.QosLevel.DEFAULT_LEVEL, p0, p1, p2, p3, p4);
    }
    public int sendCustomControlFrame(SpdyProtocol.DataChannel p0,SpdySession.QosLevel p1,int p2,int p3,int p4,int p5,byte[] p6){
        Object[] objArray;
        boolean b;
        int i10;
        SpdySession oobject = this;
        SpdyProtocol.DataChannel oobject1 = p0;
        int i = p3;
        int i1 = p5;
        byte[] oobject2 = p6;
        int i2 = 7;
        int i3 = 6;
        int i4 = 5;
        int i5 = 4;
        int i6 = 3;
        int i7 = 2;
        int i8 = 1;
        int i9 = 0;
        this.sessionIsOpen();
        if (oobject2 != null && oobject2.length <= 0) {
            oobject2 = null;
        }
        byte[] oobject3 = oobject2;
        int mode = oobject.mode;
        if (((mode & 0x0100) != 0) && ((mode & 0x10) != 0)) {
            if (oobject1 == SpdyProtocol.DataChannel.UnreliableChannel) {
                mode = oobject.unreliableChannelMss;
                if (i1 > mode && mode != -1) {
                    b = true;
                    if (b) {
                        throw new SpdyErrorException("length "+i1+" exceeds Mss:"+oobject.unreliableChannelMss, -2020);
                    }
                }
            }
            b = false;
            if (b) {
                throw new SpdyErrorException("length "+i1+" exceeds Mss:"+oobject.unreliableChannelMss, -2020);
            }
        }
        if (oobject.pptr4sessionNativePtr.a()) {
            i10 = this.sendCustomControlFrameN(oobject.sessionNativePtr, p0.getDataChannelInt(), p1.getQosLevel(), p2, p3, p4, p5, oobject3);
            oobject.pptr4sessionNativePtr.b();
        }else {
            i10 = -2001;
        }
        if (i10 != 0) {
            return i10;
        }else {
            throw new SpdyErrorException("sendCustomControlFrame error: "+i10, i10);
        }
    }
    public void setMode(int p0){
        this.mode = p0;
    }
    public int setOption(int p0,int p1){
        this.sessionIsOpen();
        if (this.pptr4sessionNativePtr.a()) {
            p0 = this.setOptionN(this.sessionNativePtr, p0, p1);
            this.pptr4sessionNativePtr.b();
        }else {
            p0 = -2001;
        }
        if (p0 != 0) {
            return p0;
        }else {
            throw new SpdyErrorException("setOption error: "+p0, p0);
        }
    }
    public void setSessionNativePtr(long p0){
        this.sessionNativePtr = p0;
    }
    public void setSessionPoolUniqueKey(String p0){
        this.sessionPoolUniqueKey = p0;
    }
    public void setSuperviseConnectInfoOnConnectedCB(SuperviseConnectInfo p0){
        int i = 0;
        if(p0 == null){
            return;
        }else {
            try{
                this.mSuperviseConnectInfo.connectTime = p0.connectTime;
                this.mSuperviseConnectInfo.retryTimes = p0.retryTimes;
                this.mSuperviseConnectInfo.timeout = p0.timeout;
                this.mSuperviseConnectInfo.handshakeTime = p0.handshakeTime;
                this.mSuperviseConnectInfo.doHandshakeTime = p0.doHandshakeTime;
                this.mSuperviseConnectInfo.sessionTicketReused = p0.sessionTicketReused;
                if ((this.mSuperviseConnectInfo.isForceCellular = p0.isForceCellular) != 0) {
                    this.mSuperviseConnectInfo.scid = p0.scid;
                    this.mSuperviseConnectInfo.dcid = p0.dcid;
                    this.mSuperviseConnectInfo.congControlKind = p0.congControlKind;
                    if ((this.mSuperviseConnectInfo.isQuicTry0RTT = p0.isQuicTry0RTT) != 0) {
                        this.mSuperviseConnectInfo.currStrategySeq = p0.currStrategySeq;
                        this.mSuperviseConnectInfo.currStrategyStatus = p0.currStrategyStatus;
                        this.mSuperviseConnectInfo.tunnelScid = p0.tunnelScid;
                        this.mSuperviseConnectInfo.tunnelDcid = p0.tunnelDcid;
                        this.mSuperviseConnectInfo.tunnelConnectTime = p0.tunnelConnectTime;
                        this.mSuperviseConnectInfo.tunnelType = p0.tunnelType;
                        StrategyInfo strategyInfo = null;
                        if (this.isTunnelProxyClose) {
                            if (this.tunnelInfoArrayList != null && this.tunnelInfoArrayList != Collections.EMPTY_LIST) {
                                strategyInfo = (StrategyInfo) this.tunnelInfoArrayList.get(i);
                            }
                        }else if((strategyInfo = this.getStrategyInfoBySeq(p0.currStrategySeq)) != null){
                            strategyInfo.setStrategyStatus(p0.currStrategyStatus);
                        }
                        p0.tunnelInfo = strategyInfo;
                        this.mSuperviseConnectInfo.tunnelInfo = strategyInfo;
                    }
                }
                return;
            }catch(Exception e0){
            }
        }
    }
    public void setSuperviseConnectInfoOnDisconnectedCB(SuperviseConnectInfo p0){
        Object[] objArray;
        if(p0 == null){
            return;
        }else {
            this.mSuperviseConnectInfo.reused_counter = p0.reused_counter;
            this.mSuperviseConnectInfo.keepalive_period_second = p0.keepalive_period_second;
            this.mSuperviseConnectInfo.timeout = p0.timeout;
            this.mSuperviseConnectInfo.handshakeTime = p0.handshakeTime;
            this.mSuperviseConnectInfo.doHandshakeTime = p0.doHandshakeTime;
            this.mSuperviseConnectInfo.sessionTicketReused = p0.sessionTicketReused;
            this.mSuperviseConnectInfo.srtt = p0.srtt;
            this.mSuperviseConnectInfo.connRecvSize = p0.connRecvSize;
            this.mSuperviseConnectInfo.connSendSize = p0.connSendSize;
            this.mSuperviseConnectInfo.recvPacketCount = p0.recvPacketCount;
            this.mSuperviseConnectInfo.sendPacketCount = p0.sendPacketCount;
            this.mSuperviseConnectInfo.recvPacketMax = p0.recvPacketMax;
            if ((this.mSuperviseConnectInfo.connLastRdEventIdleTime = p0.connLastRdEventIdleTime) != 0) {
                this.mSuperviseConnectInfo.retransmissionRate = p0.retransmissionRate;
                this.mSuperviseConnectInfo.lossRate = p0.lossRate;
                this.mSuperviseConnectInfo.tlpCount = p0.tlpCount;
                this.mSuperviseConnectInfo.rtoCount = p0.rtoCount;
                this.mSuperviseConnectInfo.sendCount = p0.sendCount;
                this.mSuperviseConnectInfo.recvCount = p0.recvCount;
                this.mSuperviseConnectInfo.xqc0RttStatus = p0.xqc0RttStatus;
                this.mSuperviseConnectInfo.multiNetStatus = p0.multiNetStatus;
                this.mSuperviseConnectInfo.mpquicStatus = p0.mpquicStatus;
                this.mSuperviseConnectInfo.defaultPathRecvWeight = p0.defaultPathRecvWeight;
                this.mSuperviseConnectInfo.defaultPathSendWeight = p0.defaultPathSendWeight;
                if ((this.mSuperviseConnectInfo.mpquicPathInfo = p0.mpquicPathInfo) != null) {
                    this.mSuperviseConnectInfo.tunnel0RTTStatus = p0.tunnel0RTTStatus;
                    if (this.mSuperviseConnectInfo.tunnelErrorCode == -1) {
                        this.mSuperviseConnectInfo.tunnelErrorCode = p0.tunnelErrorCode;
                    }
                    if (this.mSuperviseConnectInfo.tunnelDegraded == -1) {
                        this.mSuperviseConnectInfo.tunnelDegraded = p0.tunnelDegraded;
                    }
                    this.mSuperviseConnectInfo.tunnelRetryTimes = p0.tunnelRetryTimes;
                }
            }
            return;
        }
    }
    public void setTunnelProxyClose(boolean p0){
        this.isTunnelProxyClose = p0;
    }
    public int streamReset(long p0,int p1){
        int i;
        this.sessionIsOpen();
        if (this.pptr4sessionNativePtr.a()) {
            i = this.streamCloseN(this.sessionNativePtr, (int)p0, p1);
            this.pptr4sessionNativePtr.b();
        }else {
            i = -2001;
        }
        if (i != 0) {
            return i;
        }else {
            throw new SpdyErrorException("streamReset error: "+i, i);
        }
    }
    public int submitBioPing(){
        int i;
        this.sessionIsOpen();
        if (this.pptr4sessionNativePtr.a()) {
            i = this.submitBioPingN(this.sessionNativePtr);
            this.pptr4sessionNativePtr.b();
        }else {
            i = -2001;
        }
        if (i != 0) {
            return i;
        }else {
            throw new SpdyErrorException("submitBioPing error: "+i, i);
        }
    }
    public int submitPing(){
        int i;
        this.sessionIsOpen();
        if (this.pptr4sessionNativePtr.a()) {
            i = this.submitPingN(this.sessionNativePtr);
            this.pptr4sessionNativePtr.b();
        }else {
            i = -2001;
        }
        if (i != 0) {
            return i;
        }else {
            throw new SpdyErrorException("submitPing error: "+i, i);
        }
    }
    public int submitRequest(SpdyRequest p0,SpdyDataProvider p1,Object p2,Spdycb p3){
        Object[] objArray;
        byte[] uobyteArray;
        int i6;
        SpdySession oobject = this;
        SpdyRequest oobject1 = p0;
        SpdyDataProvider oobject2 = p1;
        Object oobject3 = p2;
        Spdycb oobject4 = p3;
        int i = 4;
        int i1 = 3;
        int i2 = 2;
        int i3 = 0;
        int i4 = 1;
        if(oobject1 != null && (oobject3 != null && p0.getUrl() != null)){
            this.sessionIsOpen();
            if (sad.n() && (sad.B() && (oobject.isMultiPathParallelAddSpeedEnable && (this.isMultiPathMode() && sad.a(p0.getUrl()))))) {
                oobject1.setRequestPriority(RequestPriority.HIGHEST);
            }
            if ((uobyteArray = SpdyAgent.dataproviderToByteArray(p0, p1)) != null && uobyteArray.length <= 0) {
                uobyteArray = null;
            }
            byte[] uobyteArray1 = null;
            boolean spdyDataProv = (oobject2 != null)? oobject2.finished: true;
            SpdyStreamContext spdyStreamCo = new SpdyStreamContext(oobject3, oobject4);
            int i5 = oobject.putSpdyStreamCtx(spdyStreamCo);
            String[] stringArray = SpdyAgent.mapToByteArray(p0.getHeaders());
            p0.optimizeRequestTimeout();
            if (oobject.pptr4sessionNativePtr.a()) {
                i6 = this.submitRequestN(oobject.sessionNativePtr, p0.getUrlPath(), (byte)p0.getPriority(), stringArray, uobyteArray1, spdyDataProv, i5, p0.getRequestTimeoutMs(), p0.getRequestRdTimeoutMs(), p0.getBodyRdTimeoutMs(), p0.getRequestRecvRateBps());
                oobject.pptr4sessionNativePtr.b();
            }else {
                i6 = -2001;
            }
            if (i6 >= 0) {
                spdyStreamCo.streamId = i6;
                return i6;
            }else {
                oobject.removeSpdyStream(i5);
                throw new SpdyErrorException("submitRequest error: "+i6, i6);
            }
        }else {
            throw new SpdyErrorException("submitRequest error: -1102", -1102);
        }
    }
    public void unRegisterAppLifecycleListener(){
        int i = 0;
        if(this.isAddAppLifecycleListen.getAndSet(false)){
            sab.b(this.LifecycleListener);
        }
        return;
    }
    public void unRegisterNetworkStatusChangeListener(){
        int i = 0;
        if(this.isAddInterfaceListen.getAndSet(false)){
            NetWorkStatusUtil.b(this.NetworkStatusChangeListener);
        }
        return;
    }

    enum QosLevel {
        HIGHEST(1),
        HIGH(2),
        MEDIUM(3),
        NORMAL(4),
        LOW(5),
        LOWEST(6),
        DEFAULT_LEVEL(4);


        private int qosLevel;

        QosLevel(int p2){

            this.qosLevel = p2;
        }

        int getQosLevel(){
            return this.qosLevel;
        }
    }

}
