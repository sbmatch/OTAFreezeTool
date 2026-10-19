package com.xiaomi.joyose;

import android.os.IBinder;
import android.os.IInterface;

public interface IMonitorSurface extends IInterface {
    void getdyFps(int fps);

    abstract class Stub extends android.os.Binder implements IMonitorSurface {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
