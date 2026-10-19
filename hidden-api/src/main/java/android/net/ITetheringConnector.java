package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface ITetheringConnector extends IInterface {
    void startTethering(TetheringRequestParcel request, String callerPkg,
                        String callingAttributionTag, IIntResultListener receiver);

    void stopTethering(int type, String callerPkg, String callingAttributionTag,
                       IIntResultListener receiver);

    void registerTetheringEventCallback(ITetheringEventCallback callback, String callerPkg);

    void unregisterTetheringEventCallback(ITetheringEventCallback callback, String callerPkg);

    abstract class Stub extends Binder implements ITetheringConnector{
        public static ITetheringConnector asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
