package android.app.admin;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

public interface IVivoCallStateCallback extends IInterface {
    void onVivoCallStateChanged(Bundle bundle);

    abstract class Stub extends Binder implements IVivoCallStateCallback {
        public static IVivoCallStateCallback asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
