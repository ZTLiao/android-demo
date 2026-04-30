package org.android.spdy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class SpdyBytePool {

    private ArrayList<Deque<SpdyByteArray>> bucks;
    private int POOL_SIZE;
    private static SpdyBytePool gInstance;
    private static Object lock;

    static {
        SpdyBytePool.lock = new Object();
        SpdyBytePool.gInstance = null;
    }
    private SpdyBytePool(){
        this.bucks = null;
        this.bucks = new ArrayList(16);
        for (int i = 0; i < 16; i = i + 1) {
            this.bucks.add(null);
        }
    }
    public static SpdyBytePool getInstance(){
        if(SpdyBytePool.gInstance == null){
            synchronized (SpdyBytePool.lock) {
                if (SpdyBytePool.gInstance == null) {
                    SpdyBytePool.gInstance = new SpdyBytePool();
                }
            }
        }
        return SpdyBytePool.gInstance;
    }
    public SpdyByteArray getSpdyByteArray(int p0){
        Deque<SpdyByteArray> uDeque;
        int i = 0;
        p0 = (p0 + 4095) & 0xf000;
        SpdyByteArray spdyByteArra = null;
        if (p0 > 0) {
            i = p0 >> 12;
        }
        if (i < 16) {
            synchronized (SpdyBytePool.lock) {
                if ((uDeque = this.bucks.get(i)) != null && uDeque.size() > 0) {
                    spdyByteArra = uDeque.pop();
                }
            }
        }
        if (spdyByteArra == null) {
            spdyByteArra = new SpdyByteArray(p0);
        }
        return spdyByteArra;
    }
    public void recycle(SpdyByteArray p0){
        Deque uDeque;
        int i = 0;
        if(p0.length > 0){
            i = p0.length >> 12;
        }
        int i1 = 16;
        if (i < i1) {
            Object lock = SpdyBytePool.lock;
            synchronized (SpdyBytePool.lock) {
                if ((uDeque = this.bucks.get(i)) == null) {
                    uDeque = new ArrayDeque(i1);
                    this.bucks.set(i, uDeque);
                }
                uDeque.push(p0);
            }
        }
    }


}
