package org.android.netutil;

public enum NetListenerType {

    NL_NULL(-1L),
    NL_NEW_IP_ADDRESS(0L),
    NL_INTERFACE_ON_OFF(1L);
    

    private long value;
    
    NetListenerType(long p2){
        this.value = p2;
    }
    
    long getValue(){
        return this.value;
    }
}
