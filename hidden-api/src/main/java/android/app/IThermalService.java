package android.app;

import android.os.IInterface;
import android.os.IThermalEventListener;

public interface IThermalService extends IInterface {
    boolean registerThermalEventListener(IThermalEventListener listener);
    boolean unregisterThermalEventListener(IThermalEventListener listener);

    abstract class Stub extends android.os.Binder implements IThermalService {
        public static IThermalService asInterface(android.os.IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
