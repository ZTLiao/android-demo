package com.example.demo.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.demo.aidl.IMyAidlInterface;

public class MyService extends Service {

    String TAG = this.getClass().getSimpleName();

    private IBinder mBinder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getStudentAge(String name) throws RemoteException {
            Student student = new Student();
            return student.getAge(name);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServerBinder();
    }
}
