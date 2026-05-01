package tb;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class sac {
    private static boolean a;
    private static SharedPreferences b;

    static {
        try{
            //TODO
            //Class.forName("com.taobao.orange.OrangeConfig");
            sac.a = true;
        }catch(Exception e0){
            sac.a = false;
        }
    }
    private static long a(String p0,long p1){
        if(sac.b != null){
            return sac.b.getLong(p0, p1);
        }else {
            return p1;
        }
    }
    private static Boolean a(Context p0, String p1){
        int i = 1;
        try{
            Class uClass = Class.forName("com.taobao.android.ab.api.ABGlobal");
            Class[] uClassArray = new Class[]{Context.class,String.class};
            Object[] objArray = new Object[]{p0,p1};
            boolean b = ((Boolean)uClass.getDeclaredMethod("isFeatureOpened", uClassArray).invoke(uClass, objArray)).booleanValue();
            return Boolean.valueOf(b);
        }catch(Throwable e10){
            e10.printStackTrace();
            return false;
        }
    }
    private static String a(String p0,String p1){
        if(!sac.a){
            return null;
        }else {
            try{
                //TODO
                //return OrangeConfig.getInstance().getConfig("tnet4Android_sdk", p0, p1);
                return null;
            }catch(Exception e0){
                return null;
            }
        }
    }
    public static void a(Context p0){
        Boolean uBoolean;
        Boolean uBoolean1;
        String e0 = "";
        int i = 0;
        if(sac.a && p0 != null){
            try{
                String[] stringArray = new String[]{"tnet4Android_sdk"};
                //OrangeConfig.getInstance().registerListener(stringArray, new sac$1());
                sac.b = p0.getSharedPreferences("tnet_android_config", i);
                sad.a(sac.a("tlog_enable_switch", 1));
                sad.b(sac.a("jni_tlog_enable_switch", 1));
                sad.a(sac.a("jni_tlog_xquic_level", 2));
                sad.b(sac.a("quic_connect_timeout_ms", 5000));
                sad.c(sac.a("tcp_connect_timeout_ms", 6000));
                sad.f(sac.a("tunnel_proxy_enable_switch", 1) == 1);
                sad.g(sac.a("request_read_idle_timeout_switch", 1) == 1);
                sad.i(sac.a("http3_opt_dev_enable", 1) == 1);
                sad.o(sac.a("http_zstd_enable", 1) == 1);
                sad.l(sac.a("agent_free_enable", 1) == 1);
                sad.m(sac.a("channel_mem_opt_enable", i) == 1);
                sad.n(sac.a("quic_so_plugin_load_enable", 1) == 1);
                long l = sac.a("quic_init_and_min_cwnd", 0);
                try{
                    sad.e(l);
                    sad.z(sac.b("common_switch_config", e0));
                    sad.f(sac.b("connect_fast_timeout_host_white_list", e0));
                    sad.b(sac.b("multi_network_harmony_white_list", e0));
                    sad.d(sac.b("weak_network_force_cellular_host_white_list", e0));
                    sad.k(sac.b("mpquic_connect_compensate_host_white_list", e0));
                    sad.o(sac.b("mpquic_request_add_speed_url_white_list", e0));
                    sad.h(sac.b("request_idle_timeout_parameter_config", e0));
                    sad.i(sac.b("mpquic_parameter_config", e0));
                    sad.p(sac.b("tunnel_parameter_config", e0));
                    sad.a(sac.b("multi_network_background_brand_block_list", e0));
                    sad.q(sac.b("accs_parameter_config", e0));
                    sad.t(sac.b("cdn_connect_option", e0));
                    //TODO
                    if ("com.taobao.taobao".equals("com.taobao.taobao")) {
                        sad.c(sac.a("multi_thread_opt_enable", 1));
                        sad.m(sac.b("mpquic_connect_add_speed_host_white_list", "[\"umsgacs.m.taobao.com\"]"));
                        sad.r(sac.b("request_limit_speed_host_white_list", "[\"mtlexternal.alibabausercontent.com\"]"));
                        sad.w(sac.b("recv_body_opt_config", "{\"direct_enable\": \"true\",\"resize_host\": [\"heic.alicdn.com\",\"gw.alicdn.com\",\"guide-acs.m.taobao.com\",\"trade-acs.m.taobao.com\",\"guangguang.cloudvideocdn.taobao.com\",\"mtlexternal.alibabausercontent.com\"]}\n"));
                        sad.x(sac.b("multi_session_host_white_list", "[\"heic.alicdn.com\",\"gw.alicdn.com\"]"));
                        sad.A(sac.b("request_timeout_block_list", "{\"guide-acs.m.taobao.com\":[\"/gw/mtop.relationrecommend.mtoprecommend.recommendstream/1.0\"]}\n"));
                        sad.B(sac.b("quic_0rtt_connect_fast_timeout_host_white", "[\"pages-fast.m.taobao.com\"]"));
                    }
                    if ((uBoolean = sac.a(p0, "tnet_connect_fast_timeout_ab_enable")) != null) {
                        sad.s(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_connect_multi_network_ab_exp")) != null) {
                        sad.t(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_tunnel_closed")) != null) {
                        sad.u(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_tunnel_datagram_switch")) != null) {
                        sad.v(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_request_read_idle_timeout_ab_enable")) != null) {
                        sad.z(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_body_read_idle_timeout_ab_exp")) != null) {
                        sad.A(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_mpquic_compensate_enable")) != null) {
                        sad.w(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_mpquic_add_speed_enable")) != null) {
                        sad.x(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_amdc_mp_disable")) != null) {
                        sad.y(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "quic_connect_option_picture_enable")) != null) {
                        sad.C(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "quic_connect_option_video_enable")) != null) {
                        sad.D(uBoolean.booleanValue());
                    }
                    if ((uBoolean = sac.a(p0, "tnet_quic_init_rtt_enable")) != null) {
                        sad.E(uBoolean.booleanValue());
                    }
                    if ((uBoolean1 = sac.a(p0, "tnet_multi_thread_enable")) != null) {
                        sad.j(uBoolean1.booleanValue());
                    }
                    return;
                }catch(Exception e1){
                }
            }catch(Exception e2){
            }
        }
    }
    public static void a(String p0){
        sac.b(p0);
    }
    private static void a(String p0,Long p1){
        if(sac.b != null){
            sac.b.edit().putLong(p0, p1.longValue()).apply();
        }
        return;
    }
    private static boolean a(String p0,boolean p1){
        if(sac.b != null){
            return sac.b.getBoolean(p0, p1);
        }else {
            return p1;
        }
    }
    private static String b(String p0,String p1){
        if(sac.b != null){
            return sac.b.getString(p0, p1);
        }else {
            return p1;
        }
    }
    private static void b(String p0){
        boolean b;
        long l1;
        String e0 = "";
        String str = "tunnel_parameter_config";
        String str1 = "request_timeout_block_list";
        String str2 = "request_idle_timeout_parameter_config";
        String str3 = "request_read_idle_timeout_switch";
        String str4 = "mpquic_request_add_speed_url_white_list";
        String str5 = "mpquic_connect_add_speed_host_white_list";
        String str6 = "mpquic_connect_compensate_host_white_list";
        String str7 = "mpquic_parameter_config";
        String str8 = "http3_opt_dev_enable";
        String str9 = "weak_network_force_cellular_host_white_list";
        String str10 = "multi_network_harmony_white_list";
        String str11 = "quic_0rtt_connect_fast_timeout_host_white";
        String str12 = "connect_fast_timeout_host_white_list";
        String str13 = "multi_network_background_brand_block_list";
        if(!"tnet4Android_sdk".equals(p0)){
            return;
        }else {
            String str14 = null;
            try{
                String str15 = sac.a("tlog_enable_switch", str14);
                if (!TextUtils.isEmpty(str15)) {
                    b = Boolean.valueOf(str15).booleanValue();
                    sad.a(b);
                    sac.b("tlog_enable_switch", b);
                }
                try{
                    str15 = sac.a("jni_tlog_enable_switch", e0);
                    if (!TextUtils.isEmpty(str15)) {
                        b = Boolean.valueOf(str15).booleanValue();
                        sad.b(b);
                        sac.b("jni_tlog_enable_switch", b);
                    }
                    try{
                        str15 = sac.a("jni_tlog_xquic_level", e0);
                        if (!TextUtils.isEmpty(str15)) {
                            l1 = Long.valueOf(str15).longValue();
                            sad.a(l1);
                            sac.a("jni_tlog_xquic_level", Long.valueOf(l1));
                        }
                        try{
                            str15 = sac.a("tunnel_proxy_enable_switch", e0);
                            if (!TextUtils.isEmpty(str15)) {
                                b = Boolean.valueOf(str15).booleanValue();
                                sad.f(b);
                                sac.b("tunnel_proxy_enable_switch", b);
                            }
                            try{
                                str15 = sac.a("quic_connect_timeout_ms", e0);
                                if (!TextUtils.isEmpty(str15)) {
                                    l1 = Long.valueOf(str15).longValue();
                                    sad.b(l1);
                                    sac.a("quic_connect_timeout_ms", Long.valueOf(l1));
                                }
                                try{
                                    str15 = sac.a("tcp_connect_timeout_ms", e0);
                                    if (!TextUtils.isEmpty(str15)) {
                                        l1 = Long.valueOf(str15).longValue();
                                        sad.c(l1);
                                        sac.a("tcp_connect_timeout_ms", Long.valueOf(l1));
                                    }
                                    try{
                                        str15 = sac.a(str12, e0);
                                        if (!TextUtils.isEmpty(str15)) {
                                            sad.f(str15);
                                            sac.c(str12, str15);
                                        }else {
                                            sac.c(str12);
                                        }
                                        try{
                                            str15 = sac.a(str11, e0);
                                            if (!TextUtils.isEmpty(str15)) {
                                                sad.B(str15);
                                                sac.c(str11, str15);
                                            }else {
                                                sac.c(str11);
                                            }
                                            try{
                                                str15 = sac.a(str10, e0);
                                                if (!TextUtils.isEmpty(str15)) {
                                                    sad.b(str15);
                                                    sac.c(str10, str15);
                                                }else {
                                                    sac.c(str10);
                                                }
                                                try{
                                                    str15 = sac.a(str9, e0);
                                                    if (!TextUtils.isEmpty(str15)) {
                                                        sad.d(str15);
                                                        sac.c(str9, str15);
                                                    }else {
                                                        sac.c(str9);
                                                    }
                                                    try{
                                                        str15 = sac.a(str8, e0);
                                                        if (!TextUtils.isEmpty(str15)) {
                                                            b = Boolean.valueOf(str15).booleanValue();
                                                            sad.i(b);
                                                            sac.b(str8, b);
                                                        }else {
                                                            sac.c(str8);
                                                        }
                                                        try{
                                                            str15 = sac.a(str7, e0);
                                                            if (!TextUtils.isEmpty(str15)) {
                                                                sad.i(str15);
                                                                sac.c(str7, str15);
                                                            }else {
                                                                sac.c(str7);
                                                            }
                                                            try{
                                                                str15 = sac.a(str6, e0);
                                                                if (!TextUtils.isEmpty(str15)) {
                                                                    sad.k(str15);
                                                                    sac.c(str6, str15);
                                                                }else {
                                                                    sac.c(str6);
                                                                }
                                                                try{
                                                                    str15 = sac.a(str5, e0);
                                                                    if (!TextUtils.isEmpty(str15)) {
                                                                        sad.m(str15);
                                                                        sac.c(str5, str15);
                                                                    }else {
                                                                        sac.c(str5);
                                                                    }
                                                                    try{
                                                                        str15 = sac.a(str4, e0);
                                                                        if (!TextUtils.isEmpty(str15)) {
                                                                            sad.o(str15);
                                                                            sac.c(str4, str15);
                                                                        }else {
                                                                            sac.c(str4);
                                                                        }
                                                                        try{
                                                                            str15 = sac.a(str3, e0);
                                                                            if (!TextUtils.isEmpty(str15)) {
                                                                                b = Boolean.valueOf(str15).booleanValue();
                                                                                sad.g(b);
                                                                                sac.b(str3, b);
                                                                            }else {
                                                                                sac.c(str3);
                                                                            }
                                                                            try{
                                                                                str15 = sac.a(str2, e0);
                                                                                if (!TextUtils.isEmpty(str15)) {
                                                                                    sad.h(str15);
                                                                                    sac.c(str2, str15);
                                                                                }else {
                                                                                    sac.c(str2);
                                                                                }
                                                                                try{
                                                                                    str15 = sac.a(str1, e0);
                                                                                    if (!TextUtils.isEmpty(str15)) {
                                                                                        sad.A(str15);
                                                                                        sac.c(str1, str15);
                                                                                    }else {
                                                                                        sac.c(str1);
                                                                                    }
                                                                                    try{
                                                                                        str15 = sac.a(str, e0);
                                                                                        if (!TextUtils.isEmpty(str15)) {
                                                                                            sad.p(str15);
                                                                                            sac.c(str, str15);
                                                                                        }else {
                                                                                            sac.c(str);
                                                                                        }
                                                                                        str15 = str13;
                                                                                        try{
                                                                                            str = sac.a(str15, e0);
                                                                                            if (!TextUtils.isEmpty(str)) {
                                                                                                sad.a(str);
                                                                                                sac.c(str15, str);
                                                                                            }else {
                                                                                                sac.c(str15);
                                                                                            }
                                                                                            try{
                                                                                                str15 = sac.a("quic_0rtt_cache_expired_time_ms", e0);
                                                                                                if (!TextUtils.isEmpty(str15)) {
                                                                                                    sad.d(Long.valueOf(str15).longValue());
                                                                                                }
                                                                                                try{
                                                                                                    str15 = sac.a("accs_parameter_config", e0);
                                                                                                    if (!TextUtils.isEmpty(str15)) {
                                                                                                        sad.q(str15);
                                                                                                        sac.c("accs_parameter_config", str15);
                                                                                                    }else {
                                                                                                        sac.c("accs_parameter_config");
                                                                                                    }
                                                                                                    try{
                                                                                                        str15 = sac.a("http_zstd_enable", e0);
                                                                                                        if (!TextUtils.isEmpty(str15)) {
                                                                                                            b = Boolean.valueOf(str15).booleanValue();
                                                                                                            sad.o(b);
                                                                                                            sac.b("http_zstd_enable", b);
                                                                                                        }else {
                                                                                                            sac.c("http_zstd_enable");
                                                                                                        }
                                                                                                        try{
                                                                                                            str15 = sac.a("agent_free_enable", e0);
                                                                                                            if (!TextUtils.isEmpty(str15)) {
                                                                                                                b = Boolean.valueOf(str15).booleanValue();
                                                                                                                sad.l(b);
                                                                                                                sac.b("agent_free_enable", b);
                                                                                                            }else {
                                                                                                                sac.c("agent_free_enable");
                                                                                                            }
                                                                                                            try{
                                                                                                                str15 = sac.a("channel_mem_opt_enable", e0);
                                                                                                                if (!TextUtils.isEmpty(str15)) {
                                                                                                                    b = Boolean.valueOf(str15).booleanValue();
                                                                                                                    sad.m(b);
                                                                                                                    sac.b("channel_mem_opt_enable", b);
                                                                                                                }else {
                                                                                                                    sac.c("channel_mem_opt_enable");
                                                                                                                }
                                                                                                                try{
                                                                                                                    str15 = sac.a("common_switch_config", e0);
                                                                                                                    if (!TextUtils.isEmpty(str15)) {
                                                                                                                        sad.z(str15);
                                                                                                                        sac.c("common_switch_config", str15);
                                                                                                                    }else {
                                                                                                                        sac.c("common_switch_config");
                                                                                                                    }
                                                                                                                    try{
                                                                                                                        str15 = sac.a("quic_so_plugin_load_enable", e0);
                                                                                                                        if (!TextUtils.isEmpty(str15)) {
                                                                                                                            b = Boolean.valueOf(str15).booleanValue();
                                                                                                                            sad.n(b);
                                                                                                                            sac.b("quic_so_plugin_load_enable", b);
                                                                                                                        }else {
                                                                                                                            sac.c("quic_so_plugin_load_enable");
                                                                                                                        }
                                                                                                                        try{
                                                                                                                            str15 = sac.a("request_limit_speed_host_white_list", e0);
                                                                                                                            if (!TextUtils.isEmpty(str15)) {
                                                                                                                                sad.r(str15);
                                                                                                                                sac.c("request_limit_speed_host_white_list", str15);
                                                                                                                            }else {
                                                                                                                                sac.c("request_limit_speed_host_white_list");
                                                                                                                            }
                                                                                                                            try{
                                                                                                                                str15 = sac.a("cdn_connect_option", e0);
                                                                                                                                if (!TextUtils.isEmpty(str15)) {
                                                                                                                                    sad.t(str15);
                                                                                                                                    sac.c("cdn_connect_option", str15);
                                                                                                                                }else {
                                                                                                                                    sac.c("cdn_connect_option");
                                                                                                                                }
                                                                                                                                try{
                                                                                                                                    str15 = sac.a("quic_init_and_min_cwnd", e0);
                                                                                                                                    if (!TextUtils.isEmpty(str15)) {
                                                                                                                                        long l = Long.valueOf(str15).longValue();
                                                                                                                                        sad.e(l);
                                                                                                                                        sac.a("quic_init_and_min_cwnd", Long.valueOf(l));
                                                                                                                                    }else {
                                                                                                                                        sac.c("quic_init_and_min_cwnd");
                                                                                                                                    }
                                                                                                                                    try{
                                                                                                                                        str15 = sac.a("recv_body_opt_config", e0);
                                                                                                                                        if (!TextUtils.isEmpty(str15)) {
                                                                                                                                            sad.w(str15);
                                                                                                                                            sac.c("recv_body_opt_config", str15);
                                                                                                                                        }else {
                                                                                                                                            sac.c("recv_body_opt_config");
                                                                                                                                        }
                                                                                                                                        try{
                                                                                                                                            str15 = sac.a("multi_session_host_white_list", e0);
                                                                                                                                            if (!TextUtils.isEmpty(str15)) {
                                                                                                                                                sad.x(str15);
                                                                                                                                                sac.c("multi_session_host_white_list", str15);
                                                                                                                                            }else {
                                                                                                                                                sac.c("multi_session_host_white_list");
                                                                                                                                            }
                                                                                                                                            try{
                                                                                                                                                str14 = sac.a("multi_thread_opt_enable", e0);
                                                                                                                                                if (!TextUtils.isEmpty(str14)) {
                                                                                                                                                    sac.b("multi_thread_opt_enable", Boolean.valueOf(str14).booleanValue());
                                                                                                                                                }else {
                                                                                                                                                    sac.c("multi_thread_opt_enable");
                                                                                                                                                }
                                                                                                                                                return;
                                                                                                                                            }catch(Exception e){
                                                                                                                                            }
                                                                                                                                        }catch(Exception e){
                                                                                                                                        }
                                                                                                                                    }catch(Exception e){
                                                                                                                                    }
                                                                                                                                }catch(Exception e){
                                                                                                                                }
                                                                                                                            }catch(Exception e){
                                                                                                                            }
                                                                                                                        }catch(Exception e){
                                                                                                                        }
                                                                                                                    }catch(Exception e){
                                                                                                                    }
                                                                                                                }catch(Exception e){
                                                                                                                }
                                                                                                            }catch(Exception e){
                                                                                                            }
                                                                                                        }catch(Exception e){
                                                                                                        }
                                                                                                    }catch(Exception e){
                                                                                                    }
                                                                                                }catch(Exception e){
                                                                                                }
                                                                                            }catch(Exception e){
                                                                                            }
                                                                                        }catch(Exception e){
                                                                                        }
                                                                                    }catch(Exception e){
                                                                                    }
                                                                                }catch(Exception e){
                                                                                }
                                                                            }catch(Exception e){
                                                                            }
                                                                        }catch(Exception e){
                                                                        }
                                                                    }catch(Exception e){
                                                                    }
                                                                }catch(Exception e){
                                                                }
                                                            }catch(Exception e){
                                                            }
                                                        }catch(Exception e){
                                                        }
                                                    }catch(Exception e){
                                                    }
                                                }catch(Exception e){
                                                }
                                            }catch(Exception e){
                                            }
                                        }catch(Exception e){
                                        }
                                    }catch(Exception e){
                                    }
                                }catch(Exception e){
                                }
                            }catch(Exception e){
                            }
                        }catch(Exception e){
                        }
                    }catch(Exception e){
                    }
                }catch(Exception e){
                }
            }catch(Exception e){
            }
        }
    }
    private static void b(String p0,boolean p1){
        if(sac.b != null){
            sac.b.edit().putBoolean(p0, p1).apply();
        }
        return;
    }
    private static void c(String p0){
        if(sac.b != null){
            sac.b.edit().remove(p0).apply();
        }
        return;
    }
    private static void c(String p0,String p1){
        if(sac.b != null){
            sac.b.edit().putString(p0, p1).apply();
        }
        return;
    }

}
