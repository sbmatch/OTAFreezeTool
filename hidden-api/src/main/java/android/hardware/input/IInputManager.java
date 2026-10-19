package android.hardware.input;

import android.os.IBinder;
import android.os.IInterface;
import android.view.InputEvent;
import android.view.InputMonitor;

public interface IInputManager extends IInterface {
    boolean injectInputEvent(InputEvent ev, int mode);
    boolean injectInputEventToTarget(InputEvent ev, int mode, int targetUid);
    void registerKeyGestureEventListener(IKeyGestureEventListener listener);
    void unregisterKeyGestureEventListener(IKeyGestureEventListener listener);
    InputMonitor monitorGestureInput(IBinder token, String name, int displayId);

    abstract class Stub extends android.os.Binder implements IInputManager {
        public static IInputManager asInterface(android.os.IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
