package miui.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IMiuiInputManager extends IInterface {
    void unregisterMiuiMotionEventListener();
    void registerMiuiMotionEventListener(IMiuiMotionEventListener listener);

    abstract class Stub extends Binder implements IMiuiInputManager{
        public static IMiuiInputManager asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
