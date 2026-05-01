package org.android.spdy;

import android.content.Context;

import java.lang.reflect.Method;

public class SecurityGuardCacherImp implements QuicCacher {

    private Class IDynamicDataStoreComponent;
    private Class SecurityGuardManager;
    private Object ddsComp;
    private Method getDynamicDataStoreComp;
    private Method getInstance;
    private Method getStringDDpEx;
    private boolean init_ok;
    private Method putStringDDpEx;
    private Method removeStringDDpEx;
    private Object sgMgr;
    public static int XQC_SESSION_CACHE;
    public static int XQC_TOKEN_CACHE;
    public static int XQC_TRANS_PARA_CACHE;

    public SecurityGuardCacherImp(){
        this.init_ok = false;
    }

    @Override
    public synchronized void init(Context p0) {
        if (this.init_ok) {
            return;
        }
        try{
//            this.SecurityGuardManager = Class.forName("com.taobao.wireless.security.sdk.SecurityGuardManager");
//            Class[] uClassArray = new Class[]{Context.class};
//            this.getInstance = this.SecurityGuardManager.getDeclaredMethod("getInstance", uClassArray);
//            Object[] objArray = new Object[]{p0};
//            this.sgMgr = this.getInstance.invoke(null, objArray);
//            if (this.sgMgr == null) {
//                return;
//            }
//            Class[] uClassArray1 = new Class[0];
//            this.getDynamicDataStoreComp = this.SecurityGuardManager.getDeclaredMethod("getDynamicDataStoreComp", uClassArray1);
//            Object[] objArray1 = new Object[0];
//            this.ddsComp = this.getDynamicDataStoreComp.invoke(this.sgMgr, objArray1);
//            if (this.ddsComp == null) {
//                return;
//            }
//            this.IDynamicDataStoreComponent = Class.forName("com.taobao.wireless.security.sdk.dynamicdatastore.IDynamicDataStoreComponent");
//            uClassArray1 = new Class[]{String.class,String.class,Integer.TYPE};
//            this.putStringDDpEx = this.IDynamicDataStoreComponent.getDeclaredMethod("putStringDDpEx", uClassArray1);
//            uClassArray1 = new Class[]{String.class,Integer.TYPE};
//            this.getStringDDpEx = this.IDynamicDataStoreComponent.getDeclaredMethod("getStringDDpEx", uClassArray1);
//            uClassArray1 = new Class[]{String.class,Integer.TYPE};
//            this.removeStringDDpEx = this.IDynamicDataStoreComponent.getDeclaredMethod("removeStringDDpEx", uClassArray1);
            this.init_ok = true;
            return;
        }catch(java.lang.Exception e7){
            e7.printStackTrace();
            return;
        }

    }

    @Override
    public byte[] load(String p0) {
        if (!this.init_ok) {
            return null;
        }
        try{
            Object[] objArray = new Object[]{p0,Integer.valueOf(0)};
            if ((p0 = (String) this.getStringDDpEx.invoke(this.ddsComp, objArray)) != null) {
                return p0.getBytes("ISO-8859-1");
            }
        }catch(java.lang.Exception e6){
            e6.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String p0){
        if (!this.init_ok) {
            return;
        }
        try{
            Object[] objArray = new Object[]{p0, 0};
            this.removeStringDDpEx.invoke(this.ddsComp, objArray);
            return;
        }catch(java.lang.Exception e5){
            e5.printStackTrace();
            return;
        }
    }

    @Override
    public boolean store(String p0,String p1){
        if (!this.init_ok) {
            return false;
        }
        try{
            Object[] objArray = new Object[]{p0,p1, 0};
            this.putStringDDpEx.invoke(this.ddsComp, objArray);
            return true;
        }catch(java.lang.Exception e6){
            e6.printStackTrace();
            return false;
        }
    }

}
