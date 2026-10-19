package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothMetadataListener extends IInterface {
    void onMetadataChanged(BluetoothDevice devices, int key, byte[] value);

    abstract class Stub extends Binder implements IBluetoothMetadataListener{
        public static IBluetoothMetadataListener asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
