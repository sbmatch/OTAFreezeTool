package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
public interface ITelephony extends IInterface {

    int getSimStateForSlotIndex(int slotIndex);
    int getNetworkTypeForSubscriber(int subId, String callingPackage, String callingFeatureId);

    /**
     * Register an IMS connection state callback
     */
    //void registerImsStateCallback(int subId, int feature, IImsStateCallback cb, String callingPackage);

    /**
     * Unregister an IMS connection state callback
     */
    //void unregisterImsStateCallback(IImsStateCallback cb);

    /**
     * Register an IMS provisioning change callback with Telephony.
     */
    //void registerImsProvisioningChangedCallback(int subId, IImsConfigCallback callback);

    /**
     * unregister an existing IMS provisioning change callback.
     */
    //void unregisterImsProvisioningChangedCallback(int subId, IImsConfigCallback callback);

    /**
     * Adds an IMS registration status callback for the subscription id specified.
     */
    //void registerImsRegistrationCallback(int subId, IImsRegistrationCallback c);
    /**
     * Removes an existing IMS registration status callback for the subscription specified.
     */
    //void unregisterImsRegistrationCallback(int subId, IImsRegistrationCallback c);


    abstract class Stub extends Binder implements ITelephony {
        public static ITelephony asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
