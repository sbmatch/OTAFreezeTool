package android.content;

import android.database.IContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IContentService extends IInterface {

    void registerContentObserver(Uri uri, boolean notifyForDescendants, IContentObserver observer, int userHandle, int targetSdkVersion);
    void unregisterContentObserver(IContentObserver observer);

    abstract class Stub extends Binder implements IContentService {

        public static IContentService asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
