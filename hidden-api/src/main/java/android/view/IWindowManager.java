package android.view;

import android.os.IInterface;
import android.window.ScreenCapture;

public interface IWindowManager extends IInterface {

    void captureDisplay(int displayId, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener listener);

    void registerScreenStateListener(IScreenStateListener listener);
    void unregisterScreenStateListener(IScreenStateListener listener);

    abstract class Stub extends android.os.Binder implements IWindowManager {
        public static IWindowManager asInterface(android.os.IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }

}
