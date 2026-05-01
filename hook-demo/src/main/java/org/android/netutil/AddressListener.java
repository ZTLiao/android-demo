package org.android.netutil;

public abstract class AddressListener extends NetListener {

    public AddressListener(){
        super(NetListenerType.NL_NEW_IP_ADDRESS);
    }
    public abstract void onNewAddress(String p0);
}
