package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothQualityReportReadyCallback extends IInterface {
    void onBluetoothQualityReportReady(BluetoothDevice device, BluetoothQualityReport bluetoothQualityReport, int status);

    abstract class Stub extends Binder implements IBluetoothQualityReportReadyCallback {
        public static IBluetoothQualityReportReadyCallback asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
