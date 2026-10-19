package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IKeyGestureEventListener extends IInterface {
    void onKeyGestureEvent(AidlKeyGestureEvent event);

    abstract class Stub extends Binder implements IKeyGestureEventListener{
       public static IKeyGestureEventListener asInterface(android.os.IBinder obj) {
           throw new RuntimeException("Stub!");
       }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
