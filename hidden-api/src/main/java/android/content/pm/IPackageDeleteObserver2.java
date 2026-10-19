package android.content.pm;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IPackageDeleteObserver2 extends IInterface {
    void onUserActionRequired(Intent intent);
    void onPackageDeleted(String packageName, int returnCode, String msg);

    abstract class Stub extends Binder implements IPackageDeleteObserver2 {
        public static IPackageDeleteObserver2 asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
