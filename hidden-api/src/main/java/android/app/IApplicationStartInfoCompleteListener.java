package android.app;

import android.content.IIntentReceiver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

public interface IApplicationStartInfoCompleteListener extends IInterface {
    String DESCRIPTOR = "android.app.IApplicationStartInfoCompleteListener";
    void onApplicationStartInfoComplete(ApplicationStartInfo applicationStartInfo);

    abstract class Stub extends Binder implements IApplicationStartInfoCompleteListener {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
