package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothStateChangeCallback extends IInterface {
    void onBluetoothStateChange(boolean on);

    abstract class Stub extends Binder implements IBluetoothStateChangeCallback{
        public static IBluetoothStateChangeCallback asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
