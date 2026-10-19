package android.database;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IContentObserver extends IInterface {

    void onChange(boolean selfUpdate, Uri uri, int userId);
    void onChangeEtc(boolean selfUpdate, Uri[] uri, int flags, int userId);


    abstract class Stub extends Binder implements IContentObserver{
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
