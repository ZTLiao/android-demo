package org.android.spdy;

public class NetTimeGaurd {

    public static  int CREATE;
    public static  int ERROR;
    public static  int PING;
    public static  int STREAM;
    private static  long calltime;
    private static  long total;
    private static long[] totaltime;

    static {
        long[] olongArray = new long[4];
        NetTimeGaurd.totaltime = olongArray;
    }
    public static long begin(){
        long l = 0;
        if (SpdyAgent.enableTimeGaurd) {
            l = System.currentTimeMillis();
        }
        return l;
    }
    public static void end(String p0,int p1,long p2){
        if(SpdyAgent.enableTimeGaurd){
            long l = System.currentTimeMillis() - p2;
            long[] totaltime = NetTimeGaurd.totaltime;
            totaltime[p1] = totaltime[p1] + l;
            long l1 = 10;
            if ((l - l1) > 0) {
                throw new SpdyErrorException("CallBack:"+p0+" timeconsuming:"+l+"  mustlessthan:"+l1, -1);
            }
        }
        return;
    }
    public static void finish(int p0){
        if(SpdyAgent.enableTimeGaurd && (NetTimeGaurd.totaltime[p0] - 50) > 0){
            throw new SpdyErrorException("CallBack totaltimeconsuming:"+NetTimeGaurd.totaltime[p0]+"  mustlessthan:"+50, -1);
        }
    }
    public static void start(int p0){
        if(SpdyAgent.enableTimeGaurd){
            NetTimeGaurd.totaltime[p0] = 0;
        }
    }

}
