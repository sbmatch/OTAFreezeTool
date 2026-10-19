package com.vivo.services.security.client;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IVivoPermissionCallback extends IInterface {
    void onPermissionConfirmed(boolean result);

    abstract class Stub extends Binder implements IVivoPermissionCallback {

        public static IVivoPermissionCallback asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
