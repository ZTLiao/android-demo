package tb;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class sab$1 implements Application.ActivityLifecycleCallbacks {
    private int a;

    public sab$1(){
        this.a = 0;
    }
    public void onActivityCreated(Activity p0,Bundle p1){
        return;
    }
    public void onActivityDestroyed(Activity p0){
        return;
    }
    public void onActivityPaused(Activity p0){
        return;
    }
    public void onActivityResumed(Activity p0){
        return;
    }
    public void onActivitySaveInstanceState(Activity p0,Bundle p1){
        return;
    }
    public void onActivityStarted(Activity p0){
        this.a = this.a + 1;
        if (sab.a()) {
            sab.c();
        }
    }
    public void onActivityStopped(Activity p0){
        this.a = this.a - 1;
        if (this.a <= 0 && !sab.a()) {
            this.a = 0;
            sab.d();
        }
    }

}
