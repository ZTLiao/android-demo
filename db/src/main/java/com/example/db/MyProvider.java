package com.example.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {

    String TAG = MyProvider.class.getSimpleName();

    private static final UriMatcher uriMatcher;

    private static final String AUTHORITY_PROVIDER = "com.example.db.authority";

    private static final int CODE_PROVIDER_USER = 11;

    private SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY_PROVIDER, DBHelper.TABLE_USER, CODE_PROVIDER_USER);
    }


    @Override
    public boolean onCreate() {
        try (DBHelper dbHelper = new DBHelper(getContext(), "", null, 1)) {
            db = dbHelper.getWritableDatabase();
        }
        db.execSQL("insert into " + DBHelper.TABLE_USER + " values(1, 'tom', 19, 87)");
        db.execSQL("insert into " + DBHelper.TABLE_USER + " values(2, 'jack', 18, 36)");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return null;
        }
        return db.query(tableName, strings, s, strings1, null, null, s1);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "";
    }

    private String getTableName(Uri uri) {
        String tableName = "";
        if (uriMatcher.match(uri) == CODE_PROVIDER_USER) {
            tableName = DBHelper.TABLE_USER;
        }
        return tableName;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return null;
        }
        long count = db.insert(tableName, null, contentValues);
        if (count > 0) {
            Log.i(TAG, "insert success");
        } else {
            Log.i(TAG, "insert fail");
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return 0;
        }
        int count = db.delete(tableName, s, strings);
        if (count > 0) {
            Log.i(TAG, "delete success");
        } else {
            Log.i(TAG, "delete fail");
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return 0;
        }
        int count = db.update(tableName, contentValues, s, strings);
        if (count > 0) {
            Log.i(TAG, "update success");
        } else {
            Log.i(TAG, "update fail");
        }
        return count;
    }
}
