package org.android.netutil;

public interface PingTaskWatcher {
    void OnEntry(int p0,int p1,double p2);
    void OnFailed(int p0);
    void OnFinished();
}
