package android.hardware.input;

import android.os.IBinder;
import android.util.SparseArray;
import android.view.InputDevice;
import android.view.InputEvent;

/**
 * Manages communication with the input manager service on behalf of
 * an application process. You're probably looking for {@link InputManager}.
 *
 */
public final class InputManagerGlobal {
    private static final String TAG = "InputManagerGlobal";
    // To enable these logs, run: 'adb shell setprop log.tag.InputManagerGlobal DEBUG'
    private boolean debug() {
        throw new RuntimeException("Stub!");
    }

    private SparseArray<InputDevice> mInputDevices;


    // InputDeviceSensorManager gets notified synchronously from the binder thread when input
    // devices change, so it must be synchronized with the input device listeners.

    private static InputManagerGlobal sInstance;

    private final String mVelocityTrackerStrategy;

    private final IInputManager mIm;

    public InputManagerGlobal(IInputManager im) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Gets an instance of the input manager global singleton.
     *
     * @return The input manager instance, may be null early in system startup
     * before the input manager has been fully initialized.
     */
    public static InputManagerGlobal getInstance() {
        throw new RuntimeException("Stub!");
    }

    public IInputManager getInputManagerService() {
        throw new RuntimeException("Stub!");
    }
    
    /**
     * @see InputManager#getInputDevice(int)
     */
    
    public InputDevice getInputDevice(int id) {
        throw new RuntimeException("Stub!");
    }

    
    private void populateInputDevicesLocked() {
        throw new RuntimeException("Stub!");
    }
    

    private static boolean containsDeviceId(int[] deviceIdAndGeneration, int deviceId) {
        throw new RuntimeException("Stub!");
    }

    
    private void sendMessageToInputDeviceListenersLocked(int what, int deviceId) {
        throw new RuntimeException("Stub!");
    }
    
  
    /**
     * @see InputManager#injectInputEvent(InputEvent, int, int)
     */

    public boolean injectInputEvent(InputEvent event, int mode, int targetUid) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @see InputManager#injectInputEvent(InputEvent, int)
     */
    public boolean injectInputEvent(InputEvent event, int mode) {
        throw new RuntimeException("Stub!");
    }


    /**
     * @see android.view.View#requestPointerCapture()
     */
    public void requestPointerCapture(IBinder windowToken, int mode) {
        // We need to check the mode is valid here too, since the binder call is oneway, so the
        // check in InputManagerService#requestPointerCapture would not result in the exception
        // being propagated to the caller.
        throw new RuntimeException("Stub!");
    }


    public void addUniqueIdAssociationByPort(String inputPort, String displayUniqueId) {
        throw new RuntimeException("Stub!");
    }


    public void removeUniqueIdAssociationByPort(String inputPort) {
        throw new RuntimeException("Stub!");
    }

    public void addUniqueIdAssociationByDescriptor(String inputDeviceDescriptor, String displayUniqueId) {
        throw new RuntimeException("Stub!");
    }

    public void removeUniqueIdAssociationByDescriptor(String inputDeviceDescriptor) {
        throw new RuntimeException("Stub!");
    }

    public String getInputDeviceBluetoothAddress(int deviceId) {
        throw new RuntimeException("Stub!");
    }

    public void cancelCurrentTouch() {
        throw new RuntimeException("Stub!");
    }

}
