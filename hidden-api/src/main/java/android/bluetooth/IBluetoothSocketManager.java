package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.ParcelUuid;

public interface IBluetoothSocketManager extends IInterface {
    ParcelFileDescriptor connectSocket(BluetoothDevice device, int type, ParcelUuid uuid, int port, int flag);
    ParcelFileDescriptor createSocketChannel(int type, String serviceName, ParcelUuid uuid, int port, int flag);
    void requestMaximumTxDataLength(BluetoothDevice device);

    abstract class Stub extends Binder implements IBluetoothSocketManager{
        public static IBluetoothSocketManager asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
