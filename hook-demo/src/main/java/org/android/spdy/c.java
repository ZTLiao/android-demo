package org.android.spdy;

import android.util.LruCache;

import java.nio.ByteBuffer;

public class c {

    private final LruCache<ByteBuffer, String> a;
    private static c b;

    static {
        c.b = new c();
    }
    private c(){
        this.a = new LruCache<>(128);
    }
    public static c a(){
        return c.b;
    }
    public String a(ByteBuffer p0){
        String str;
        if((str = this.a.get(p0)) != null){
            return str;
        }else {
            int i = p0.limit();
            int i1 = p0.position();
            try{
                i = i - i1;
                str = new String(p0.array(), p0.position(), i, "utf-8");
            }catch(java.lang.Exception e1){
                e1.printStackTrace();
            }
            this.a.put(p0, str);
            return str;
        }
    }
}
