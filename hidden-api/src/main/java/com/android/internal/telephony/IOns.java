package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;


public interface IOns extends IInterface {

    abstract class Stub extends Binder implements IOns {
        public static IOns asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
