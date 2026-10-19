package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothCallback extends IInterface {
    void onBluetoothStateChange(int prevState, int newState);

    abstract class Stub extends Binder implements IBluetoothCallback{
        public static IBluetoothCallback asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
