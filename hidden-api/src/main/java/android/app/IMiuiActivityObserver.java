package android.app;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

public interface IMiuiActivityObserver extends IInterface {
    String DESCRIPTOR = "android.app.IMiuiActivityObserver";
    void activityDestroyed(Intent intent) throws RemoteException;

    void activityIdle(Intent intent) throws RemoteException;

    void activityPaused(Intent intent) throws RemoteException;

    void activityResumed(Intent intent) throws RemoteException;

    void activityStopped(Intent intent) throws RemoteException;

    abstract class Stub extends Binder implements IMiuiActivityObserver {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
