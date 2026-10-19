package android.bluetooth;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

public interface IBluetoothPreferredAudioProfilesCallback extends IInterface {
    void onPreferredAudioProfilesChanged(BluetoothDevice device, Bundle preferredAudioProfiles, int status);

    abstract class Stub extends Binder implements IBluetoothPreferredAudioProfilesCallback{
        public static IBluetoothPreferredAudioProfilesCallback asInterface(IBinder obj){
            throw new RuntimeException("Stub!");
        }
    }
}
