package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothManagerCallback extends IInterface {
    void onBluetoothServiceUp(IBluetooth bluetoothService);
    void onBluetoothServiceDown();
    void onBrEdrDown();

    abstract class Stub extends Binder implements IBluetoothManagerCallback {
        public static IBluetoothManagerCallback asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
