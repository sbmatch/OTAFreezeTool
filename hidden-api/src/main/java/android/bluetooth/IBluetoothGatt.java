package android.bluetooth;

import android.bluetooth.le.IScannerCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothGatt extends IInterface {
    void registerStatisticsClient(IScannerCallback callback);
    void unregisterStatisticsClient(IScannerCallback callback);

    abstract class Stub extends Binder implements IBluetoothGatt{
        public static IBluetoothGatt asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
