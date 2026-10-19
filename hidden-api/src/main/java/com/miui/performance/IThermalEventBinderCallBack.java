package com.miui.performance;

import android.os.IBinder;
import android.os.IInterface;

public interface IThermalEventBinderCallBack extends IInterface {
    void onThermalLevelChanged(int level);

    abstract class Stub extends android.os.Binder implements IThermalEventBinderCallBack {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
