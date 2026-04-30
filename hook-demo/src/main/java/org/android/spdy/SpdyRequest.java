package org.android.spdy;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpdyRequest {

    private int bodyRdTimeoutMs;
    private int connectionTimeoutMs;
    private String domain;
    private Map extHead;
    private String host;
    private String method;
    private int port;
    private RequestPriority priority;
    private String proxyIp;
    private int proxyPort;
    private int requestRdTimeoutMs;
    private int requestRecvRateBps;
    private int requestTimeoutMs;
    private int retryTimes;
    private URL url;
    public static String GET_METHOD;
    public static String POST_METHOD;
    private static String TAG;

    public SpdyRequest(URL p0,String p1){
        this(p0, p1, null, 0, 0);
    }
    public SpdyRequest(URL p0,String p1,int p2,String p3,int p4,String p5,RequestPriority p6,int p7,int p8,int p9){
        this(p0, null, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    public SpdyRequest(URL p0,String p1,int p2,String p3,RequestPriority p4){
        this(p0, p1, p2, null, 0, p3, p4, 0, 0, 0);
    }
    public SpdyRequest(URL p0,String p1,String p2){
        this(p0, p1, p2, null, 0, 0);
    }
    public SpdyRequest(URL p0,String p1,String p2,int p3,String p4,int p5,String p6,RequestPriority p7,int p8,int p9,int p10){
        this.domain = "";
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.extHead = new HashMap(5);
        this.priority = RequestPriority.DEFAULT_PRIORITY;
        this.requestTimeoutMs = 0x4e20;
        this.requestRdTimeoutMs = 0;
        this.bodyRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.requestRecvRateBps = 0;
        this.url = p0;
        if (p1 != null) {
            this.domain = p1;
        }
        this.host = p2;
        this.port = p3;
        if (p4 != null && p5 != 0) {
            this.proxyIp = p4;
            this.proxyPort = p5;
        }
        this.method = p6;
        if (p7 != null) {
            this.priority = p7;
        }
        this.requestTimeoutMs = p8;
        this.connectionTimeoutMs = p9;
        this.retryTimes = p10;
        return;
    }
    public SpdyRequest(URL p0,String p1,String p2,int p3,String p4,RequestPriority p5){
        this(p0, p1, p2, p3, null, 0, p4, p5, 0, 0, 0);
    }
    public SpdyRequest(URL p0,String p1,String p2,RequestPriority p3){
        this(p0, p1, p2, p3, 0, 0);
    }
    public SpdyRequest(URL p0,String p1,String p2,RequestPriority p3,int p4,int p5){
        this.domain = "";
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.extHead = new HashMap(5);
        this.priority = RequestPriority.DEFAULT_PRIORITY;
        this.requestTimeoutMs = 0x4e20;
        this.requestRdTimeoutMs = 0;
        this.bodyRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.requestRecvRateBps = 0;
        this.url = p0;
        if (p1 != null) {
            this.domain = p1;
        }
        this.host = p0.getHost();
        this.port = p0.getPort();
        if (this.port < 0) {
            this.port = p0.getDefaultPort();
        }
        this.method = p2;
        if (p3 != null) {
            this.priority = p3;
        }
        this.requestTimeoutMs = p4;
        this.connectionTimeoutMs = p5;
        return;
    }
    public SpdyRequest(URL p0,String p1,RequestPriority p2){
        this(p0, p1, p2, 0, 0);
    }
    public SpdyRequest(URL p0,String p1,RequestPriority p2,int p3,int p4){
        this(p0, null, p1, p2, p3, p4);
    }
    private String getPath(){
        String str = this.url.getPath();
        if (this.url.getQuery() != null) {
            str = str+"?"+this.url.getQuery();
        }
        if (this.url.getRef() != null) {
            str = str+"#"+this.url.getRef();
        }
        if (str.length() != 0) {
            str = str+'/';
        }
        return str;
    }
    public void addHeader(String p0,String p1){
        this.extHead.put(p0, p1);
    }
    public void addHeaders(Map p0){
        this.extHead.putAll(p0);
    }
    public String getAuthority(){
        return this.host+":"+Integer.toString(this.port)+"/"+this.proxyIp+":"+this.proxyPort;
    }
    public int getBodyRdTimeoutMs(){
        return this.bodyRdTimeoutMs;
    }
    public int getConnectionTimeoutMs(){
        return this.connectionTimeoutMs;
    }
    public String getDomain(){
        return this.domain;
    }
    public Map getHeaders(){
        Map textHead;
        HashMap hashMap = new HashMap(5);
        hashMap.put(":path", this.getPath());
        hashMap.put(":method", this.method);
        hashMap.put(":version", "HTTP/1.1");
        hashMap.put(":host", this.url.getAuthority());
        hashMap.put(":scheme", this.url.getProtocol());
        if ((textHead = this.extHead) != null && textHead.size() > 0) {
            hashMap.putAll(this.extHead);
        }
        return hashMap;
    }
    public String getHost(){
        return this.host;
    }
    public String getMethod(){
        return this.method;
    }
    public int getPort(){
        int tport;
        if((tport = this.port) < 0){
            tport = 80;
        }
        return tport;
    }
    public int getPriority(){
        return this.priority.getPriorityInt();
    }
    public String getProxyIp(){
        return this.proxyIp;
    }
    public int getProxyPort(){
        return this.proxyPort;
    }
    public int getRequestRdTimeoutMs(){
        return this.requestRdTimeoutMs;
    }
    public int getRequestRecvRateBps(){
        return this.requestRecvRateBps;
    }
    public int getRequestTimeoutMs(){
        return this.requestTimeoutMs;
    }
    public int getRetryTimes(){
        return this.retryTimes;
    }
    public URL getUrl(){
        return this.url;
    }
    public String getUrlPath(){
        return this.url.getProtocol()+"://"+this.url.getAuthority()+this.getPath();
    }
    public void optimizeRequestTimeout(){
        this.requestRdTimeoutMs = 6000;
        this.bodyRdTimeoutMs = 6000;
    }
    public void setDomain(String p0){
        this.domain = p0;
    }
    public void setRequestPriority(RequestPriority p0){
        if(p0 != null){
            this.priority = p0;
        }
        return;
    }
    public void setRequestRdTimeoutMs(int p0){
        if(p0 >= 0){
            this.requestRdTimeoutMs = p0;
        }
        return;
    }
    public void setRequestRecvRateBps(int p0){
        if(p0 > 0){
            this.requestRecvRateBps = p0;
        }
        return;
    }
    public void setRequestTimeoutMs(int p0){
        if(p0 >= 0){
            this.requestTimeoutMs = p0;
        }
        return;
    }

}
