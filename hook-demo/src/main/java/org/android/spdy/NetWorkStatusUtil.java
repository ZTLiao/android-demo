package org.android.spdy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.ParcelFileDescriptor;

import java.util.concurrent.CopyOnWriteArraySet;

public class NetWorkStatusUtil {

    public static Network a;
    public static Network b;
    public static boolean c;
    public static int d;
    public static String e;
    public static InterfaceStatus f;
    public static CopyOnWriteArraySet g;
    private static StringBuilder h;
    private static ConnectivityManager i;

    static {
        NetWorkStatusUtil.f = InterfaceStatus.ACTIVE_INTERFACE_NONE;
        NetWorkStatusUtil.g = new CopyOnWriteArraySet();
    }

    public static int a(int p0) {
        String str = "";
        String str1 = "tnetsdk.NetWorkStatusUtil";
        int i = 2;
        try {
            int i1 = 4;
            int i2 = 3;
            if (NetWorkStatusUtil.a != null) {
                NetWorkStatusUtil.a.bindSocket(ParcelFileDescriptor.fromFd(p0).getFileDescriptor());
                return 0;
            } else {
                return -1;
            }
        } catch (java.lang.Throwable e10) {
            e10.printStackTrace();
            return -1;
        }
    }

    public static void a(Context p0) {
        int i1;
        int i2;
        int i = 0;
        try {
            i1 = 3;
            i2 = 4;
            if (p0 != null && NetWorkStatusUtil.c()) {
                NetWorkStatusUtil.b(p0);
                NetWorkStatusUtil.c(p0);
                NetWorkStatusUtil.c = true;
                NetWorkStatusUtil.d = i;
            } else if (p0 == null) {
                NetWorkStatusUtil.d = 1;
            } else if (!NetWorkStatusUtil.e()) {
                NetWorkStatusUtil.d = i2;
            }
        } catch (java.lang.Exception e9) {
            e9.printStackTrace();
            NetWorkStatusUtil.e = e9.getMessage();
            NetWorkStatusUtil.d = 5;
        }
    }

    public static void a(InterfaceStatus p0, boolean p1) {
        Object[] objArray;
        int interfaceSta1;
        int interfaceSta = NetWorkStatusUtil.f.getInterfaceStatus();
        if (p0 == InterfaceStatus.ACTIVE_INTERFACE_WIFI) {
            if (p1) {
                interfaceSta1 = InterfaceStatus.ACTIVE_INTERFACE_WIFI.getInterfaceStatus();
                interfaceSta = interfaceSta | interfaceSta1;
            } else {
                interfaceSta1 = InterfaceStatus.ACTIVE_INTERFACE_WIFI.getInterfaceStatus();
                interfaceSta = interfaceSta & (~interfaceSta1);
            }
        } else if (p0 == InterfaceStatus.ACTIVE_INTERFACE_CELLULAR) {
            if (p1) {
                interfaceSta1 = InterfaceStatus.ACTIVE_INTERFACE_CELLULAR.getInterfaceStatus();
                interfaceSta = interfaceSta | interfaceSta1;
            } else {
                interfaceSta1 = InterfaceStatus.ACTIVE_INTERFACE_CELLULAR.getInterfaceStatus();
                interfaceSta = interfaceSta & (~interfaceSta1);
            }
        }
        if (NetWorkStatusUtil.f.getInterfaceStatus() != interfaceSta) {
            NetWorkStatusUtil.f.setInterfaceStatus(interfaceSta);
        }
    }

    public static void a(NetWorkStatusUtil$a p0) {
        try {
            NetWorkStatusUtil.g.add(p0);
        } catch (java.lang.Throwable e0) {
        }
    }

    public static boolean a() {
        try {
            //TODO check wifi
            return true;
        } catch (java.lang.Throwable e0) {
        }
        return false;
    }

    public static int b(int p0) {
        Object[] objArray1;
        int i = 1;
        int i1 = 0;
        int i2 = 4;
        if (NetWorkStatusUtil.b != null && (p0 > 0)) {
            try {
                NetWorkStatusUtil.b.bindSocket(ParcelFileDescriptor.fromFd(p0).getFileDescriptor());
                return i1;
            } catch (java.lang.Throwable e11) {
                e11.printStackTrace();
                return 0;
            }
        } else {
            return -1;
        }
    }

    public static void b(Context p0) {
        int i = 0;
        if (NetWorkStatusUtil.i == null) {
            NetWorkStatusUtil.i = (ConnectivityManager) p0.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkRequest.Builder uBuilder = new NetworkRequest.Builder();
        uBuilder.addTransportType(i).addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        NetWorkStatusUtil.i.requestNetwork(uBuilder.build(), new NetWorkStatusUtil$1());
    }

    public static void b(NetWorkStatusUtil$a p0) {
        try {
            NetWorkStatusUtil.g.remove(p0);
            return;
        } catch (java.lang.Throwable e0) {
        }
    }

    public static boolean b() {
        try {
            //TODO check mobile
            return true;
        } catch (java.lang.Throwable e0) {
        }
        return false;
    }

    public static void c(Context p0) {
        int i = 0;
        if (NetWorkStatusUtil.i == null) {
            NetWorkStatusUtil.i = (ConnectivityManager) p0.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkRequest.Builder uBuilder = new NetworkRequest.Builder();
        uBuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI).addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        NetWorkStatusUtil.i.requestNetwork(uBuilder.build(), new NetWorkStatusUtil$2());
    }

    public static boolean c() {
        if (NetWorkStatusUtil.e()) {
            return true;
        } else {
            return false;
        }
    }

    public static String d() {
        StringBuilder h;
        if (NetWorkStatusUtil.h == null) {
            h = new StringBuilder(32);
            NetWorkStatusUtil.h = h;
            if ((NetWorkStatusUtil.h = new StringBuilder(h + "err=" + NetWorkStatusUtil.d + ",level=" + Build.VERSION.SDK_INT)) != null) {
                NetWorkStatusUtil.h = new StringBuilder(NetWorkStatusUtil.h + ",exMsg=" + NetWorkStatusUtil.e);
            }
        }
        if ((h = NetWorkStatusUtil.h) != null) {
            return h.toString();
        } else {
            return "null";
        }
    }

    public static boolean e() {
        return true;
    }

    public static boolean f() {
        if (NetWorkStatusUtil.f == NetWorkStatusUtil.InterfaceStatus.ACTIVE_INTERFACE_MULTI) {
            return true;
        } else {
            return false;
        }
    }

    public static int g() {
        int i = 0;
        if (NetWorkStatusUtil.c) {
            i = 1;
        }
        if (NetWorkStatusUtil.b != null) {
            i = i | 0x02;
        }
        if (NetWorkStatusUtil.a != null) {
            i = i | 0x04;
        }
        return i;
    }

    public enum InterfaceStatus {
        ACTIVE_INTERFACE_NONE(0),

        ACTIVE_INTERFACE_CELLULAR(1),

        ACTIVE_INTERFACE_WIFI(2),

        ACTIVE_INTERFACE_MULTI(3),
        ;

        private int interfaceStatus;

        InterfaceStatus(int p2) {
            this.interfaceStatus = p2;
        }

        int getInterfaceStatus() {
            return this.interfaceStatus;
        }

        void setInterfaceStatus(int p0) {
            this.interfaceStatus = p0;

        }
    }
}