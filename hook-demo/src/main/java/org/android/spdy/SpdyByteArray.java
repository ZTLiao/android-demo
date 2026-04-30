package org.android.spdy;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class SpdyByteArray implements Comparable<SpdyByteArray> {

    private byte[] byteArray;
    public int dataLength;
    private boolean isDirectBuffer;
    public int length;
    private ByteBuffer mByteBuffer;

    public SpdyByteArray(){
        this.mByteBuffer = null;
        this.isDirectBuffer = false;
        this.byteArray = null;
        this.length = 0;
        this.dataLength = 0;
    }
    public SpdyByteArray(int p0){
        this.mByteBuffer = null;
        this.isDirectBuffer = false;
        byte[] uobyteArray = new byte[p0];
        this.byteArray = uobyteArray;
        this.length = p0;
        this.dataLength = 0;
    }
    public int compareTo(SpdyByteArray p0){
        int tlength = this.length;
        int length = p0.length;
        if (tlength != length) {
            return (tlength - length);
        }
        if (this.byteArray == null) {
            return -1;
        }
        if (p0.byteArray == null) {
            return 1;
        }
        return (this.hashCode() - p0.hashCode());
    }
    public byte[] getByteArray(){
        return this.byteArray;
    }
    public ByteBuffer getByteBuffer(){
        return this.mByteBuffer;
    }
    public int getDataLength(){
        return this.dataLength;
    }
    public boolean isUseDirectBuffer(){
        return this.isDirectBuffer;
    }
    public void recycle(){
        int i = 0;
        Arrays.fill(this.byteArray, (byte) 0);
        this.dataLength = i;
        this.mByteBuffer = null;
        SpdyBytePool.getInstance().recycle(this);
    }
    public void setByteArrayDataLength(int p0){
        this.dataLength = p0;
    }
    public void setDirectBufferMode(boolean p0){
        this.isDirectBuffer = p0;
    }
    public void setDirectByteBuffer(ByteBuffer p0){
        this.mByteBuffer = p0;
    }

}
