package tb;

import java.util.Iterator;

public class sab$2 implements Runnable {
    public boolean a;

    public sab$2(boolean p0){
        this.a = p0;
    }
    public void run(){
        Iterator<sab$a> iterator = sab.e().iterator();
        while (iterator.hasNext()) {
            sab$a uoa = iterator.next();
            try{
                if (this.a) {
                    uoa.a();
                }else {
                    uoa.b();
                }
            }catch(java.lang.Exception e0){
            }
        }
    }

}
