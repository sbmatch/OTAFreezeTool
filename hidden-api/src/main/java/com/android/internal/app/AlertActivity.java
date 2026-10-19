package com.android.internal.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public abstract class AlertActivity extends Activity implements DialogInterface {


    public AlertActivity() {
    }

    /**
     * The model for the alert.
     *
     * @see #mAlertParams
     */
    protected AlertController mAlert;

    /**
     * The parameters for the alert.
     */
    protected AlertController.AlertParams mAlertParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        throw new RuntimeException("Stub!");
    }

    public void cancel() {
        throw new RuntimeException("Stub!");
    }
    public void dismiss() {
        throw new RuntimeException("Stub!");
    }


    /**
     * Sets up the alert, including applying the parameters to the alert model,
     * and installing the alert's content.
     *
     * @see #mAlert
     * @see #mAlertParams
     */
    protected void setupAlert() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
}
