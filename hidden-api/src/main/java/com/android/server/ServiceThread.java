package com.android.server;

import android.os.HandlerThread;

public class ServiceThread extends HandlerThread {
    public ServiceThread(String name, int priority, boolean allowIo){
        super(name, priority);
        throw new RuntimeException("Stub!");
    }
}
