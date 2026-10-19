package com.android.modules.utils;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface ISynchronousResultReceiver extends IInterface {
    void send(SynchronousResultReceiver.Result result);

    abstract class Stub extends Binder implements ISynchronousResultReceiver{
        public static ISynchronousResultReceiver asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
