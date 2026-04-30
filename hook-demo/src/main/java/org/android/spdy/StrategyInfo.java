package org.android.spdy;

public class StrategyInfo {
    private int seq;
    private StrategyStatus status;
    private String tunnelHost;
    private int tunnelPort;

    public void StrategyInfo(String p0, int p1, int p2) {
        this.status = StrategyStatus.UNUSED;
        this.tunnelPort = 443;
        this.tunnelHost = p0;
        if (p1 > 0) {
            this.tunnelPort = p1;
        }
        if (p2 < 0) {
            p2 = -p2;
        }
        this.seq = p2;
        return;
    }

    public void StrategyInfo(StrategyInfo p0) {
        this.status = StrategyStatus.UNUSED;
        this.tunnelPort = 443;
        if (p0 != null) {
            this.tunnelHost = p0.tunnelHost;
            this.tunnelPort = p0.tunnelPort;
            this.seq = p0.seq;
            this.status = p0.status;
        }
        return;
    }

    public String getTunnelStrategyHost() {
        return this.tunnelHost;
    }

    public int getTunnelStrategyPort() {
        return this.tunnelPort;
    }

    public int getTunnelStrategySeq() {
        return this.seq;
    }

    public StrategyStatus getTunnelStrategyStatus() {
        return this.status;
    }

    public String infoToString() {
        return this.tunnelHost + "_" + this.tunnelPort + "_" + this.seq;
    }

    public void setStrategyStatus(int p0) {
        this.status.setStrategyStatus(p0);
    }

    public enum StrategyStatus {

        UNUSED("UNUSED", 0, 0),
        SUCCESS("SUCCESS", 1, 1),
        FAIL("FAIL", 2, 2),
        ;

        private int strategyStatus;

        StrategyStatus(String p0, int p1, int p2) {
            this.strategyStatus = p2;
        }

        public static StrategyStatus valueOf(int p0) {
            if (p0 == 1) {
                return StrategyStatus.SUCCESS;
            }
            if (p0 != 2) {
                return StrategyStatus.UNUSED;
            }
            return StrategyStatus.FAIL;
        }

        public int getStrategyStatusInt() {
            return this.strategyStatus;
        }

        public void setStrategyStatus(int p0) {
            this.strategyStatus = p0;
        }
    }
}
