package tb;

import android.os.Build;
import android.text.TextUtils;

import org.android.spdy.SpdyAgent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class sad {

    private static boolean A;
    private static boolean B;
    private static boolean C;
    private static boolean D;
    public static  boolean DEFAULT_AGENT_FREE_ENABLE;
    public static  long DEFAULT_BODY_READ_TIMEOUT_MS;
    public static  boolean DEFAULT_CHANNEL_MEM_OPT_ENABLE;
    public static  boolean DEFAULT_HTTP_ZSTD_ENCODE_ENABLE;
    public static  String DEFAULT_MPQUIC_ADD_SPEED_WHITE_LIST;
    public static  boolean DEFAULT_MPQUIC_ENABLE;
    public static  long DEFAULT_QUIC_CONNECT_TIMEOUT_MS;
    public static  boolean DEFAULT_QUIC_SO_PLUGIN_LOAD_ENABLE;
    public static  boolean DEFAULT_REQUEST_READ_IDLE_TIMEOUT_ENABLE;
    public static  long DEFAULT_REQUEST_READ_TIMEOUT_MS;
    public static  long DEFAULT_TCP_CONNECT_TIMEOUT_MS;
    public static  boolean DEFAULT_TLOG_ENABLE;
    public static  long DEFAULT_TLOG_XQUIC_LEVEL;
    public static  boolean DEFAULT_TUNNEL_DATAGRAM_ENABLE;
    public static  boolean DEFAULT_TUNNEL_PROXY_ENABLE;
    private static boolean E;
    private static boolean F;
    private static CopyOnWriteArrayList G;
    private static boolean H;
    private static boolean I;
    private static boolean J;
    private static boolean K;
    private static boolean L;
    private static boolean M;
    private static boolean N;
    private static boolean O;
    private static boolean P;
    private static boolean Q;
    private static CopyOnWriteArrayList R;
    public static  String REQUEST_READ_TIMEOUT_TYPE_FAST;
    public static  String REQUEST_READ_TIMEOUT_TYPE_NORMAL;
    private static CopyOnWriteArrayList S;
    private static String T;
    private static String U;
    private static boolean V;
    private static boolean W;
    private static int X;
    private static CopyOnWriteArrayList Y;
    private static CopyOnWriteArrayList Z;
    private static List a;
    private static boolean aa;
    private static boolean b;
    private static boolean c;
    private static ConcurrentHashMap d;
    private static CopyOnWriteArrayList e;
    private static CopyOnWriteArrayList f;
    private static CopyOnWriteArrayList g;
    private static CopyOnWriteArrayList h;
    private static CopyOnWriteArrayList i;
    private static long j;
    private static long k;
    private static boolean l;
    private static boolean m;
    private static boolean n;
    private static long o;
    private static long p;
    private static ConcurrentHashMap q;
    private static long r;
    private static long s;
    private static ConcurrentHashMap t;
    private static boolean u;
    private static CopyOnWriteArrayList v;
    private static CopyOnWriteArrayList w;
    private static CopyOnWriteArrayList x;
    private static ConcurrentHashMap y;
    private static long z;

    static {
        sad.a = new ArrayList();
        sad.b = true;
        sad.c = true;
        sad.e = null;
        sad.f = null;
        sad.g = null;
        sad.i = null;
        sad.j = 5000;
        sad.k = 6000;
        sad.l = true;
        sad.n = true;
        sad.o = 2500;
        sad.p = 3000;
        sad.q = null;
        sad.r = 1500;
        sad.s = 3000;
        sad.t = null;
        sad.d = null;
        sad.u = true;
        sad.v = null;
        sad.w = null;
        sad.x = null;
        sad.y = null;
        sad.z = 0x240c8400;
        sad.A = true;
        sad.B = false;
        sad.C = true;
        sad.D = true;
        sad.E = true;
        sad.Q = true;
        sad.F = true;
        sad.G = null;
        sad.R = null;
        sad.S = null;
        sad.T = null;
        sad.U = null;
        sad.Y = null;
        sad.Z = null;
        sad.m = false;
        sad.h = null;
        sad.H = false;
        sad.I = false;
        sad.J = false;
        sad.K = false;
        sad.L = false;
        sad.M = false;
        sad.N = false;
        sad.O = false;
        sad.P = false;
        sad.V = false;
        sad.W = false;
        sad.aa = false;
    }
    public static void A(String p0){
        ConcurrentHashMap $ipChange1 = null;
        if(TextUtils.isEmpty(p0)){
            sad.d = null;
            return;
        }else {
            try{
                $ipChange1 = new ConcurrentHashMap();
                JSONObject jSONObject = new JSONObject(p0);
                Iterator<String> iterator = jSONObject.keys();
                try{
                    while (iterator.hasNext()) {
                        String str = iterator.next();
                        JSONArray obj = (JSONArray)jSONObject.get(str);
                        if (obj instanceof JSONArray) {
                            int i1 = obj.length();
                            ArrayList uArrayList = new ArrayList(i1);
                            int i2 = 0;
                            while (i2 < i1) {
                                Object obj1 = obj.get(i2);
                                if (obj1 instanceof String) {
                                    uArrayList.add(obj1);
                                }
                                i2 = i2 + 1;
                            }
                            if (!uArrayList.isEmpty()) {
                                $ipChange1.put(str, uArrayList);
                            }
                        }
                    }
                }catch(Exception e0){
                }
            }catch(Exception e12){
                sad.d = $ipChange1;
                return;
            }
        }
    }
    public static void A(boolean p0){
        sad.L = p0;
    }
    public static boolean A(){
        return sad.M;
    }
    public static void B(String p0){
        Object[] objArray;
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.h = uCopyOnWrite;
                return;
            }catch(Exception e6){
                return;
            }
        }
    }
    public static void B(boolean p0){
        sad.Q = p0;
    }
    public static boolean B(){
        return sad.N;
    }
    public static void C(boolean p0){
        sad.V = p0;
    }
    public static boolean C(){
        return sad.O;
    }
    public static boolean C(String p0){
        CopyOnWriteArrayList h;
        if(!TextUtils.isEmpty(p0) && (h = sad.h) != null){
            return h.contains(p0);
        }else {
            return false;
        }
    }
    public static void D(boolean p0){
        sad.W = p0;
    }
    public static boolean D(){
        return sad.K;
    }
    public static void E(boolean p0){
        SpdyAgent.configSwitchValueByKey(30, p0 ? 1 : 0, 0, null);
    }
    public static boolean E(){
        return sad.L;
    }
    public static boolean F(){
        return sad.Q;
    }
    public static boolean G(){
        return sad.V;
    }
    public static boolean H(){
        return sad.W;
    }
    public static int I(){
        if(sad.b() && sad.J()){
            return 2;
        }else {
            return 1;
        }
    }
    public static boolean J(){
        return sad.aa;
    }
    public static void a(int p0){
        sad.X = p0;
    }
    public static void a(long p0){
        SpdyAgent.configSwitchValueByKey(14, (int)p0, 0, null);
    }
    public static void a(String p0){
        Object[] objArray;
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.g = uCopyOnWrite;
                return;
            }catch(Exception e6){
                e6.printStackTrace();
                return;
            }
        }
    }
    private static void a(JSONArray p0){
        Object[] objArray;
        if(p0 == null){
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                int i = 0;
                while (i < p0.length()) {
                    String str = p0.getString(i);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i = i + 1;
                }
                sad.x = uCopyOnWrite;
                return;
            }catch(Exception e6){
                e6.printStackTrace();
                return;
            }
        }
    }
    public static void a(boolean p0){
        sad.b = p0;
    }
    public static boolean a(){
        return sad.b;
    }
    public static boolean a(URL p0){
        List<String> list;
        ConcurrentHashMap y = sad.y;
        if (p0 != null && y != null) {
            try{
                if ((list = (List) y.get(p0.getHost())) == null) {
                    return false;
                }else if(list == sad.a){
                    return true;
                }else {
                    Iterator<String> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        if (p0.getPath().startsWith(iterator.next())) {
                            return true;
                        }
                    }
                }
            }catch(Exception e0){
                e0.printStackTrace();
            }
        }
        return false;
    }
    public static boolean a(URL p0,String p1){
        List list;
        if(p0 != null && (p1 != null && !TextUtils.isEmpty(p1))){
            ConcurrentHashMap uConcurrentH = null;
            try{
                if ("fast".equalsIgnoreCase(p1)) {
                    uConcurrentH = sad.t;
                }else if("normal".equalsIgnoreCase(p1)){
                    uConcurrentH = sad.q;
                }
                if (uConcurrentH == null) {
                    return false;
                }else if((list = (List) uConcurrentH.get(p0.getHost())) == null){
                    return false;
                }else if(list == sad.a){
                    return true;
                }else {
                    Iterator<String> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        if (p0.getPath().startsWith(iterator.next())) {
                            return true;
                        }
                    }
                }
            }catch(Exception e0){
            }
        }
        return false;
    }
    public static void b(long p0){
        int $ipChange = 0x4e20;
        if ((p0 - $ipChange) > 0) {
            p0 = $ipChange;
        }
        if ((p0) > 0) {
            sad.j = p0;
        }
    }
    public static void b(String p0){
        Object[] objArray;
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.f = uCopyOnWrite;
                return;
            }catch(Exception e6){
                e6.printStackTrace();
                return;
            }
        }
    }
    private static void b(JSONArray p0){
        Object[] objArray;
        if(p0 == null){
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                int i = 0;
                while (i < p0.length()) {
                    String str = p0.getString(i);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i = i + 1;
                }
                sad.Y = uCopyOnWrite;
                return;
            }catch(Exception e6){
                e6.printStackTrace();
                return;
            }
        }
    }
    public static void b(boolean p0){
        SpdyAgent.configSwitchValueByKey(1, p0 ? 1 : 0, 0, null);
    }
    public static boolean b(){
        return sad.m;
    }
    public static boolean b(URL p0){
        List list;
        ConcurrentHashMap d = sad.d;
        if (p0 != null && d != null) {
            try{
                if ((list = (List) d.get(p0.getHost())) == null) {
                    return false;
                }else {
                    Iterator<String> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        if (p0.getPath().startsWith(iterator.next())) {
                            return true;
                        }
                    }
                }
            }catch(Exception e0){
            }
        }
        return false;
    }
    public static void c(long p0){
        int $ipChange = 0x4e20;
        if ((p0 - $ipChange) > 0) {
            p0 = $ipChange;
        }
        if ((p0) > 0) {
            sad.k = p0;
        }
    }
    public static void c(boolean p0){
        sad.m = p0;
    }
    public static boolean c(){
        return sad.c;
    }
    public static boolean c(String p0){
        CopyOnWriteArrayList f;
        if((f = sad.f) != null && f.size() > 0){
            if (!sad.f.contains(p0) && !sad.f.contains("*")) {
                return false;
            }
            return true;
        }else {
            return false;
        }
    }
    public static void d(long p0){
        if((p0) > 0){
            sad.z = p0;
        }
        return;
    }
    public static void d(String p0){
        Object[] objArray;
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.e = uCopyOnWrite;
                return;
            }catch(Exception e6){
                e6.printStackTrace();
            }
        }
    }
    public static void d(boolean p0){
        sad.c = p0;
    }
    public static boolean d(){
        if(sad.g == null){
            return false;
        }else if(sad.g.contains("*")){
            return true;
        }else {
            return sad.g.contains(Build.BRAND.toLowerCase());
        }
    }
    public static void e(long p0){
        int i = 0;
        SpdyAgent.configSwitchValueByKey(29, (int)p0, 0, null);
    }
    public static boolean e(String p0){
        CopyOnWriteArrayList e;
        if(!TextUtils.isEmpty(p0) && (e = sad.e) != null){
            if (e.contains("*")) {
                return true;
            }
            return sad.e.contains(p0);
        }else {
            return false;
        }
    }
    public static long f(){
        return sad.j;
    }
    public static void f(String p0){
        Object[] objArray;
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.i = uCopyOnWrite;
                return;
            }catch(Exception e6){
                e6.printStackTrace();
                return;
            }
        }
    }
    public static void f(boolean p0){
        sad.l = p0;
    }
    public static long g(){
        return sad.k;
    }
    public static void g(boolean p0){
        sad.n = p0;
        SpdyAgent.configSwitchValueByKey(2, p0 ? 1 : 0, 0, null);
    }
    public static boolean g(String p0){
        CopyOnWriteArrayList i;
        if(!TextUtils.isEmpty(p0) && (i = sad.i) != null){
            if (i.contains("*")) {
                return true;
            }
            return sad.i.contains(p0);
        }else {
            return false;
        }
    }
    public static void h(String p0){
        int i = 0;
        ConcurrentHashMap uConcurrentH = null;
        if (TextUtils.isEmpty(p0)) {
            sad.q = uConcurrentH;
            sad.t = uConcurrentH;
            return;
        }else {
            try{
                JSONArray jSONArray = new JSONArray();
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    try{
                        ConcurrentHashMap uConcurrentH1 = new ConcurrentHashMap();
                        JSONObject jSONObject = jSONArray.getJSONObject(i1);
                        String str = jSONObject.getString("type");
                        if (!TextUtils.isEmpty(str)) {
                            Long longx = Long.valueOf(jSONObject.optLong("request_idle_time"));
                            Long longx1 = Long.valueOf(jSONObject.optLong("body_idle_time"));
                            if ((jSONObject = jSONObject.optJSONObject("url")) != null) {
                                Iterator<String> iterator = jSONObject.keys();
                                while (iterator.hasNext()) {
                                    String str1 = iterator.next();
                                    JSONArray obj = (JSONArray) jSONObject.get(str1);
                                    try{
                                        if ("*".equals(obj)) {
                                            uConcurrentH1.put(str1, sad.a);
                                        }else if(obj instanceof JSONArray){
                                            int i2 = obj.length();
                                            ArrayList uArrayList = new ArrayList(i2);
                                            int i3 = 0;
                                            while (i3 < i2) {
                                                Object obj1 = obj.get(i3);
                                                if (obj1 instanceof String) {
                                                    uArrayList.add(obj1);
                                                }
                                                i3 = i3 + 1;
                                                i = 0;
                                            }
                                            if (!uArrayList.isEmpty()) {
                                                uConcurrentH1.put(str1, uArrayList);
                                            }
                                        }
                                        i = 0;
                                    }catch(Exception e0){
                                    }
                                }
                                int i2 = 0;
                                if ("fast".equalsIgnoreCase(str)) {
                                    if (longx != null && (longx.longValue() - i2) > 0) {
                                        sad.r = longx.longValue();
                                    }
                                    if (longx1 != null && (longx1.longValue() - i2) > 0) {
                                        sad.s = longx1.longValue();
                                    }
                                    if (!uConcurrentH1.isEmpty()) {
                                        sad.t = uConcurrentH1;
                                    }
                                }else if("normal".equalsIgnoreCase(str)){
                                    if (longx != null && (longx.longValue() - i2) > 0) {
                                        sad.o = longx.longValue();
                                    }
                                    if (longx1 != null && (longx1.longValue() - i2) > 0) {
                                        sad.p = longx1.longValue();
                                    }
                                    if (!uConcurrentH1.isEmpty()) {
                                        sad.q = uConcurrentH1;
                                    }
                                }
                            }
                        }
                        i1 = i1 + 1;
                        i = 0;
                    }catch(Exception e0){
                        e0.printStackTrace();
                        return;
                    }
                }
                return;
            }catch(Exception e0){
            }
        }
    }
    public static void h(boolean p0){
        sad.u = p0;
    }
    public static boolean h(){
        return sad.l;
    }
    public static void i(String p0){
        JSONArray jSONArray;
        int i = 1;
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            String str1 = null;
            try{
                try{
                    JSONObject jSONObject = new JSONObject(p0);
                    sad.h(jSONObject.getBoolean("mpquic_enable"));
                    try{
                        int intx = jSONObject.getInt("mp_version");
                        SpdyAgent.configSwitchValueByKey(3, intx, 0, null);
                        try{
                            intx = jSONObject.getInt("path_keep_alive_time");
                            SpdyAgent.configSwitchValueByKey(5, intx, 0, null);
                            try{
                                intx = jSONObject.getInt("scheduler_type");
                                SpdyAgent.configSwitchValueByKey(6, intx, 0, null);
                                try{
                                    if ((jSONArray = jSONObject.optJSONArray("minrtt_host")) != null) {
                                        sad.a(jSONArray);
                                    }
                                    try{
                                        intx = jSONObject.getInt("mp_enable_reinjection_type");
                                        SpdyAgent.configSwitchValueByKey(8, intx, 0, null);
                                        try{
                                            double doublex = jSONObject.getDouble("reinj_deadline_srtt_factor");
                                            SpdyAgent.configSwitchValueByKey(9, 0, doublex, null);
                                            try{
                                                boolean booleanX = jSONObject.getBoolean("enable_path_info");
                                                long l = 27;
                                                int i1 = (booleanX)? 1: 0;
                                                SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                                                try{
                                                    sad.r(jSONObject.getBoolean("enable_amdc_mp"));
                                                }catch(Exception e0){
                                                    return;
                                                }
                                            }catch(Exception e0){
                                            }
                                        }catch(Exception e0){
                                        }
                                    }catch(Exception e0){
                                    }
                                }catch(Exception e0){
                                }
                            }catch(Exception e0){
                            }
                        }catch(Exception e0){
                        }
                    }catch(Exception e0){
                    }
                }catch(Exception e0){
                }
            }catch(Exception e13){
                e13.printStackTrace();
                return;
            }
        }
    }
    public static void i(boolean p0){
        Object[] objArray;
        int i = 0;
        SpdyAgent.configSwitchValueByKey(15, p0 ? 1 :0, 0, null);
    }
    public static boolean i(){
        return sad.n;
    }
    public static long j(){
        return sad.o;
    }
    public static void j(boolean p0){
        sad.aa = p0;
    }
    public static boolean j(String p0){
        CopyOnWriteArrayList x;
        if(!TextUtils.isEmpty(p0) && (x = sad.x) != null){
            if (x.contains("*")) {
                return true;
            }
            return sad.x.contains(p0);
        }else {
            return false;
        }
    }
    public static long k(){
        return sad.r;
    }
    public static void k(String p0){
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            sad.v = null;
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.v = uCopyOnWrite;
                return;
            }catch(Exception e7){
                e7.printStackTrace();
                return;
            }
        }
    }
    public static long l(){
        return sad.p;
    }
    public static void l(boolean p0){
        sad.A = p0;
    }
    public static boolean l(String p0){
        CopyOnWriteArrayList v;
        if(!TextUtils.isEmpty(p0) && (v = sad.v) != null){
            if (v.contains("*")) {
                return true;
            }
            return sad.v.contains(p0);
        }else {
            return false;
        }
    }
    public static long m(){
        return sad.s;
    }
    public static void m(String p0){
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            sad.w = null;
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.w = uCopyOnWrite;
                return;
            }catch(Exception e7){
                e7.printStackTrace();
            }
        }
    }
    public static void m(boolean p0){
        sad.B = p0;
    }
    public static void n(boolean p0){
        sad.E = p0;
    }
    public static boolean n(){
        return sad.u;
    }
    public static boolean n(String p0){
        CopyOnWriteArrayList w;
        if(!TextUtils.isEmpty(p0) && (w = sad.w) != null){
            if (w.contains("*")) {
                return true;
            }
            return sad.w.contains(p0);
        }else {
            return false;
        }
    }
    public static long o(){
        return sad.z;
    }
    public static void o(String p0){
        ConcurrentHashMap $ipChange1 = null;
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            sad.y = null;
            return;
        }else {
            try{
                $ipChange1 = new ConcurrentHashMap();
                JSONObject jSONObject = new JSONObject(p0);
                Iterator<String> iterator = jSONObject.keys();
                try{
                    while (iterator.hasNext()) {
                        String str = iterator.next();
                        JSONArray obj = (JSONArray)jSONObject.get(str);
                        if ("*".equals(obj)) {
                            $ipChange1.put(str, sad.a);
                        }else if(obj instanceof JSONArray){
                            int i1 = obj.length();
                            ArrayList uArrayList = new ArrayList(i1);
                            int i2 = 0;
                            while (i2 < i1) {
                                Object obj1 = obj.get(i2);
                                if (obj1 instanceof String) {
                                    uArrayList.add(obj1);
                                }
                                i2 = i2 + 1;
                            }
                            if (!uArrayList.isEmpty()) {
                                $ipChange1.put(str, uArrayList);
                            }
                        }
                    }
                }catch(Exception e0){
                }
            }catch(Exception e12){
                e12.printStackTrace();
                sad.y = $ipChange1;
                return;
            }
        }
    }
    public static void o(boolean p0){
        SpdyAgent.configSwitchValueByKey(24, p0 ? 1 : 0, 0, null);
    }
    public static void p(String p0){
        Object[] objArray1;
        int i = 1;
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            String str1 = null;
            try{
                try{
                    JSONObject jSONObject = new JSONObject(p0);
                    int intx = jSONObject.getInt("tunnel_quic_cc_type");
                    SpdyAgent.configSwitchValueByKey(18, intx, 0, null);
                }catch(Exception e0){
                    return;
                }
            }catch(Exception e11){
                e11.printStackTrace();
            }
            return;
        }
    }
    public static void p(boolean p0){
        sad.D = p0;
    }
    public static boolean p(){
        return sad.A;
    }
    public static void q(String p0){
        Object[] objArray;
        Long longx;
        Long longx1;
        Long longx2;
        Long longx3;
        String str = "max_ack_delay_ms";
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            String str1 = null;
            try{
                JSONObject jSONObject = new JSONObject();
                if ((longx = Long.valueOf(jSONObject.optLong("ack_frequency"))) != null) {
                    SpdyAgent.configSwitchValueByKey(19, longx.intValue(), 0, null);
                }
                if ((longx1 = Long.valueOf(jSONObject.optLong(str))) != null) {
                    SpdyAgent.configSwitchValueByKey(20, longx1.intValue(), 0, null);
                }
                if ((longx2 = Long.valueOf(jSONObject.optLong("datagram_redundancy"))) != null) {
                    SpdyAgent.configSwitchValueByKey(21, longx2.intValue(), 0, null);
                }
                if ((longx3 = Long.valueOf(jSONObject.optLong("datagram_redundant_probe_us"))) != null) {
                    SpdyAgent.configSwitchValueByKey(22, longx3.intValue(), 0, null);
                }
                try{
                    boolean booleanx = jSONObject.getBoolean("enable_pmtu");
                    long l = 26;
                    int i1 = (booleanx)? 1: 0;
                    SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                    try{
                        boolean booleanx1 = jSONObject.getBoolean("enable_mp_bg_opt");
                        long l1 = 28;
                        int i = (booleanx1)? 1: 0;
                        SpdyAgent.configSwitchValueByKey(l1, i, 0, null);
                    }catch(Exception e0){
                    }
                }catch(Exception e0){
                }
            }catch(Exception e0){
            }
        }
    }
    public static void q(boolean p0){
        sad.C = p0;
    }
    public static boolean q(){
        if(sad.B && "com.taobao.taobao:channel".equals("com.taobao.taobao:channel")){
            return true;
        }else {
            return false;
        }
    }
    public static void r(String p0){
        int i = 0;
        if(TextUtils.isEmpty(p0)){
            sad.G = null;
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.G = uCopyOnWrite;
                return;
            }catch(Exception e7){
                e7.printStackTrace();
            }
        }
    }
    public static void r(boolean p0){
        sad.F = p0;
    }
    public static boolean r(){
        return sad.E;
    }
    public static void s(boolean p0){
        sad.H = p0;
    }
    public static boolean s(){
        if(sad.r() && !"com.taobao.taobao:channel".equals("com.taobao.taobao:channel")){
            return true;
        }else {
            return false;
        }
    }
    public static boolean s(String p0){
        CopyOnWriteArrayList g;
        if(!TextUtils.isEmpty(p0) && (g = sad.G) != null){
            return g.contains(p0);
        }else {
            return false;
        }
    }
    public static void t(String p0){
        Object[] objArray;
        String str = "type";
        if(TextUtils.isEmpty(p0)){
            sad.R = null;
            sad.S = null;
            return;
        }else {
            try{
                JSONArray jSONArray = new JSONArray(p0);
                int i = 0;
                while (i < jSONArray.length()) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String str1 = jSONObject.getString(str);
                    String str2 = jSONObject.optString("connect_option");
                    JSONArray jSONArray1 = jSONObject.optJSONArray("host");
                    if (!TextUtils.isEmpty(str1) && (!TextUtils.isEmpty(str2) && jSONArray1 != null)) {
                        CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                        int i1 = 0;
                        while (i1 < jSONArray1.length()) {
                            String str3 = jSONArray1.getString(i1);
                            if (!TextUtils.isEmpty(str3)) {
                                uCopyOnWrite.add(str3);
                            }
                            i1 = i1 + 1;
                        }
                        if (!uCopyOnWrite.isEmpty()) {
                            if ("video".equalsIgnoreCase(str1)) {
                                sad.T = str2;
                                sad.R = uCopyOnWrite;
                            }else if("picture".equalsIgnoreCase(str1)){
                                sad.U = str2;
                                sad.S = uCopyOnWrite;
                            }
                        }
                    }
                    i++;
                }
                return;
            }catch(Exception e13){
                e13.printStackTrace();
            }
        }
    }
    public static void t(boolean p0){
        sad.I = p0;
    }
    public static boolean t(){
        return sad.D;
    }
    public static String u(String p0){
        if(!TextUtils.isEmpty(p0) && (!TextUtils.isEmpty(sad.U) && (sad.S == null && (TextUtils.isEmpty(sad.T) && sad.S == null)))){
            try{
                if (sad.H() && (sad.R != null && sad.R.contains(p0))) {
                    return sad.T;
                }else if(sad.G() && (sad.S != null && sad.S.contains(p0))){
                    return sad.U;
                }
            }catch(Exception e4){
                e4.printStackTrace();
            }
        }
        return null;
    }
    public static void u(boolean p0){
        sad.J = p0;
    }
    public static boolean u(){
        return sad.C;
    }
    public static void v(boolean p0){
        sad.P = p0;
        SpdyAgent.configSwitchValueByKey(17, p0 ? 1 : 0, 0, null);
    }
    public static boolean v(){
        return sad.F;
    }
    public static boolean v(String p0){
        CopyOnWriteArrayList y;
        if(!TextUtils.isEmpty(p0) && (y = sad.Y) != null){
            return y.contains(p0);
        }else {
            return false;
        }
    }
    public static void w(String p0){
        Long longx1;
        JSONArray jSONArray;
        String str = "tnetsdk.SwitchConfig";
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            String str1 = null;
            try{
                JSONObject jSONObject = new JSONObject(p0);
                Long longx = Long.valueOf(jSONObject.optLong("raw_size"));
                int i = 0x40000;
                if (longx != null && (longx.intValue() > 0 && longx.intValue() < i)) {
                    SpdyAgent.configSwitchValueByKey(33, longx.intValue(), 0, null);
                }
                if ((longx1 = Long.valueOf(jSONObject.optLong("deflated_size"))) != null && (longx1.intValue() > 0 && longx1.intValue() < i)) {
                    SpdyAgent.configSwitchValueByKey(34, longx1.intValue(), 0, null);
                }
                try{
                    boolean bool = jSONObject.getBoolean("direct_enable");
                    long l = 35;
                    int i1 = (bool)? 1: 0;
                    SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                    try{
                        bool = jSONObject.getBoolean("recvmmsg_enable");
                        l = 31;
                        i1 = (bool)? 1: 0;
                        SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                        try{
                            if ((jSONArray = jSONObject.optJSONArray("resize_host")) != null) {
                                sad.b(jSONArray);
                            }
                        }catch(Exception e0){
                        }
                    }catch(Exception e0){
                    }
                }catch(Exception e0){
                }
            }catch(Exception e14){
            }
        }
    }
    public static void w(boolean p0){
        sad.M = p0;
    }
    public static boolean w(){
        return sad.H;
    }
    public static void x(String p0){
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            try{
                CopyOnWriteArrayList uCopyOnWrite = new CopyOnWriteArrayList();
                JSONArray jSONArray = new JSONArray(p0);
                int i1 = 0;
                while (i1 < jSONArray.length()) {
                    String str = jSONArray.getString(i1);
                    if (!TextUtils.isEmpty(str)) {
                        uCopyOnWrite.add(str);
                    }
                    i1++;
                }
                sad.Z = uCopyOnWrite;
                return;
            }catch(Exception e6){
                e6.printStackTrace();
                return;
            }
        }
    }
    public static void x(boolean p0){
        sad.N = p0;
    }
    public static boolean x(){
        return sad.I;

    }
    public static void y(boolean p0){
        sad.O = p0;
    }
    public static boolean y(){
        return sad.J;
    }
    public static boolean y(String p0){
        CopyOnWriteArrayList z;
        if(!TextUtils.isEmpty(p0) && (z = sad.Z) != null){
            if (z.contains("*")) {
                return true;
            }
            return sad.Z.contains(p0);
        }else {
            return false;
        }
    }
    public static void z(String p0){
        Object[] objArray1;
        String str = "tnetsdk.SwitchConfig";
        if(TextUtils.isEmpty(p0)){
            return;
        }else {
            String str1 = null;
            try{
                try{
                    JSONObject jSONObject = new JSONObject(p0);
                    boolean booleanx = jSONObject.getBoolean("cid_update_enable");
                    long l = 25;
                    int i1 = (booleanx)? 1: 0;
                    SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                    try{
                        sad.p(jSONObject.getBoolean("quic_init_opt"));
                        try{
                            booleanx = jSONObject.getBoolean("zstd_rso");
                            sad.B(booleanx);
                            try{
                                booleanx = jSONObject.getBoolean("mp_net_enable");
                                sad.d(booleanx);
                                try{
                                    sad.q(jSONObject.getBoolean("mp_net_upper_enable"));
                                    try{
                                        booleanx = jSONObject.getBoolean("tunnel_datagram");
                                        l = 16;
                                        i1 = (booleanx)? 1: 0;
                                        SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                                        try{
                                            booleanx = jSONObject.getBoolean("encode_crash_opt");
                                            l = 36;
                                            i1 = (booleanx)? 1: 0;
                                            SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                                            try{
                                                booleanx = jSONObject.getBoolean("decode_crash_opt");
                                                l = 37;
                                                i1 = (booleanx)? 1: 0;
                                                SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                                                try{
                                                    booleanx = jSONObject.getBoolean("storage_remove");
                                                    l = 39;
                                                    i1 = (booleanx)? 1: 0;
                                                    SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                                                    try{
                                                        booleanx = jSONObject.getBoolean("storage_env_fix");
                                                        l = 40;
                                                        i1 = (booleanx)? 1: 0;
                                                        SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                                                        try{
                                                            booleanx = jSONObject.getBoolean("quic_opt");
                                                            l = 41;
                                                            i1 = (booleanx)? 1: 0;
                                                            SpdyAgent.configSwitchValueByKey(l, i1, 0, null);
                                                            try{
                                                                i1 = (booleanx = jSONObject.getBoolean("atoi_enable"))? 1: 0;
                                                                SpdyAgent.configSwitchValueByKey(32, i1, 0, null);
                                                            }catch(Exception e0){
                                                                return;
                                                            }
                                                        }catch(Exception e0){
                                                        }
                                                    }catch(Exception e0){
                                                    }
                                                }catch(Exception e0){
                                                }
                                            }catch(Exception e0){
                                            }
                                        }catch(Exception e0){
                                        }
                                    }catch(Exception e0){
                                    }
                                }catch(Exception e0){
                                }
                            }catch(Exception e0){
                            }
                        }catch(Exception e0){
                        }
                    }catch(Exception e0){
                    }
                }catch(Exception e0){
                }
            }catch(Exception e11){
                e11.printStackTrace();
                return;
            }
        }
    }
    public static void z(boolean p0){
        sad.K = p0;
    }
    public static boolean z(){
        return sad.P;

    }

}
