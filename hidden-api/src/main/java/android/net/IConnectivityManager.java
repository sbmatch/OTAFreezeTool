package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IConnectivityManager extends IInterface {

    void registerQosSocketCallback(QosSocketInfo socketInfo, IQosCallback callback);
    void unregisterQosCallback(IQosCallback callback);
    Network getActiveNetwork();
    NetworkInfo[] getAllNetworkInfo();

    abstract class Stub extends Binder implements IConnectivityManager {

        public static IConnectivityManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
