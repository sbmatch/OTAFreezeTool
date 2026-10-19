package android.view;

import android.os.IBinder;
import android.os.IInterface;

public interface IScreenStateListener extends IInterface {
    void onScreenTurningOff(int displayId);

    abstract class Stub extends android.os.Binder implements IScreenStateListener {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
