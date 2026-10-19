package com.android.internal.net;

import android.app.IActivityController;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IOemNetd extends IInterface {
    boolean setMiuiFirewallRule(String packageName, int uid, int rule, int type);

    abstract class Stub extends Binder implements IOemNetd {

        public static IOemNetd asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
