package com.example.demo.client;

import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

public class BindProxy implements IStudentInterface {

    String TAG = this.getClass().getSimpleName();

    public static final int REQUEST_CODE = 1000;

    public final IBinder mRemoteBinder;

    public BindProxy(IBinder mRemoteBinder) {
        this.mRemoteBinder = mRemoteBinder;
    }

    @Override
    public int getStudentAge(String name) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeString(name);
        try {
            if (mRemoteBinder == null) {
                return -1;
            }
            mRemoteBinder.transact(REQUEST_CODE, data, reply, 0);
            return reply.readInt();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return 0;
    }

    public static IStudentInterface asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        if (iBinder instanceof IStudentInterface) {
            return (IStudentInterface) iBinder;
        } else {
            return new BindProxy(iBinder);
        }
    }

}
