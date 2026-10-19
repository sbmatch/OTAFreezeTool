package com.sbmatch.androtool;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
// import com.bytedance.android.bytehook.ByteHook;
// import com.bytedance.shadowhook.ShadowHook;
// import com.sbmatch.androtool.utils.ExecutorUtils;

public class HookInitProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        // ByteHook AAR 已移除，先注释掉；需要时恢复依赖后再打开。
        // ExecutorUtils.submit(ByteHook::init);
        //System.loadLibrary("auto_hooker");
        return false;
    }

    // 其他方法不需要实现
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}