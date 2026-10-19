package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface INetworkPolicyManager extends IInterface {

    void setUidPolicy(int uid, int policy);
    void addUidPolicy(int uid, int policy);
    void removeUidPolicy(int uid, int policy);

    int getUidPolicy(int uid);
    int[] getUidsWithPolicy(int policy);
    void registerListener(INetworkPolicyListener listener);
    void unregisterListener(INetworkPolicyListener listener);

    /** Gets the restrict background status based on the caller's UID:
     1 - disabled
     2 - whitelisted
     3 - enabled
     */
    int getRestrictBackgroundStatus(int uid);
    boolean isUidNetworkingBlocked(int uid, boolean meteredNetwork);

    void setNetworkPolicies(NetworkPolicy[] policies);

    void setWifiMeteredOverride(String networkId, int meteredOverride);
    NetworkPolicy[] getNetworkPolicies(String callingPackage);

    abstract class Stub extends Binder implements INetworkPolicyManager {
        public static INetworkPolicyManager asInterface(IBinder binder){
            throw new RuntimeException("Stub!");
        }
    }

}
