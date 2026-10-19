package android.bluetooth;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothProfileServiceConnection extends IInterface {
    void onServiceDisconnected(ComponentName comp);
    void onServiceConnected(ComponentName comp, IBinder service);

    abstract class Stub extends Binder implements IBluetoothProfileServiceConnection{
        public static IBluetoothProfileServiceConnection asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
