package org.android.spdy;

public class SpdyErrorException extends RuntimeException {

    private int error;

    public SpdyErrorException(int p0){
        super();
        this.error = 0;
        this.error = p0;
    }
    public SpdyErrorException(String p0,int p1){
        super(p0);
        this.error = 0;
        this.error = p1;
    }
    public SpdyErrorException(String p0,Throwable p1,int p2){
        super(p0, p1);
        this.error = 0;
        this.error = p2;
    }
    public SpdyErrorException(Throwable p0,int p1){
        super(p0);
        this.error = 0;
        this.error = p1;
    }
    public int SpdyErrorGetCode(){
        return this.error;
    }
}
