package tb;

import java.util.concurrent.ThreadFactory;

public class sae$a implements ThreadFactory {

    private String a;

    public sae$a(String p0){
        this.a = p0;
    }
    public Thread newThread(Runnable p0){
        Thread thread = new Thread(p0, this.a+sae.b().getAndIncrement());
        thread.setPriority(5);
        return thread;
    }
}
