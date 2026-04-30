package org.android.spdy;

public class SpdySession$2 implements NetWorkStatusUtil$a {

    public SpdySession a;

    public SpdySession$2(SpdySession p0){
        this.a = p0;
    }

    @Override
    public void a(NetWorkStatusUtil.InterfaceStatus p0, boolean p1) {
        int i2;
        if(p0 == NetWorkStatusUtil.InterfaceStatus.ACTIVE_INTERFACE_CELLULAR && p1){
            return;
        }else {
            int i = p0.getInterfaceStatus() << 16;
            if (p1) {
                i = i | 0x01;
            }
            int i1 = 4;
            try{
                i2 = this.a.setOption(i1, i);
            }catch(org.android.spdy.SpdyErrorException e6){
                i2 = e6.SpdyErrorGetCode();
            }catch(java.lang.Exception e0){
                i2 = 0;
            }
        }
    }
}
