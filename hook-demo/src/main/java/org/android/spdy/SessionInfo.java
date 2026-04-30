package org.android.spdy;

import java.util.ArrayList;
import java.util.Collections;

public class SessionInfo {

    private String certHost;
    private int cong_control;
    private int connTimeoutMs;
    private int connectIndex;
    private String domain;
    private String host;
    private int mode;
    private boolean multiPathCompensateEnable;
    private boolean multiPathParallelAddSpeedEnable;
    private int port;
    private String proxyHost;
    private int proxyPort;
    private int pubkey_seqnum;
    private int recvRateBps;
    private SessionCb sessionCb;
    private SessionCustomExtraCb sessionCustomExtraCb;
    private Object sessionUserData;
    private int setMSS;
    private boolean tryForceCellular;
    private String tunnelDatagramDomain;
    private String tunnelDomain;
    private String tunnelInfo;
    private ArrayList tunnelInfoArrayList;
    private int tunnelRetryTimes;
    private static int INVALID_PUBLIC_SEQNUM;

    public SessionInfo(String p0,int p1,String p2,String p3,int p4,Object p5,SessionCb p6,int p7){
        this.certHost = null;
        this.connectIndex = 0;
        this.tryForceCellular = false;
        this.tunnelInfoArrayList = null;
        this.tunnelInfo = null;
        this.tunnelDomain = "test.xquic.com";
        this.tunnelDatagramDomain = "tunnel.datagram.com";
        this.multiPathCompensateEnable = false;
        this.multiPathParallelAddSpeedEnable = false;
        this.host = p0;
        this.port = p1;
        this.domain = p2;
        this.proxyHost = p3;
        this.proxyPort = p4;
        this.sessionUserData = p5;
        this.sessionCb = p6;
        this.mode = p7;
        this.pubkey_seqnum = -1;
        this.connTimeoutMs = -1;
        this.cong_control = 0;
    }
    public String getAuthority(){
        String str = ":";
        if (this.proxyHost != null && this.proxyPort != 0) {
            return this.host+str+this.port+"/"+this.proxyHost+str+this.proxyPort;
        }
        return this.host+str+this.port;
    }
    public String getCertHost(){
        if(this.pubkey_seqnum != -1){
            return null;
        }else {
            return this.certHost;
        }
    }
    public int getConnectIndex(){
        return this.connectIndex;
    }
    public int getConnectionTimeoutMs(){
        return this.connTimeoutMs;
    }
    public String getDomain(){
        return this.domain;
    }
    public int getMode(){
        return this.mode;
    }
    public int getMss(){
        return this.setMSS;
    }
    public boolean getMultiPathCompensateEnable(){
        return this.multiPathCompensateEnable;
    }
    public boolean getMultiPathParallelAddSpeedEnable(){
        return this.multiPathParallelAddSpeedEnable;
    }
    public int getPubKeySeqNum(){
        return this.pubkey_seqnum;
    }
    public int getRecvRateBps(){
        return this.recvRateBps;
    }
    public SessionCb getSessionCb(){
        return this.sessionCb;
    }
    public SessionCustomExtraCb getSessionCustomExtraCb(){
        return this.sessionCustomExtraCb;
    }
    public Object getSessonUserData(){
        return this.sessionUserData;
    }
    public String getTunnelDomain(){
        if(true){
            return "tunnel.datagram.com";
        }else {
            return "test.xquic.com";
        }
    }
    public ArrayList getTunnelStrategyList(){
        return this.tunnelInfoArrayList;
    }
    public int getXquicCongControl(){
        return this.cong_control;
    }
    public boolean isTryForceCellular(){
        return this.tryForceCellular;
    }
    public void setCertHost(String p0){
        this.certHost = p0;
    }
    public void setConnectIndex(int p0){
        this.connectIndex = p0;
    }
    public void setConnectionTimeoutMs(int p0){
        this.connTimeoutMs = p0;
    }
    public void setMss(int p0){
        if(p0 > 0){
            this.setMSS = p0;
        }
    }
    public SessionInfo setMultiPathCompensateEnable(boolean p0){
        this.multiPathCompensateEnable = p0;
        return this;
    }
    public SessionInfo setMultiPathParallelAddSpeedEnable(boolean p0){
        this.multiPathParallelAddSpeedEnable = p0;
        return this;
    }
    public void setPubKeySeqNum(int p0){
        this.pubkey_seqnum = p0;
    }
    public void setRecvRateBps(int p0){
        this.recvRateBps = p0;
    }
    public SessionInfo setSessionCustomExtraCb(SessionCustomExtraCb p0){
        if(p0 != null){
            this.sessionCustomExtraCb = p0;
        }
        return this;
    }
    public SessionInfo setTryForceCellular(boolean p0){
        this.tryForceCellular = p0;
        return this;
    }
    public void setTunnelInfos(ArrayList p0){
        if(p0 != null && p0 != Collections.EMPTY_LIST){
            this.tunnelInfoArrayList = new ArrayList(p0);
        }
        return;
    }
    public void setXquicCongControl(int p0){
        this.cong_control = p0;
    }
    public void setXquicLossDetect(boolean p0){
        return;
    }
    public void setXquicPacing(int p0){
        return;
    }


}
