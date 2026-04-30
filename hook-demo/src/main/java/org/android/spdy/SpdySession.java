package org.android.spdy;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class SpdySession {

    private sab$a LifecycleListener;
    private NetWorkStatusUtil$a NetworkStatusChangeListener;
    private SpdyAgent agent;
    private String authority;
    private String certHost;
    private AtomicBoolean closed;
    public long connectTaskStartTime;
    public SessionCustomExtraCb customExtraCb;
    private String domain;
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
    private d pptr4sessionNativePtr;
    private int pubkey_seqnum;
    public int refcount;
    private String seq;
    public SessionCb sessionCallBack;
    private AtomicBoolean sessionClearedFromSessionMgr;
    private long sessionNativePtr;
    private int sessionParameter;
    private String sessionPoolUniqueKey;
    private NetSparseArray spdyStream;
    private int streamcount;
    private String tunnelHost;
    private ArrayList tunnelInfoArrayList;
    public int unreliableChannelMss;
    private Object userData;
    private static String APPKEY_SPLIT;
    public static int CUSTOM_FRAME_QUIC_DGRAM_UNRELIABLE_CHANNEL_TYPE;
    public static int CUSTOM_FRAME_QUIC_RELIABLE_CHANNEL_TYPE;
    private static int DEFAULE_UNRELIABLECHANNEL_MSS;
    public static int LIFECYCLE_CHANGED;
    public static int NETWORK_CHANGED;
    private static int P_CONNECT_QUIC_0RTT_FAST_TIMEOUT;
    private static int P_CONNECT_RECV_BUFFER_RESIZE_ENABLE;
    private static int P_MP_MIN_RTT;
    private static int P_REQUEST_LIMIT_SPEED_ENABLE;
    private static String SCHEME_SPLIT;
    private static String TAG;

    public void SpdySession(long p0,SpdyAgent p1,String p2,String p3,String p4,String p5,SessionCb p6,SessionCustomExtraCb p7,int p8,int p9,Object p10,String p11,ArrayList p12,boolean p13,boolean p14,int p15){
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
        spdySession.pptr4sessionNativePtr = new d(this);
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
    public native int NotifyNotInvokeAnyMoreN(long p0);
    public native int sendCustomControlFrameN(long p0,int p1,int p2,int p3,int p4,int p5,int p6,byte[] p7);
    public native int sendHeadersN(long p0,int p1,String[] p2,boolean p3);
    public native int setOptionN(long p0,int p1,int p2);
    public native int streamCloseN(long p0,int p1,int p2);
    public native int streamSendDataN(long p0,int p1,byte[] p2,int p3,int p4,boolean p5);
    public native int submitBioPingN(long p0);
    public native int submitPingN(long p0);
    public native int submitRequestN(long p0,String p1,byte p2,String[] p3,byte[] p4,boolean p5,int p6,int p7,int p8,int p9,int p10);

    public boolean isTunnel(){
        return (this.mode & 0x0200) != 0;
    }

    public int getMode(){
        return this.mode;
    }
}
