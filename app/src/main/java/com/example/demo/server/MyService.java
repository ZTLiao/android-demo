package com.example.demo.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyService extends Service {

    public static final int REQUEST_CODE = 1000;

    private final Binder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            if (code == REQUEST_CODE) {
                String studentName = data.readString();
                int age = getStudentAge(studentName);
                if (reply != null) {
                    reply.writeInt(age);
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    public int getStudentAge(String name) {
        Student student = new Student();
        return student.getAge(name);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
