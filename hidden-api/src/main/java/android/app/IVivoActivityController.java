package android.app;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

public interface IVivoActivityController extends IInterface {
    boolean activityStarting(Intent intent, String pkg, Bundle options);

    abstract class Stub extends Binder implements IVivoActivityController {

        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }
    }
}
