package miui.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.view.MotionEvent;

public interface IMiuiMotionEventListener extends IInterface {
    void onMiuiMotionEvent(MotionEvent event);

    abstract class Stub extends Binder implements IMiuiMotionEventListener {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
