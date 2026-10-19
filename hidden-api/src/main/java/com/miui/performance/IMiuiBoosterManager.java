package com.miui.performance;

import android.os.IInterface;

public interface IMiuiBoosterManager extends IInterface {
    int registerThermalEventCallback(int uid, IThermalEventBinderCallBack cb);
    int unRegisterThermalEventCallback(int uid, IThermalEventBinderCallBack cb);
    int requestCpuHighFreq(int uid, int level, int timeoutms);

    abstract class Stub extends android.os.Binder implements IMiuiBoosterManager {
        public static IMiuiBoosterManager asInterface(android.os.IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
