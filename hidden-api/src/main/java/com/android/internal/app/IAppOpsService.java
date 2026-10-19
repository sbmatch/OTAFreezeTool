package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IAppOpsService extends IInterface {

    int checkOperation(int code, int uid, String packageName);
    void setUidMode(int code, int uid, int mode);
    void setMode(int code, int uid, String packageName, int mode);
    void stopWatchingStarted(IAppOpsStartedCallback callback);
    void startWatchingStarted(int[] ops, IAppOpsStartedCallback callback);

    int permissionToOpCode(String permission);
    void stopWatchingNoted(IAppOpsNotedCallback callback);
    void startWatchingNoted(int[] ops, IAppOpsNotedCallback callback);


    abstract class Stub extends Binder implements IAppOpsService {

        public static IAppOpsService asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
