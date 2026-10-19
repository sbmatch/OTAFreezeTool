package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IAppOpsNotedCallback extends IInterface {

    void opNoted(int op, int uid, String packageName, String attributionTag, int virtualDeviceId, int flags, int mode);

    abstract class Stub extends Binder implements IAppOpsNotedCallback {
        public static IAppOpsNotedCallback asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
