package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface ITelephonyRegistry extends IInterface {

//    void addOnSubscriptionsChangedListener(String pkg, String featureId, IOnSubscriptionsChangedListener callback);
//    void addOnOpportunisticSubscriptionsChangedListener(String pkg, String featureId, IOnSubscriptionsChangedListener callback);
//    void removeOnSubscriptionsChangedListener(String pkg, IOnSubscriptionsChangedListener callback);
//    void listenWithEventList(boolean renounceFineLocationAccess,
//                             boolean renounceCoarseLocationAccess, int subId, String pkg, String featureId,
//                             IPhoneStateListener callback, int[] events, boolean notifyNow);
//    void addCarrierPrivilegesCallback(int phoneId, ICarrierPrivilegesCallback callback, String pkg, String featureId);
//    void removeCarrierPrivilegesCallback(ICarrierPrivilegesCallback callback, String pkg);

    abstract class Stub extends Binder implements ITelephonyRegistry {
        public static ITelephonyRegistry asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
