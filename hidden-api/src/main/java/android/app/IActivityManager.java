package android.app;

import android.content.IIntentReceiver;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;

public interface IActivityManager extends IInterface {

    int broadcastIntent(IApplicationThread caller, Intent intent, String resolvedType, IIntentReceiver resultTo, int resultCode, String resultData, Bundle map, String[] requiredPermissions, int appOp, Bundle options, boolean serialized, boolean sticky, int userId);
    void unbroadcastIntent(IApplicationThread caller, Intent intent, int userId);

    void forceStopPackage(String packageName, int userId);
    void registerProcessObserver(IProcessObserver observer);
    void unregisterProcessObserver(IProcessObserver observer);
    ParcelFileDescriptor openContentUri(String uriString);
    void removeContentProviderExternal(String name, IBinder token);
    ContentProviderHolder getContentProviderExternal(String name, int userId, IBinder token, String tag);

    void attachApplication(IApplicationThread app, long startSeq);

    abstract class Stub extends Binder implements IActivityManager {

        public static IActivityManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
