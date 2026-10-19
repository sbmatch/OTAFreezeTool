package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothConnectionCallback extends IInterface {
    void onDeviceConnected(BluetoothDevice device);
    void onDeviceDisconnected(BluetoothDevice device, int hciReason);

    abstract class Stub extends Binder implements IBluetoothConnectionCallback{
        public static IBluetoothConnectionCallback asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
