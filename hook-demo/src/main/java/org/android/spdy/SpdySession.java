package org.android.spdy;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import tb.sab$a;

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
    private  d pptr4sessionNativePtr;
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
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[0] = this;
            return $ipChange.ipc$dispatch("b6886785", objArray).intValue();
        }else {
            SpdySession tlock = this.lock;
            _monitor_enter(tlock);
            if (!this.sessionClearedFromSessionMgr.getAndSet(i)) {
                this.agent.clearSpdySession(this.sessionPoolUniqueKey);
            }
            this.unRegisterNetworkStatusChangeListener();
            this.unRegisterAppLifecycleListener();
            if ((allStreamCb = this.getAllStreamCb()) != null) {
                int len = allStreamCb.length;
                for (int i1 = 0; i1 < len; i1 = i1 + 1) {
                    object oobject = allStreamCb[i1];
                    spduLog.Logi("tnetsdk.SpdySession", "[spdyStreamCloseCallback] unfinished stm=", (long)oobject.streamId);
                    oobject.callBack.spdyStreamCloseCallback(this, (long)oobject.streamId, -2001, oobject.streamContext, null);
                }
            }
            this.spdyStream.clear();
            _monitor_exit(tlock);
            return 0;
        }
    }
    private String getAuthority(){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.authority;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("3dac808c", objArray);
    }
    public static String getHostFromDomain(String p0){
        int i;
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{p0};
            return $ipChange.ipc$dispatch("baafc2e3", objArray);
        }else if((i = p0.indexOf("_")) > 0){
            p0 = p0.substring((p0.indexOf("://") + 3), i);
        }
        return p0;
    }
    private StrategyInfo getStrategyInfoBySeq(int p0){
        SpdySession ttunnelInfoA;
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0)};
            return $ipChange.ipc$dispatch("87969029", objArray);
        }else if((ttunnelInfoA = this.tunnelInfoArrayList) != null && ttunnelInfoA != Collections.EMPTY_LIST){
            while (i < this.tunnelInfoArrayList.size()) {
                StrategyInfo strategyInfo = this.tunnelInfoArrayList.get(i);
                if (strategyInfo.getTunnelStrategySeq() == p0) {
                    return strategyInfo;
                }
                i = i + 1;
            }
        }
        return null;
    }
    private void putExtra(String p0,Object p1){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0,p1};
            $ipChange.ipc$dispatch("c83a756e", objArray);
            return;
        }else if(this.extra == null){
            this.extra = new JSONObject();
        }
        this.extra.put(p0, p1);
        return;
    }
    private native int sendCustomControlFrameN(long p0,int p1,int p2,int p3,int p4,int p5,int p6,byte[] p7);
    private native int sendHeadersN(long p0,int p1,String[] p2,boolean p3);
    private void sessionIsOpen(){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("5c79b851", objArray);
            return;
        }else if(!this.closed.get()){
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
    private native int submitRequestN(long p0,String p1,byte p2,String[] p3,byte[] p4,boolean p5,int p6,int p7,int p8,int p9,int p10);
    public void checkWifiConsecutiveFailStatus(int p0){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0)};
            $ipChange.ipc$dispatch("e67d75bf", objArray);
            return;
        }else if(NetWorkStatusUtil.a()){
            if (p0 != -2003 && (p0 != -5004 && (!this.isTunnel() && p0 == -4993))) {
                if (p0 == -2002) {
                    int i = 0;
                    if ((this.mSuperviseConnectInfo.connRecvSize - i) || ((this.connectTaskStartTime - i) <= 0 || ((System.currentTimeMillis() - this.connectTaskStartTime) - 3000) <= 0)) {
                        label_0058 :
                        return;
                    }
                }else {
                goto label_0058 ;
                }
            }
            SpdyAgent.wifiConsecutiveFailCount = SpdyAgent.wifiConsecutiveFailCount + 1;
          goto label_0058 ;
        }else {
          goto label_0058 ;
        }
    }
    public int cleanUp(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        int i1 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[i1] = this;
            return $ipChange.ipc$dispatch("d0f7d53e", objArray).intValue();
        }else {
            spduLog.Logd("tnetsdk.SpdySession", "[SpdySession.cleanUp] - ");
            if (!this.closed.getAndSet(i)) {
                this.agent.removeSession(this);
                i1 = this.closeprivate();
            }
            return i1;
        }
    }
    public void clearAllStreamCb(){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("39fb473a", objArray);
            return;
        }else {
            spduLog.Logd("tnetsdk.SpdySession", "[SpdySession.clearAllStreamCb] - ");
            SpdySession tlock = this.lock;
            _monitor_enter(tlock);
            this.spdyStream.clear();
            _monitor_exit(tlock);
            return;
        }
    }
    public int closeInternal(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        int i1 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[i1] = this;
            return $ipChange.ipc$dispatch("e6a5a2cf", objArray).intValue();
        }else if(!this.closed.getAndSet(i)){
            i1 = this.closeprivate();
        }
        return i1;
    }
    public int closeSession(){
        SpdySession tpptr4sessio;
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        int i1 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[i1] = this;
            return $ipChange.ipc$dispatch("a316aaf8", objArray).intValue();
        }else {
            spduLog.Logd("tnetsdk.SpdySession", "[SpdySession.closeSession] - ");
            this.unRegisterNetworkStatusChangeListener();
            this.unRegisterAppLifecycleListener();
            SpdySession tlock = this.lock;
            _monitor_enter(tlock);
            if (!this.sessionClearedFromSessionMgr.getAndSet(i)) {
                spduLog.Logd("tnetsdk.SpdySession", "[SpdySession.closeSession] - ", this.authority);
                this.agent.clearSpdySession(this.sessionPoolUniqueKey);
                if (this.pptr4sessionNativePtr.a()) {
                    try{
                        i1 = this.agent.closeSession(this.sessionNativePtr);
                        tpptr4sessio = this.pptr4sessionNativePtr;
                    }catch(java.lang.UnsatisfiedLinkError e1){
                        e1.printStackTrace();
                        tpptr4sessio = this.pptr4sessionNativePtr;
                    }
                    tpptr4sessio.b();
                }else {
                    i1 = -2001;
                }
            }
            _monitor_exit(tlock);
            return i1;
        }
    }
    public SpdyStreamContext[] getAllStreamCb(){
        int i;
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("6797af7a", objArray);
        }else {
            SpdyStreamContext[] spdyStreamCo = null;
            SpdySession tlock = this.lock;
            _monitor_enter(tlock);
            if ((i = this.spdyStream.size()) > 0) {
                spdyStreamCo = new SpdyStreamContext[i];
                this.spdyStream.toArray(spdyStreamCo);
            }
            _monitor_exit(tlock);
            return spdyStreamCo;
        }
    }
    public int getConnectFastTimeout(int p0){
        long l;
        int i2;
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        int i1 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0)};
            return $ipChange.ipc$dispatch("dad5f9de", objArray).intValue();
        }else if(sad.w() && sad.g(this.mHost)){
            if (this.isQUIC()) {
                if (p0 <= 0 || (sad.f() - (long)p0) < 0) {
                    l = sad.f();
                    label_0057 :
                    i2 = (int)l;
                    label_0058 :
                    Object[] objArray1 = new Object[10];
                    objArray1[i1] = "demain";
                    objArray1[i] = this.domain;
                    objArray1[2] = "certHost";
                    objArray1[3] = this.certHost;
                    objArray1[4] = "protocol";
                    objArray1[5] = Integer.valueOf(this.mode);
                    objArray1[6] = "connTimeoutMs";
                    objArray1[7] = Integer.valueOf(p0);
                    objArray1[8] = "changeTimeout";
                    objArray1[9] = Integer.valueOf(i2);
                    spduLog.Tloge("tnetsdk.SpdySession", "", "In connect fast timeout white list", objArray1);
                    p0 = i2;
                }
            }else if(p0 > 0 && (sad.g() - (long)p0) >= 0){
                l = sad.g();
             goto label_0057 ;
            }
            i2 = p0;
          goto label_0058 ;
        }
        return p0;
    }
    public String getConnectInfoOnConnected(){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("e6b384f1", objArray);
        }else {
            StringBuilder str = new StringBuilder(64)+"mode="+this.mode+",connectTime="+this.mSuperviseConnectInfo.connectTime+",handshakeTime="+this.mSuperviseConnectInfo.handshakeTime+",doHandshakeTime="+this.mSuperviseConnectInfo.doHandshakeTime+",isForceCellular="+this.mSuperviseConnectInfo.isForceCellular;
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
    }
    public String getConnectInfoOnDisConnected(){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("4abf6ebd", objArray);
        }else {
            StringBuilder str = new StringBuilder(256)+this.getConnectInfoOnConnected()+",reusedCnt="+this.mSuperviseConnectInfo.reused_counter+",keepAlive="+this.mSuperviseConnectInfo.keepalive_period_second+",srtt="+this.mSuperviseConnectInfo.srtt+",sendSz="+this.mSuperviseConnectInfo.connSendSize+",recvSz="+this.mSuperviseConnectInfo.connRecvSize+",recvMax="+this.mSuperviseConnectInfo.recvPacketMax+",sendPktCnt="+this.mSuperviseConnectInfo.sendPacketCount+",recvPktCnt="+this.mSuperviseConnectInfo.recvPacketCount+",connRdIdleTime="+this.mSuperviseConnectInfo.connLastRdEventIdleTime+",datagramMss="+this.unreliableChannelMss;
            if (this.isQUIC()) {
                str = str+",retransRate="+this.mSuperviseConnectInfo.retransmissionRate+",tlpCnt="+this.mSuperviseConnectInfo.tlpCount+",rtoCnt="+this.mSuperviseConnectInfo.rtoCount+",sendCnt="+this.mSuperviseConnectInfo.sendCount+",recvCnt="+this.mSuperviseConnectInfo.recvCount+",lossRate="+this.mSuperviseConnectInfo.lossRate+",0RttStatus="+this.mSuperviseConnectInfo.xqc0RttStatus+",multiNet="+this.mSuperviseConnectInfo.multiNetStatus+",mpStatus="+this.mSuperviseConnectInfo.mpquicStatus+",sendWeight="+this.mSuperviseConnectInfo.defaultPathSendWeight+",recvWeight="+this.mSuperviseConnectInfo.defaultPathRecvWeight+",mpPathInfo="+this.mSuperviseConnectInfo.mpquicPathInfo;
                if (this.isTunnel()) {
                    str = str+",tl0RTTStatus="+this.mSuperviseConnectInfo.tunnel0RTTStatus+",tlRetryTimes="+this.mSuperviseConnectInfo.tunnelRetryTimes+",tlDegraded="+this.mSuperviseConnectInfo.tunnelDegraded+",tlErrorCode="+this.mSuperviseConnectInfo.tunnelErrorCode;
                }
            }
            return str+","+this.getExternStat();
        }
    }
    public String getDomain(){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.domain;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("6fba2f7f", objArray);
    }
    public String getExternStat(){
        SpdySession textra;
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("c8a3972f", objArray);
        }else {
            this.putExtra("qcStat", Integer.valueOf(SoInstallMgrSdk.loadQuicStat));
            this.putExtra("net", Integer.valueOf(NetWorkStatusUtil.g()));
            this.putExtra("mpNetEnv", NetWorkStatusUtil.d());
            if ((textra = this.extra) != null) {
                return textra.toString();
            }
            return "null";
        }
    }
    public int getMode(){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.mode;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("56d77213", objArray).intValue();
    }
    public int getRefCount(){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.refcount;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("a97da52c", objArray).intValue();
    }
    public int getSessionParameter(){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.sessionParameter;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("ac20923", objArray).intValue();
    }
    public String getSessionPoolUniqueKey(){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.sessionPoolUniqueKey;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("d823cca7", objArray);
    }
    public String getSessionSeq(){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("eac5f67a", objArray);
        }else if(TextUtils.isEmpty(this.seq)){
            this.seq = Integer.toHexString(this.hashCode());
        }
        return this.seq;
    }
    public SpdyStreamContext getSpdyStream(int p0){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0)};
            return $ipChange.ipc$dispatch("ea52d066", objArray);
        }else {
            SpdyStreamContext spdyStreamCo = null;
            if (p0 > 0) {
                SpdySession tlock = this.lock;
                _monitor_enter(tlock);
                spdyStreamCo = this.spdyStream.get(p0);
                _monitor_exit(tlock);
            }
            return spdyStreamCo;
        }
    }
    public byte[] getTunnelInfoBytes(){
        SpdySession ttunnelInfoA;
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("f443d9f7", objArray);
        }else if((ttunnelInfoA = this.tunnelInfoArrayList) != null && (ttunnelInfoA != Collections.EMPTY_LIST && this.tunnelInfoArrayList.size())){
            StringBuilder str = "";
            for (; i < this.tunnelInfoArrayList.size(); i = i + 1) {
                str = str.append(this.tunnelInfoArrayList.get(i).infoToString()).append("/");
            }
            return str.getBytes();
        }else {
            return null;
        }
    }
    public int getUsedProtocolMode(){
        return this.mUsedProtocolMode;
    }
    public Object getUserData(){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.userData;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("a3c756e0", objArray);
    }
    public void increRefCount(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[0] = this;
            $ipChange.ipc$dispatch("c2ff3bf4", objArray);
            return;
        }else {
            this.refcount = this.refcount + i;
            return;
        }
    }
    public boolean isConnectTryForceCellular(boolean p0){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 2;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[0] = this;
            objArray[1] = new Boolean(p0);
            return $ipChange.ipc$dispatch("a33b6377", objArray).booleanValue();
        }else if(sad.c() && (this.isMultiPathCompensateEnable != null && (sad.e(this.mHost) && (NetWorkStatusUtil.a() && (NetWorkStatusUtil.a != null && (!sab.b() && sad.d())))))){
            $ipChange = (sad.x() && SpdyAgent.wifiConsecutiveFailCount >= i)? 1: 0;
            if (!p0 && !$ipChange) {
                return 0;
            }else {
                return 1;
            }
        }else {
            return 0;
        }
    }
    public boolean isForceUseCellular(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("4c9241fb", objArray).booleanValue();
        }else if(this.mSuperviseConnectInfo.isForceCellular == 1){
            return 1;
        }else {
            return i;
        }
    }
    public boolean isHTTP3(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("7ee069cc", objArray).booleanValue();
        }else if((this.mode & 0x0100)){
            return 1;
        }else {
            return i;
        }
    }
    public boolean isMultiPathMode(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("b6120fc2", objArray).booleanValue();
        }else if(!((this.mode & 0x0800)) && !((this.mUsedProtocolMode & 0x0800))){
            return i;
        }else {
            return 1;
        }
    }
    public boolean isQUIC(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("bbe592f3", objArray).booleanValue();
        }else if(!this.isHTTP3() && !((this.mode & 0x04))){
            return i;
        }else {
            return 1;
        }
    }
    public boolean isQuicTry0RTT(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("98884ec0", objArray).booleanValue();
        }else if(this.mSuperviseConnectInfo.isQuicTry0RTT == 1){
            return 1;
        }else {
            return i;
        }
    }
    public boolean isTunnel(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("6089dbd", objArray).booleanValue();
        }else if((this.mode & 0x0200)){
            return 1;
        }else {
            return i;
        }
    }
    public boolean isTunnelProxyClose(){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.isTunnelProxyClose;
        }
        Object[] objArray = new Object[]{this};
        return $ipChange.ipc$dispatch("9a896687", objArray).booleanValue();
    }
    public void notifyLifecycleEvent(boolean p0){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        int i1 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Boolean(p0)};
            $ipChange.ipc$dispatch("9805d5d4", objArray);
            return;
        }else if(!sad.d()){
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
        SpdySession mSuperviseCo;
        object oobject = this;
        object oobject1 = p1;
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 3;
        int i1 = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[i1] = oobject;
            objArray[1] = p0;
            objArray[2] = oobject1;
            $ipChange.ipc$dispatch("18bf06dc", objArray);
            return;
        }else {
            SpdySession mode = oobject.mode;
            if (((mode & 0x0100)) || ((mode & 4))) {
                String str = "tnetsdk.SpdySession";
                if (sad.r() && SoInstallMgrSdk.loadQuicSucc) {
                    if (!"meizu".equalsIgnoreCase(Build.BRAND) && (!"xiaomi".equalsIgnoreCase(Build.BRAND) && (!"cmcc".equalsIgnoreCase(Build.BRAND) || (Build$VERSION.SDK_INT != 22 && (!"samsung".equalsIgnoreCase(Build.BRAND) && ("sm-a7160".equalsIgnoreCase(Build.MODEL) && Build$VERSION.SDK_INT == 31)))))) {
                        mode = oobject.mode;
                        if (((mode & 0x04)) && !((mode & 0x10))) {
                            throw new SpdyErrorException("QUIC should with custom", -1110);
                        }else if(oobject.certHost != null){
                            StringBuilder str1 = new StringBuilder(64);
                            boolean b = (oobject.isMultiPathCompensateEnable == null && oobject.isMultiPathParallelAddSpeedEnable == null)? false: true;
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
                                    Object[] objArray1 = new Object[]{"ab",Boolean.valueOf(b2),"orange",Boolean.valueOf(sad.v())};
                                    spduLog.Tloge(str, this.getSessionSeq(), "amdc multipath disable", objArray1);
                                }
                                if (b && b3) {
                                    oobject.mUsedProtocolMode = oobject.mUsedProtocolMode | 0x0800;
                                    objArray2 = new Object[i1];
                                    spduLog.Tloge(str, this.getSessionSeq(), "In mpquic connect compensate host white list", objArray2);
                                }
                                if (b1 && b4) {
                                    oobject.mUsedProtocolMode = oobject.mUsedProtocolMode | 0x0800;
                                    objArray2 = new Object[i1];
                                    spduLog.Tloge(str, this.getSessionSeq(), "In mpquic connect add speed host white list", objArray2);
                                }
                            }else {
                                oobject.mUsedProtocolMode = oobject.mUsedProtocolMode & 0xf7ff;
                            }
                            str1 = str1+",path=";
                            if ((oobject.mUsedProtocolMode & 0x0800)) {
                                i1 = 1;
                            }
                            oobject.putExtra("mpEnv", str1+i1);
                            if (((oobject.mUsedProtocolMode & 0x0200)) && (p0 == null || (!((oobject.mUsedProtocolMode & 0x0200)) && p0 != null))) {
                                oobject.mUsedProtocolMode = oobject.mUsedProtocolMode & 0xfdff;
                                oobject.setTunnelProxyClose(1);
                                mSuperviseCo = oobject.mSuperviseConnectInfo;
                                mSuperviseCo.tunnelDegraded = 1;
                                mSuperviseCo.tunnelErrorCode = -1102;
                            }
                            if (sad.h() && !sad.y()) {
                                mSuperviseCo = oobject.mUsedProtocolMode;
                                if (!((mSuperviseCo & 0x0200)) || !((mSuperviseCo & 0x0800))) {
                                    label_01ce :
                                    if ((oobject.mUsedProtocolMode & 0x0800)) {
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
                            oobject.setTunnelProxyClose(1);
                            mSuperviseCo = oobject.mSuperviseConnectInfo;
                            mSuperviseCo.tunnelDegraded = 1;
                            mSuperviseCo.tunnelErrorCode = -4990;
                      goto label_01ce ;
                        }else {
                            throw new SpdyErrorException("certHost is null", -1102);
                        }
                    }else {
                        throw new SpdyErrorException("platform not support", -1107);
                    }
                }else {
                    Object[] objArray3 = new Object[]{"loadStat",Integer.valueOf(SoInstallMgrSdk.loadQuicStat)};
                    spduLog.Tloge(str, null, "QUIC so load fail", objArray3);
                    throw new SpdyErrorException("QUIC load fail", -1108);
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
    }
    public int putSpdyStreamCtx(SpdyStreamContext p0){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            return $ipChange.ipc$dispatch("e14d5804", objArray).intValue();
        }else {
            SpdySession tlock = this.lock;
            _monitor_enter(tlock);
            SpdySession tstreamcount = this.streamcount;
            this.streamcount = tstreamcount + 1;
            this.spdyStream.put(tstreamcount, p0);
            _monitor_exit(tlock);
            return tstreamcount;
        }
    }
    public void registerAppLifecycleListenerListener(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[0] = this;
            $ipChange.ipc$dispatch("279ef8bb", objArray);
            return;
        }else if(!this.isAddAppLifecycleListen.getAndSet(i)){
            sab.a(this.LifecycleListener);
        }
        return;
    }
    public void registerNetworkStatusChangeListener(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 1;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[i];
            objArray[0] = this;
            $ipChange.ipc$dispatch("20846bc8", objArray);
            return;
        }else if(!this.isAddInterfaceListen.getAndSet(i)){
            NetWorkStatusUtil.a(this.NetworkStatusChangeListener);
        }
        return;
    }
    public void releasePptr(){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("d69e472c", objArray);
            return;
        }else {
            this.pptr4sessionNativePtr.c();
            return;
        }
    }
    public void removeSpdyStream(int p0){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0)};
            $ipChange.ipc$dispatch("7b8353a6", objArray);
            return;
        }else if(p0 > 0){
            SpdySession tlock = this.lock;
            _monitor_enter(tlock);
            this.spdyStream.remove(p0);
            _monitor_exit(tlock);
            return;
        }else {
            return;
        }
    }
    public int sendCustomControlFrame(int p0,int p1,int p2,int p3,byte[] p4){
        IpChange $ipChange = SpdySession.$ipChange;
        if (!$ipChange instanceof IpChange) {
            return this.sendCustomControlFrame(SpdyProtocol$DataChannel.ReliableChannel, SpdySession$QosLevel.DEFAULT_LEVEL, p0, p1, p2, p3, p4);
        }
        Object[] objArray = new Object[]{this,new Integer(p0),new Integer(p1),new Integer(p2),new Integer(p3),p4};
        return $ipChange.ipc$dispatch("8afa674a", objArray).intValue();
    }
    public int sendCustomControlFrame(SpdyProtocol$DataChannel p0,SpdySession$QosLevel p1,int p2,int p3,int p4,int p5,byte[] p6){
        Object[] objArray;
        boolean b;
        int i10;
        object oobject = this;
        object oobject1 = p0;
        int i = p3;
        int i1 = p5;
        object oobject2 = p6;
        IpChange $ipChange = SpdySession.$ipChange;
        int i2 = 7;
        int i3 = 6;
        int i4 = 5;
        int i5 = 4;
        int i6 = 3;
        int i7 = 2;
        int i8 = 1;
        int i9 = 0;
        if ($ipChange instanceof IpChange) {
            objArray = new Object[]{oobject,oobject1,p1,new Integer(p2),new Integer(i),new Integer(p4),new Integer(i1),oobject2};
            return $ipChange.ipc$dispatch("685daeba", objArray).intValue();
        }else {
            this.sessionIsOpen();
            if (oobject2 != null && oobject2.length <= 0) {
                oobject2 = 0;
            }
            object oobject3 = oobject2;
            SpdySession mode = oobject.mode;
            if (((mode & 0x0100)) && ((mode & 0x10))) {
                if (oobject1 == SpdyProtocol$DataChannel.UnreliableChannel) {
                    mode = oobject.unreliableChannelMss;
                    if (i1 > mode && mode != -1) {
                        b = true;
                        label_0074 :
                        objArray = new Object[14];
                        objArray[i9] = "dataId";
                        objArray[i8] = Integer.valueOf(p2);
                        objArray[i7] = "channel";
                        objArray[i6] = Integer.valueOf(p0.getDataChannelInt());
                        objArray[i5] = "qos";
                        objArray[i4] = Integer.valueOf(p1.getQosLevel());
                        objArray[i3] = "len";
                        objArray[i2] = Integer.valueOf(p5);
                        objArray[8] = "mss";
                        objArray[9] = Integer.valueOf(oobject.unreliableChannelMss);
                        objArray[10] = "type";
                        objArray[11] = Integer.valueOf(p3);
                        objArray[12] = "drop";
                        objArray[13] = Boolean.valueOf(b);
                        spduLog.Tloge("tnetsdk.SpdySession", this.getSessionSeq(), "[accs2][sendCustomFrame]", objArray);
                        if (b) {
                            throw new SpdyErrorException("length "+i1+" exceeds Mss:"+oobject.unreliableChannelMss, -2020);
                        }
                    }
                }
                b = false;
             goto label_0074 ;
            }
            spduLog.Logi("tnet-jni", "[sendCustomControlFrame] - type: ", (long)i);
            if (oobject.pptr4sessionNativePtr.a()) {
                i10 = this.sendCustomControlFrameN(oobject.sessionNativePtr, p0.getDataChannelInt(), p1.getQosLevel(), p2, p3, p4, p5, oobject3);
                oobject.pptr4sessionNativePtr.b();
            }else {
                i10 = -2001;
            }
            if (!i10) {
                return i10;
            }else {
                throw new SpdyErrorException("sendCustomControlFrame error: "+i10, i10);
            }
        }
    }
    public void setMode(int p0){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0)};
            $ipChange.ipc$dispatch("e06b0557", objArray);
            return;
        }else {
            this.mode = p0;
            return;
        }
    }
    public int setOption(int p0,int p1){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Integer(p0),new Integer(p1)};
            return $ipChange.ipc$dispatch("a90e0951", objArray).intValue();
        }else {
            this.sessionIsOpen();
            if (this.pptr4sessionNativePtr.a()) {
                p0 = this.setOptionN(this.sessionNativePtr, p0, p1);
                this.pptr4sessionNativePtr.b();
            }else {
                p0 = -2001;
            }
            if (!p0) {
                return p0;
            }else {
                throw new SpdyErrorException("setOption error: "+p0, p0);
            }
        }
    }
    public void setSessionNativePtr(long p0){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Long(p0)};
            $ipChange.ipc$dispatch("24c7b71a", objArray);
            return;
        }else {
            this.sessionNativePtr = p0;
            return;
        }
    }
    public void setSessionPoolUniqueKey(String p0){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            $ipChange.ipc$dispatch("109f70af", objArray);
            return;
        }else {
            this.sessionPoolUniqueKey = p0;
            return;
        }
    }
    public void setSuperviseConnectInfoOnConnectedCB(SuperviseConnectInfo p0){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,p0};
            $ipChange.ipc$dispatch("84a6ec41", objArray);
            return;
        }else if(p0 == null){
            return;
        }else {
            try{
                this.mSuperviseConnectInfo.connectTime = p0.connectTime;
                this.mSuperviseConnectInfo.retryTimes = p0.retryTimes;
                this.mSuperviseConnectInfo.timeout = p0.timeout;
                this.mSuperviseConnectInfo.handshakeTime = p0.handshakeTime;
                this.mSuperviseConnectInfo.doHandshakeTime = p0.doHandshakeTime;
                this.mSuperviseConnectInfo.sessionTicketReused = p0.sessionTicketReused;
                if (this.mSuperviseConnectInfo.isForceCellular = p0.isForceCellular) {
                    this.mSuperviseConnectInfo.scid = p0.scid;
                    this.mSuperviseConnectInfo.dcid = p0.dcid;
                    this.mSuperviseConnectInfo.congControlKind = p0.congControlKind;
                    if (this.mSuperviseConnectInfo.isQuicTry0RTT = p0.isQuicTry0RTT) {
                        this.mSuperviseConnectInfo.currStrategySeq = p0.currStrategySeq;
                        this.mSuperviseConnectInfo.currStrategyStatus = p0.currStrategyStatus;
                        this.mSuperviseConnectInfo.tunnelScid = p0.tunnelScid;
                        this.mSuperviseConnectInfo.tunnelDcid = p0.tunnelDcid;
                        this.mSuperviseConnectInfo.tunnelConnectTime = p0.tunnelConnectTime;
                        this.mSuperviseConnectInfo.tunnelType = p0.tunnelType;
                        StrategyInfo strategyInfo = null;
                        if (this.isTunnelProxyClose != null) {
                            if (this.tunnelInfoArrayList != null && this.tunnelInfoArrayList != Collections.EMPTY_LIST) {
                                strategyInfo = this.tunnelInfoArrayList.get(i);
                            }
                        }else if((strategyInfo = this.getStrategyInfoBySeq(p0.currStrategySeq)) != null){
                            strategyInfo.setStrategyStatus(p0.currStrategyStatus);
                        }
                        p0.tunnelInfo = strategyInfo;
                        this.mSuperviseConnectInfo.tunnelInfo = strategyInfo;
                    }
                }
                return;
            }catch(java.lang.Exception e0){
            }
        }
    }
    public void setSuperviseConnectInfoOnDisconnectedCB(SuperviseConnectInfo p0){
        Object[] objArray;
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            objArray = new Object[]{this,p0};
            $ipChange.ipc$dispatch("d64a3a3d", objArray);
            return;
        }else if(p0 == null){
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
            if (this.mSuperviseConnectInfo.connLastRdEventIdleTime = p0.connLastRdEventIdleTime) {
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
                if (this.mSuperviseConnectInfo.mpquicPathInfo = p0.mpquicPathInfo) {
                    this.mSuperviseConnectInfo.tunnel0RTTStatus = p0.tunnel0RTTStatus;
                    objArray = -1;
                    if (this.mSuperviseConnectInfo.tunnelErrorCode == objArray) {
                        this.mSuperviseConnectInfo.tunnelErrorCode = p0.tunnelErrorCode;
                    }
                    if (this.mSuperviseConnectInfo.tunnelDegraded == objArray) {
                        this.mSuperviseConnectInfo.tunnelDegraded = p0.tunnelDegraded;
                    }
                    this.mSuperviseConnectInfo.tunnelRetryTimes = p0.tunnelRetryTimes;
                }
            }
            return;
        }
    }
    public void setTunnelProxyClose(boolean p0){
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Boolean(p0)};
            $ipChange.ipc$dispatch("4916a079", objArray);
            return;
        }else {
            this.isTunnelProxyClose = p0;
            return;
        }
    }
    public int streamReset(long p0,int p1){
        int i;
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this,new Long(p0),new Integer(p1)};
            return $ipChange.ipc$dispatch("2adda1e8", objArray).intValue();
        }else {
            this.sessionIsOpen();
            spduLog.Logd("tnetsdk.SpdySession", "[SpdySession.streamReset] - ");
            if (this.pptr4sessionNativePtr.a()) {
                i = this.streamCloseN(this.sessionNativePtr, (int)p0, p1);
                this.pptr4sessionNativePtr.b();
            }else {
                i = -2001;
            }
            if (!i) {
                return i;
            }else {
                throw new SpdyErrorException("streamReset error: "+i, i);
            }
        }
    }
    public int submitBioPing(){
        int i;
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("fea73edc", objArray).intValue();
        }else {
            this.sessionIsOpen();
            if (this.pptr4sessionNativePtr.a()) {
                i = this.submitBioPingN(this.sessionNativePtr);
                this.pptr4sessionNativePtr.b();
            }else {
                i = -2001;
            }
            if (!i) {
                return i;
            }else {
                throw new SpdyErrorException("submitBioPing error: "+i, i);
            }
        }
    }
    public int submitPing(){
        int i;
        IpChange $ipChange = SpdySession.$ipChange;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            return $ipChange.ipc$dispatch("1b490924", objArray).intValue();
        }else {
            this.sessionIsOpen();
            if (this.pptr4sessionNativePtr.a()) {
                i = this.submitPingN(this.sessionNativePtr);
                this.pptr4sessionNativePtr.b();
            }else {
                i = -2001;
            }
            if (!i) {
                return i;
            }else {
                throw new SpdyErrorException("submitPing error: "+i, i);
            }
        }
    }
    public int submitRequest(SpdyRequest p0,SpdyDataProvider p1,Object p2,Spdycb p3){
        Object[] objArray;
        byte[] uobyteArray;
        int i6;
        object oobject = this;
        object oobject1 = p0;
        object oobject2 = p1;
        object oobject3 = p2;
        object oobject4 = p3;
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 4;
        int i1 = 3;
        int i2 = 2;
        int i3 = 0;
        int i4 = 1;
        if ($ipChange instanceof IpChange) {
            objArray = new Object[]{oobject,oobject1,oobject2,oobject3,oobject4};
            return $ipChange.ipc$dispatch("9c9f4e95", objArray).intValue();
        }else if(oobject1 != null && (oobject3 != null && p0.getUrl() != null)){
            this.sessionIsOpen();
            if (sad.n() && (sad.B() && (oobject.isMultiPathParallelAddSpeedEnable != null && (this.isMultiPathMode() && sad.a(p0.getUrl()))))) {
                oobject1.setRequestPriority(RequestPriority.HIGHEST);
                objArray = new Object[i];
                objArray[i3] = "url";
                objArray[i4] = p0.getUrlPath();
                objArray[i2] = "priority";
                objArray[i1] = Integer.valueOf(p0.getPriority());
                spduLog.Tloge("tnetsdk.SpdySession", this.getSessionSeq(), "In mpquic request add speed url white list", objArray);
            }
            if ((uobyteArray = SpdyAgent.dataproviderToByteArray(p0, p1)) != null && uobyteArray.length <= 0) {
                uobyteArray = 0;
            }
            byte[] uobyteArray1 = uobyteArray;
            SpdyDataProvider spdyDataProv = (oobject2 != null)? oobject2.finished: true;
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
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("53b28a0", objArray);
            return;
        }else if(this.isAddAppLifecycleListen.getAndSet(i)){
            sab.b(this.LifecycleListener);
        }
        return;
    }
    public void unRegisterNetworkStatusChangeListener(){
        IpChange $ipChange = SpdySession.$ipChange;
        int i = 0;
        if ($ipChange instanceof IpChange) {
            Object[] objArray = new Object[]{this};
            $ipChange.ipc$dispatch("b476aaef", objArray);
            return;
        }else if(this.isAddInterfaceListen.getAndSet(i)){
            NetWorkStatusUtil.b(this.NetworkStatusChangeListener);
        }
        return;
    }

}
