package android.bluetooth;

import android.content.AttributionSource;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothManager extends IInterface {
    IBluetooth registerAdapter(IBluetoothManagerCallback callback);
    void unregisterAdapter(IBluetoothManagerCallback callback);
    void registerStateChangeCallback(IBluetoothStateChangeCallback callback);
    void unregisterStateChangeCallback(IBluetoothStateChangeCallback callback);
    boolean enable(AttributionSource attributionSource);
    boolean enableNoAutoConnect(AttributionSource attributionSource);
    boolean disable(AttributionSource attributionSource, boolean persist);
    int getState();
    IBluetoothGatt getBluetoothGatt();
    boolean bindBluetoothProfileService(int profile, String serviceName, IBluetoothProfileServiceConnection proxy);
    void unbindBluetoothProfileService(int profile, IBluetoothProfileServiceConnection proxy);
    String getAddress(AttributionSource attributionSource);
    String getName(AttributionSource attributionSource);
    boolean onFactoryReset(AttributionSource attributionSource);
    boolean isBleScanAlwaysAvailable();
    boolean enableBle(AttributionSource attributionSource, IBinder b);
    boolean disableBle(AttributionSource attributionSource, IBinder b);
    boolean isBleAppPresent();
    boolean factoryReset();
    boolean isHearingAidProfileSupported();
    int setBtHciSnoopLogMode(int mode);
    int getBtHciSnoopLogMode();

    abstract class Stub extends Binder implements IBluetoothManager {
        public static IBluetoothManager asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
