package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IAppOpsStartedCallback extends IInterface {
    void opStarted(int op, int uid, String packageName, String attributionTag, int virtualDeviceId, int flags, int mode, int startedType, int attributionFlags, int attributionChainId);

    abstract class Stub extends Binder implements IAppOpsStartedCallback {

        public static IAppOpsStartedCallback asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
