package com.android.server;

import android.os.Handler;

public class UiThread extends ServiceThread {
    private UiThread() {
        super("android.ui", -2, false);
        throw new RuntimeException("Stub!");
    }

    public static UiThread get(){
        throw new RuntimeException("Stub!");
    }
    public static Handler getHandler(){
        throw new RuntimeException("Stub!");
    }

    public static void dispose(){
        throw new RuntimeException("Stub!");
    }


}
