package android.hardware.usb;

import android.os.Bundle;
import android.os.IInterface;

public interface IUsbManager extends IInterface {
    /* Returns a list of all currently attached USB devices */
    void getDeviceList(Bundle devices);

    abstract class Stub extends android.os.Binder implements IUsbManager {
        public static IUsbManager asInterface(android.os.IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
