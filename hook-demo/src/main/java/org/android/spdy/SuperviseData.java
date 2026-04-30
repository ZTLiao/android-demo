package org.android.spdy;

public class SuperviseData {

    public int bodyDeflatedType;
    public int bodySize;
    public int compressSize;
    public long connInFlight;
    private long connLastRdEventIdleTime;
    private long connRecvSize;
    private long connSendSize;
    public double defaultPathRecvWeight;
    public double defaultPathSendWeight;
    public long minRtt;
    public int originContentLength;
    public long rateLimit;
    public int recvBodySize;
    public int recvCompressSize;
    private int recvPacketCount;
    public int recvUncompressSize;
    public int reqMultipathStatus;
    public long requestStart;
    public long responseBodyStart;
    public long responseEnd;
    public long responseHeaderEnd;
    public long responseStart;
    public long rspDecompressionTime;
    public long sendEnd;
    private int sendPacketCount;
    public long sendStart;
    public SpdySession spdySession;
    public long srtt;
    public long streamFinRecvTime;
    public int streamId;
    private String streamInfo;
    public long streamPktRecvStart;
    public long streamPktSendStart;
    public long streamRecvStart;
    public long streamRecvTime;
    public int tunnel0RTTStatus;
    public int tunnelDegraded;
    public int tunnelErrorCode;
    public int tunnelRetryTimes;
    public int tunnelType;
    public int uncompressSize;
    public int unreliableChannelMss;
    public int waitConnectStat;

    public SuperviseData(){
        super();
        this.srtt = -1;
        this.minRtt = -1;
        this.connInFlight = -1;
        this.connSendSize = -1;
        this.connRecvSize = -1;
        this.recvPacketCount = -1;
        this.sendPacketCount = -1;
        this.connLastRdEventIdleTime = -1;
        this.tunnel0RTTStatus = -1;
        this.tunnelErrorCode = -1;
        this.tunnelDegraded = -1;
        this.tunnelRetryTimes = -1;
        this.tunnelType = 0;
    }
    public String getConnInfo(){
        SpdySession tspdySession;
        String str = ""+"sendSize="+this.connSendSize+",recvSize="+this.connRecvSize+",sendPkt="+this.sendPacketCount+",recvPkt="+this.recvPacketCount+",lastRdIdleTime="+this.connLastRdEventIdleTime+",srtt="+this.srtt+",minRtt="+this.minRtt+",inFlight="+this.connInFlight;
        if ((tspdySession = this.spdySession) != null && tspdySession.isTunnel()) {
            str = str+",tlType="+this.tunnelType+",tl0RTTStatus="+this.tunnel0RTTStatus+",tlErrorCode="+this.tunnelErrorCode+",tlDegraded="+this.tunnelDegraded+",tlRetryTimes="+this.tunnelRetryTimes;
        }
        return str;
    }
    public String getExternStat(){
        return new StringBuilder(64)+"qcStat="+SoInstallMgrSdk.loadQuicStat+",qcTime="+SoInstallMgrSdk.getFetchQuicTime()+",zstdRso="+SoInstallMgrSdk.loadZstdStat+",zstdTime="+SoInstallMgrSdk.getFetchZstdTime()+",network="+NetWorkStatusUtil.g()+","+NetWorkStatusUtil.d()+",streamInfo="+this.streamInfo;
    }
    public String superviseDataToString(){
        StringBuilder str = new StringBuilder("tnetProcessTime="+(this.sendStart - this.requestStart)+",sendCostTime="+(this.sendEnd - this.sendStart)+",firstDateTime="+(this.responseStart - this.sendEnd)+",recvHeaderTime="+(this.responseHeaderEnd - this.responseStart)+",recvBodyTime="+(this.responseEnd - this.responseBodyStart)+",reqEnd2BeginTime="+(this.streamFinRecvTime - this.requestStart)+",reqHeadSize="+this.uncompressSize+",reqHeadCompressSize="+this.compressSize+",reqBodySize="+this.bodySize+",rspHeadCompressSize="+this.recvCompressSize+",rspHeadSize="+this.recvUncompressSize+",recvBodyCompressSize="+this.recvBodySize+",contentLength="+this.originContentLength+",bodyDeflatedType="+this.bodyDeflatedType+",streamId="+this.streamId+",reqMpStatus="+this.reqMultipathStatus+",rateLimit="+this.rateLimit);
        if (this.spdySession != null) {
            if (((this.spdySession.getMode() & 0x0100)) != 0 && ((this.spdySession.getMode() & 0x10)) != 0) {
                str = str.append(",mss="+this.spdySession.unreliableChannelMss);
            }
        }
        return str+",sendWt="+this.defaultPathSendWeight+",recvWt="+this.defaultPathRecvWeight+",exStat="+this.getExternStat()+",connInfo=["+this.getConnInfo()+"]";
    }

}
