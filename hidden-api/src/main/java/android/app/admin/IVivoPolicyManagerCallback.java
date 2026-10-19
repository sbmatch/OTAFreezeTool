package android.app.admin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IVivoPolicyManagerCallback extends IInterface {
    void onVivoPolicyChanged(int poId);

    abstract class Stub extends Binder implements IVivoPolicyManagerCallback {
        public static IVivoPolicyManagerCallback asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
