package org.android.netutil;

public class PingResponse {

    private String a;
    private String b;
    private int c;
    private int d;
    private PingEntry[] e;
    private PingTaskWatcher f;

    public PingResponse(int p0){
        this.a = null;
        this.b = null;
        int i = 0;
        this.c = i;
        this.d = i;
        this.e = null;
        this.f = null;
        PingEntry[] pingEntryArr = new PingEntry[p0];
        this.e = pingEntryArr;
        for (; i < p0; i = i + 1) {
            this.e[i] = new PingEntry();
        }
        return;
    }
    public String a(){
        return this.a;
    }
    public void a(int p0){
        PingTaskWatcher tf;
        this.c = p0;
        if ((tf = this.f) != null) {
            if (p0 == 0) {
                tf.OnFinished();
                return;
            }else {
                tf.OnFailed(p0);
            }
        }
    }
    public void a(int p0,int p1,double p2){
        PingTaskWatcher tf;
        int i = 1;
        this.e[p0].a(p0, p1, p2);
        if ((0 - p2) >= 0) {
            this.d = this.d + i;
        }
        if ((tf = this.f) != null) {
            tf.OnEntry(p0, p1, p2);
        }
    }
    public void a(String p0){
        this.a = p0;
    }
    public void a(PingTaskWatcher p0){
        this.f = p0;
    }
    public String b(){
        return this.b;
    }
    public void b(String p0){
        this.b = p0;
    }
    public int c(){
        return this.c;
    }
    public PingEntry[] d(){
        return this.e;
    }
    public int e(){
        return this.d;
    }

}
