package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothActivityEnergyInfoListener extends IInterface {
    void onBluetoothActivityEnergyInfoAvailable(BluetoothActivityEnergyInfo info);

    abstract class Stub extends Binder implements IBluetoothActivityEnergyInfoListener{
        public static IBluetoothActivityEnergyInfoListener asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
