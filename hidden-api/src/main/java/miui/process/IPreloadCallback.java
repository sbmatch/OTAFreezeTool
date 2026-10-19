package miui.process;

import android.os.IBinder;
import android.os.IInterface;

public interface IPreloadCallback extends IInterface {
    void onPreloadComplete(int type, int result);

    void onPreloadAppStarted(String packageName, int pid);

    abstract class Stub extends android.os.Binder implements IPreloadCallback {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }

}
