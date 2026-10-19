package android.view;

import android.os.Looper;

public abstract class InputEventReceiver {
    /**
     * Creates an input event receiver bound to the specified input channel.
     *
     * @param inputChannel The input channel.
     * @param looper The looper to use when invoking callbacks.
     */
    public InputEventReceiver(InputChannel inputChannel, Looper looper) {
        throw new RuntimeException("Stub!");
    }
}
