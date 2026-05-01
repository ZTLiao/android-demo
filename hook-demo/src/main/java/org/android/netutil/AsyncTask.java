package org.android.netutil;

public class AsyncTask {

    public boolean done;

    public AsyncTask(){
        super();
    }
    public void onTaskFinish(){
        int i = 1;
        this.done = true;
    }
}
