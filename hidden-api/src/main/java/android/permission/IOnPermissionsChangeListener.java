package android.permission;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IOnPermissionsChangeListener extends IInterface {


    void onPermissionsChanged(int uid);
    void onPermissionsChanged(int uid, String persistentDeviceId);

    abstract class Stub extends Binder implements IOnPermissionsChangeListener {
        public static IOnPermissionsChangeListener asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
