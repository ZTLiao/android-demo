package org.android.netutil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PingTask$PingFuture extends AsyncTask implements Future {
    private PingResponse _inner_response;
    private long native_ptr;
    public PingTask this$0;

    private PingTask$PingFuture(PingTask p0){
        super();
        this.this$0 = p0;
        this.native_ptr = 0;
        this._inner_response = null;
    }
    public PingTask$PingFuture(PingTask p0,PingTask$1 p1){
        this(p0);
    }
    public static PingTask$PingFuture access$100(PingTask$PingFuture p0,String p1,int p2,int p3,int p4,int p5,PingTaskWatcher p6){
        return p0.start(p1, p2, p3, p4, p5, p6);
    }
    public static Object ipc$super(PingTask$PingFuture p0,String p1,Object[] p2){
        p1.hashCode();
        p2 = new Object[]{p1};
        throw new RuntimeException(String.format("String switch could not find \'%s\'", p2));
    }
    private PingTask$PingFuture start(String p0,int p1,int p2,int p3,int p4,PingTaskWatcher p5){
        this._inner_response = new PingResponse(p2);
        this._inner_response.a(p5);
        this.native_ptr = PingTask.access$400(this, p0, p1, p2, p3, p4);
        return this;
    }
    public boolean cancel(boolean p0){
        return false;
    }
    public void finalize() throws Throwable {
        super.finalize();
        long tnative_ptr = this.native_ptr;
        if (tnative_ptr != 0) {
            PingTask.access$200(tnative_ptr);
        }
    }
    public PingResponse get(){
        PingResponse pingResponse = null;
        long l = 0;
        try{
            pingResponse = this.get(l, TimeUnit.SECONDS);
        }catch(Exception e1){
            e1.printStackTrace();
        }
        return pingResponse;
    }
    public PingResponse get(long p0,TimeUnit p1) throws TimeoutException {
        synchronized (this) {
            if (!this.done) {
                if ((this.native_ptr) == 0) {
                    return null;
                }else if(PingTask.access$300(this.native_ptr, p1.toSeconds(p0))){
                    PingTask.access$200(this.native_ptr);
                    this.native_ptr = 0;
                }else {
                    throw new TimeoutException();
                }
            }
        }
        return this._inner_response;
    }
    public boolean isCancelled(){
        return false;
    }
    public boolean isDone(){
        return this.done;
    }
    public void onPingEntry(int p0,int p1,double p2){
        this._inner_response.a(p0, p1, p2);
    }
    public void onTaskFinish(String p0,int p1){
        this._inner_response.a(p0);
        this._inner_response.a(p1);
    }
    public void onTimxceed(String p0){
        this._inner_response.b(p0);
    }

}
