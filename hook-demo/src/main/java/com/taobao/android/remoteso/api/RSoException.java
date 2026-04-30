package com.taobao.android.remoteso.api;

public class RSoException extends RuntimeException {


    private int errorCode;
    private String errorMsg;
    public int ERROR_DOWNLOAD_FAILED;
    public int ERROR_EMPTY_IMPL;
    public int ERROR_FETCH_ASYNC_CALLBACK_FAILED;
    public int ERROR_FETCH_ASYNC_FAILED;
    public int ERROR_FETCH_ASYNC_FAILED_EMPTY_IMPL;
    public int ERROR_FETCH_EXTRACT_EMPTY_PATH_WITHOUT_EXCEPTION;
    public int ERROR_FETCH_FAILED;
    public int ERROR_FETCH_FAILED_CONFIG_ON_DEMAND_NOT_ENABLED;
    public int ERROR_FETCH_IN_APK_RESOLVE_FAILED;
    public int ERROR_FETCH_SYNC_FAILED;
    public int ERROR_INDEX_ABI_IS_EMPTY;
    public int ERROR_INDEX_DATA_NOT_FOUND;
    public int ERROR_INDEX_ENTRY_NOT_FOUND;
    public int ERROR_INDEX_FILE_MD5_IS_EMPTY;
    public int ERROR_INDEX_FILE_URL_IS_EMPTY;
    public int ERROR_INDEX_ILLEGAL_COMPRESSED_FILE_URL;
    public int ERROR_INDEX_ILLEGAL_FROM;
    public int ERROR_INDEX_INFO_NOT_FOUND;
    public int ERROR_LOAD_ASYNC_CALLBACK_FAILED;
    public int ERROR_LOAD_ASYNC_FAILED;
    public int ERROR_LOAD_DO_LOAD_FAILED;
    public int ERROR_LOAD_FAILED;
    public int ERROR_LOAD_FAILED_CONFIG_LIB_DISABLED;
    public int ERROR_LOAD_FAILED_EMPTY_IMPL;
    public int ERROR_LOAD_FALLBACK_SYSTEM_LOAD_FAILED;
    public int ERROR_LOAD_PREFER_SYSTEM_LOAD_FAILED;
    public int ERROR_LOAD_SYNC_FAILED;
    public int ERROR_OBTAIN_FAILED;
    public int ERROR_OBTAIN_FAILED_CONFIG_LIB_DISABLED;
    public int ERROR_OBTAIN_FAILED_EMPTY_IMPL;
    public int ERROR_OBTAIN_FAILED_IS_FETCHING;
    public int ERROR_OBTAIN_FAILED_NOT_FOUND;
    public int ERROR_STORAGE_ILLEGAL_ARGS;
    public int ERROR_UNKNOWN;

    public RSoException(String p0, int p1, String p2) {
        super(p0);
        this.errorCode = p1;
        this.errorMsg = p2;
    }

    public RSoException(String p0, Throwable p1, int p2, String p3) {
        super(p0, p1);
        this.errorCode = p2;
        this.errorMsg = p3;
    }

    public static RSoException error(int p0) {
        return new RSoException(RSoException.parseString(p0, null), p0, null);
    }

    public static RSoException error(int p0, String p1) {
        return new RSoException(RSoException.parseString(p0, p1), p0, p1);
    }

    public static RSoException error(int p0, Throwable p1) {
        if (p1 == null) {
            return new RSoException(RSoException.parseString(p0, null), p0, null);
        } else if (p1 instanceof RSoException) {
            return (RSoException) p1;
        } else {
            String message = p1.getMessage();
            return new RSoException(RSoException.parseString(p0, message), p1, p0, message);
        }
    }

    private static String parseString(int p0, String p1) {
        return "RSoException{errorCode=" + p0 + ", errorMsg=\'" + p1 + "'" + "} ";
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        String str = this.errorMsg + "cause:";
        String str1 = (this.getCause() == null) ? "" : this.getCause().toString();
        return str + str1;
    }

    public String toString() {
        return RSoException.parseString(this.errorCode, this.errorMsg);
    }
}


