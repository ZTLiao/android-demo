package tb;

import com.taobao.android.remoteso.api.RSoException;

public class ihw {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private final RSoException f;

    private ihw(String p0, String p1, RSoException p2) {
        super();
        this.c = null;
        this.d = null;
        this.e = "init";
        this.a = p0;
        this.b = p1;
        this.f = p2;
    }

    public static ihw a(String p0, int p1) {
        return new ihw(p0, null, RSoException.error(p1));
    }

    public static ihw a(String p0, RSoException p1) {
        return new ihw(p0, null, p1);
    }

    public static ihw a(String p0, String p1) {
        return new ihw(p0, p1, null);
    }

    public String a() {
        return this.a;
    }

    public ihw a(String p0) {
        this.e = p0;
        return this;
    }

    public String b() {
        return this.b;
    }

    public ihw b(String p0, String p1) {
        this.c = p0;
        this.d = p1;
        return this;
    }

    public String c() {
        return this.c;
    }

    public String d() {
        return this.d;
    }

    public String e() {
        return this.e;
    }

    public RSoException f() {
        return this.f;
    }

    public boolean g() {
        String tb;
        int i = 0;
        if ((tb = this.b) != null && (!tb.isEmpty() && this.f == null)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "FetchResult{libName=\'" + this.a + "'" + ", libFullPath=\'" + this.b + "'" + ", majorVersion=\'" + this.c + "'" + ", minorVersion=\'" + this.d + "'" + ", from=\'" + this.e + "'" + ", exception=" + this.f + '}';

    }
}
