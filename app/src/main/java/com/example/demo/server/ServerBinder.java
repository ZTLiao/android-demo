package com.example.demo.server;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServerBinder extends Binder implements IStudentInterface {

    public static final int REQUEST_CODE = 1000;

    @Override
    public int getStudentAge(String name) {
        Student student = new Student();
        return student.getAge(name);
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        if (code == REQUEST_CODE) {
            String studentName = data.readString();
            int age = getStudentAge(studentName);
            if (reply != null) {
                reply.writeInt(age);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }
}
