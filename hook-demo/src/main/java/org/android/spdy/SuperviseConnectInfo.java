package org.android.spdy;

public class SuperviseConnectInfo {
    public int congControlKind;
    public long connLastRdEventIdleTime;
    public long connRecvSize;
    public long connSendSize;
    public int connectTime;
    public int currStrategySeq;
    public int currStrategyStatus;
    public String dcid;
    public double defaultPathRecvWeight;
    public double defaultPathSendWeight;
    public int doHandshakeTime;
    public int handshakeTime;
    public int isForceCellular;
    public int isQuicTry0RTT;
    public int keepalive_period_second;
    public double lossRate;
    public String mpquicPathInfo;
    public int mpquicStatus;
    public int multiNetStatus;
    public int recvCount;
    public int recvPacketCount;
    public int recvPacketMax;
    public double retransmissionRate;
    public int retryTimes;
    public int reused_counter;
    public int rtoCount;
    public String scid;
    public int sendCount;
    public int sendPacketCount;
    public int sessionTicketReused;
    public long srtt;
    public int timeout;
    public int tlpCount;
    public int tunnel0RTTStatus;
    public int tunnelConnectTime;
    public String tunnelDcid;
    public int tunnelDegraded;
    public int tunnelErrorCode;
    public StrategyInfo tunnelInfo;
    public double tunnelLossRate;
    public double tunnelRetransmissionRate;
    public int tunnelRetryTimes;
    public String tunnelScid;
    public long tunnelSrtt;
    public int tunnelType;
    public int xqc0RttStatus;
    public static int tunnelInitValue;

    public void SuperviseConnectInfo() {
        this.tunnelInfo = null;
        this.defaultPathSendWeight = 1.00f;
        this.defaultPathRecvWeight = 1.00f;
        this.tunnel0RTTStatus = -1;
        this.tunnelErrorCode = -1;
        this.tunnelDegraded = -1;
        this.tunnelRetryTimes = -1;
        this.tunnelType = 0;
    }

    public StrategyInfo getCurrStrategyInfo() {
        return this.tunnelInfo;
    }
}