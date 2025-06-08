package com.example.demo.server;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyStruct implements Parcelable {

    String TAG = this.getClass().getSimpleName();

    private int a1, a2, a3, a4;

    private String a5;

    public MyStruct(int a1, int a2, int a3, int a4, String a5) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
    }

    protected MyStruct(Parcel in) {
        readToParcel(in);
    }

    public static final Creator<MyStruct> CREATOR = new Creator<MyStruct>() {
        @Override
        public MyStruct createFromParcel(Parcel in) {
            return new MyStruct(in);
        }

        @Override
        public MyStruct[] newArray(int size) {
            return new MyStruct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        Log.i(TAG, "writeToParcel");
        parcel.writeInt(a1);
        parcel.writeInt(a2);
        parcel.writeInt(a3);
        parcel.writeInt(a4);
        parcel.writeString(a5);
    }

    public void readToParcel(@NonNull Parcel in) {
        Log.i(TAG, "writeToParcel");
        a1 = in.readInt();
        a2 = in.readInt();
        a3 = in.readInt();
        a4 = in.readInt();
        a5 = in.readString();
        Log.i(TAG, "a1 : " + a1 + ", a2 : " + a2 + ", a3 : " + a3 + ", a4 : " + a4 + ", a5 : " + a5);
    }

    @Override
    public String toString() {
        return "MyStruct{" +
                "a1=" + a1 +
                ", a2=" + a2 +
                ", a3=" + a3 +
                ", a4=" + a4 +
                ", a5='" + a5 + '\'' +
                '}';
    }
}
