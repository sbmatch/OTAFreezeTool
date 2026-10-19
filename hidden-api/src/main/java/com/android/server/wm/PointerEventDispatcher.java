package com.android.server.wm;


import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.WindowManagerPolicyConstants;

import com.android.server.UiThread;

import java.util.ArrayList;

public class PointerEventDispatcher extends InputEventReceiver {
    private final ArrayList<WindowManagerPolicyConstants.PointerEventListener> mListeners;
    private WindowManagerPolicyConstants.PointerEventListener[] mListenersArray;

    public PointerEventDispatcher(InputChannel inputChannel) {
        super(inputChannel, UiThread.getHandler().getLooper());
        throw new RuntimeException("Stub!");
    }

    public void onInputEvent(InputEvent event) {
        throw new RuntimeException("Stub!");
    }

    public void registerInputEventListener(WindowManagerPolicyConstants.PointerEventListener listener) {
        throw new RuntimeException("Stub!");
    }

    public void unregisterInputEventListener(WindowManagerPolicyConstants.PointerEventListener listener) {
        throw new RuntimeException("Stub!");
    }

    public void dispose() {
        throw new RuntimeException("Stub!");
    }
}