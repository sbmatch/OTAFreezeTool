package android.net;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.os.IBinder;
import android.telephony.SubscriptionPlan;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class NetworkPolicyManager {
    /* POLICY_* are masks and can be ORed, although currently they are not.*/
    /**
     * No specific network policy, use system default.
     * @hide
     */
    public static final int POLICY_NONE = 0x0;
    /**
     * Reject network usage on metered networks when application in background.
     * @hide
     */
    public static final int POLICY_REJECT_METERED_BACKGROUND = 0x1;
    /**
     * Allow metered network use in the background even when in data usage save mode.
     * @hide
     */
    public static final int POLICY_ALLOW_METERED_BACKGROUND = 0x4;

    /*
     * Rules defining whether an uid has access to a network given its type (metered / non-metered).
     *
     * These rules are bits and can be used in bitmask operations; in particular:
     * - rule & RULE_MASK_METERED: returns the metered-networks status.
     * - rule & RULE_MASK_ALL: returns the all-networks status.
     *
     * The RULE_xxx_ALL rules applies to all networks (metered or non-metered), but on
     * metered networks, the RULE_xxx_METERED rules should be checked first. For example,
     * if the device is on Battery Saver Mode and Data Saver Mode simulatenously, and a uid
     * is allowlisted for the former but not the latter, its status would be
     * RULE_REJECT_METERED | RULE_ALLOW_ALL, meaning it could have access to non-metered
     * networks but not to metered networks.
     *
     * See network-policy-restrictions.md for more info.
     */

    /**
     * No specific rule was set
     * @hide
     */
    public static final int RULE_NONE = 0;
    /**
     * Allow traffic on metered networks.
     * @hide
     */
    public static final int RULE_ALLOW_METERED = 1 << 0;
    /**
     * Temporarily allow traffic on metered networks because app is on foreground.
     * @hide
     */
    public static final int RULE_TEMPORARY_ALLOW_METERED = 1 << 1;
    /**
     * Reject traffic on metered networks.
     * @hide
     */
    public static final int RULE_REJECT_METERED = 1 << 2;
    /**
     * Network traffic should be allowed on all networks (metered or non-metered), although
     * metered-network restrictions could still apply.
     * @hide
     */
    public static final int RULE_ALLOW_ALL = 1 << 5;
    /**
     * Reject traffic on all networks.
     * @hide
     */
    public static final int RULE_REJECT_ALL = 1 << 6;
    /**
     * Reject traffic on all networks for restricted networking mode.
     * @hide
     */
    public static final int RULE_REJECT_RESTRICTED_MODE = 1 << 10;

    /**
     * Mask used to get the {@code RULE_xxx_METERED} rules
     * @hide
     */
    public static final int MASK_METERED_NETWORKS = 0b000000001111;
    /**
     * Mask used to get the {@code RULE_xxx_ALL} rules
     * @hide
     */
    public static final int MASK_ALL_NETWORKS     = 0b000011110000;
    /**
     * Mask used to get the {@code RULE_xxx_RESTRICTED_MODE} rules
     * @hide
     */
    public static final int MASK_RESTRICTED_MODE_NETWORKS     = 0b111100000000;

    /** @hide */
    public static final int FIREWALL_RULE_DEFAULT = 0;
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_NONE = "none";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_DOZABLE = "dozable";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_STANDBY = "standby";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_POWERSAVE = "powersave";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_RESTRICTED = "restricted";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_LOW_POWER_STANDBY = "low_power_standby";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_BACKGROUND = "background";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_METERED_ALLOW = "metered_allow";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_METERED_DENY_USER = "metered_deny_user";
    /** @hide */
    public static final String FIREWALL_CHAIN_NAME_METERED_DENY_ADMIN = "metered_deny_admin";

    private static final boolean ALLOW_PLATFORM_APP_POLICY = true;

    /** @hide */
    public static int FOREGROUND_THRESHOLD_STATE;

    /** @hide */
    public static int TOP_THRESHOLD_STATE;

    /** @hide */
    public static int BACKGROUND_THRESHOLD_STATE;

    /**
     * {@link Intent} extra that indicates which {@link NetworkTemplate} rule it
     * applies to.
     * @hide
     */
    public static final String EXTRA_NETWORK_TEMPLATE = "android.net.NETWORK_TEMPLATE";

    /**
     * Mask used to check if an override value is marked as unmetered.
     * @hide
     */
    public static int SUBSCRIPTION_OVERRIDE_UNMETERED;

    /**
     * Mask used to check if an override value is marked as congested.
     * @hide
     */
    public static final int SUBSCRIPTION_OVERRIDE_CONGESTED = 1 << 1;

    /**
     * @hide
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface SubscriptionOverrideMask {}

    /**
     * Flag to indicate that app is not exempt from any network restrictions.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_NONE = 0;
    /**
     * Flag to indicate that app is exempt from certain network restrictions because of it being a
     * system component.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_SYSTEM = 1 << 0;
    /**
     * Flag to indicate that app is exempt from certain network restrictions because of it being
     * in the foreground.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_FOREGROUND = 1 << 1;
    /**
     * Flag to indicate that app is exempt from certain network restrictions because of it being
     * in the {@code allow-in-power-save} list.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_POWER_SAVE_ALLOWLIST = 1 << 2;
    /**
     * Flag to indicate that app is exempt from certain network restrictions because of it being
     * in the {@code allow-in-power-save-except-idle} list.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_POWER_SAVE_EXCEPT_IDLE_ALLOWLIST = 1 << 3;
    /**
     * Flag to indicate that app is exempt from certain network restrictions because of it holding
     * certain privileged permissions.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_RESTRICTED_MODE_PERMISSIONS = 1 << 4;
    /**
     * Flag to indicate that app is exempt from certain network restrictions because of it being
     * in the bound top or top procstate.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_TOP = 1 << 5;
    /**
     * Flag to indicate that app is exempt from low power standby restrictions because of it being
     * allowlisted.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_LOW_POWER_STANDBY_ALLOWLIST = 1 << 6;

    /**
     * Flag to indicate that the app is exempt from always-on background network restrictions.
     * Note that this is explicitly different to the flag NOT_FOREGROUND which is used to grant
     * shared exception to apps from power restrictions like doze, battery saver and app-standby.
     *
     * @hide
     */
    public static final int ALLOWED_REASON_NOT_IN_BACKGROUND = 1 << 7;

    /**
     * Flag to indicate that app is exempt from certain metered network restrictions because user
     * explicitly exempted it.
     *
     * @hide
     */
    public static final int ALLOWED_METERED_REASON_USER_EXEMPTED = 1 << 16;
    /**
     * Flag to indicate that app is exempt from certain metered network restrictions because of it
     * being a system component.
     *
     * @hide
     */
    public static final int ALLOWED_METERED_REASON_SYSTEM = 1 << 17;
    /**
     * Flag to indicate that app is exempt from certain metered network restrictions because of it
     * being in the foreground.
     *
     * @hide
     */
    public static final int ALLOWED_METERED_REASON_FOREGROUND = 1 << 18;

    /** @hide */
    public static final int ALLOWED_METERED_REASON_MASK = 0xffff0000;

    private Context mContext;

    private final Map<SubscriptionCallback, SubscriptionCallbackProxy>
            mSubscriptionCallbackMap = new ConcurrentHashMap<>();
    private final Map<NetworkPolicyCallback, NetworkPolicyCallbackProxy>
            mNetworkPolicyCallbackMap = new ConcurrentHashMap<>();



    /** @hide */
    public static NetworkPolicyManager from(Context context) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Set policy flags for specific UID.
     *
     * @param policy should be {@link #POLICY_NONE} or any combination of {@code POLICY_} flags,
     *     although it is not validated.
     * @hide
     */

    public void setUidPolicy(int uid, int policy) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Add policy flags for specific UID.
     *
     * <p>The given policy bits will be set for the uid.
     *
     * @param policy should be {@link #POLICY_NONE} or any combination of {@code POLICY_} flags,
     *     although it is not validated.
     * @hide
     */
    public void addUidPolicy(int uid, int policy) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Clear/remove policy flags for specific UID.
     *
     * <p>The given policy bits will be set for the uid.
     *
     * @param policy should be {@link #POLICY_NONE} or any combination of {@code POLICY_} flags,
     *     although it is not validated.
     * @hide
     */
    public void removeUidPolicy(int uid, int policy) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public int getUidPolicy(int uid) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public int[] getUidsWithPolicy(int policy) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public void registerListener(INetworkPolicyListener listener) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public void unregisterListener(INetworkPolicyListener listener) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public void registerSubscriptionCallback(SubscriptionCallback callback) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */

    public void unregisterSubscriptionCallback(SubscriptionCallback callback) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public void setNetworkPolicies(NetworkPolicy[] policies) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */

    public NetworkPolicy[] getNetworkPolicies() {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public void setRestrictBackground(boolean restrictBackground) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public boolean getRestrictBackground() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Determines if an UID is subject to metered network restrictions while running in background.
     *
     * @param uid The UID whose status needs to be checked.
     * @return {@link ConnectivityManager#RESTRICT_BACKGROUND_STATUS_DISABLED},
     *         {@link ConnectivityManager#RESTRICT_BACKGROUND_STATUS_ENABLED},
     *         or {@link ConnectivityManager#RESTRICT_BACKGROUND_STATUS_WHITELISTED} to denote
     *         the current status of the UID.
     * @hide
     */

    public int getRestrictBackgroundStatus(int uid) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Override connections to be temporarily marked as either unmetered or congested,
     * along with automatic timeouts if desired.
     *
     * @param subId the subscriber ID this override applies to.
     * @param overrideMask the bitmask that specifies which of the overrides is being
     *            set or cleared.
     * @param overrideValue the override values to set or clear.
     * @param networkTypes the network types this override applies to. If no
     *            network types are specified, override values will be ignored.
     *            {@see TelephonyManager#getAllNetworkTypes()}
     * @param expirationDurationMillis the duration after which the requested override
     *            will be automatically cleared, or {@code 0} to leave in the
     *            requested state until explicitly cleared, or the next reboot,
     *            whichever happens first
     * @param callingPackage the name of the package making the call.
     * @hide
     */
    public void setSubscriptionOverride(int subId, @SubscriptionOverrideMask int overrideMask,
                                        @SubscriptionOverrideMask int overrideValue,
                                        int[] networkTypes, long expirationDurationMillis,
                                        String callingPackage) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Set the subscription plans for a specific subscriber.
     *
     * @param subId the subscriber this relationship applies to.
     * @param plans the list of plans.
     * @param expirationDurationMillis the duration after which the subscription plans
     *            will be automatically cleared, or {@code 0} to leave the plans until
     *            explicitly cleared, or the next reboot, whichever happens first
     * @param callingPackage the name of the package making the call
     * @hide
     */
    public void setSubscriptionPlans(int subId, SubscriptionPlan[] plans,
                                     long expirationDurationMillis, String callingPackage) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get subscription plans for the given subscription id.
     *
     * @param subId the subscriber to get the subscription plans for.
     * @param callingPackage the name of the package making the call.
     * @return the active {@link SubscriptionPlan}s for the given subscription id, or
     *         {@code null} if not found.
     * @hide
     */

    public SubscriptionPlan[] getSubscriptionPlans(int subId, String callingPackage) {
        throw new RuntimeException("Stub!");
    }


    /**
     * Notifies that the specified {@link NetworkStatsProvider} has reached its warning threshold
     * which was set through {@link NetworkStatsProvider#onSetWarningAndLimit(String, long, long)}.
     *
     * @hide
     */
    public void notifyStatsProviderWarningReached() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Notifies that the specified {@link NetworkStatsProvider} has reached its quota
     * which was set through {@link NetworkStatsProvider#onSetLimit(String, long)} or
     * {@link NetworkStatsProvider#onSetWarningAndLimit(String, long, long)}.
     *
     * @hide
     */

    public void notifyStatsProviderLimitReached() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Resets network policy settings back to factory defaults.
     *
     * @hide
     */
    public void factoryReset(String subscriber) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Check that networking is blocked for the given uid.
     *
     * @param uid The target uid.
     * @param meteredNetwork True if the network is metered.
     * @return true if networking is blocked for the given uid according to current networking
     *         policies.
     */
    public boolean isUidNetworkingBlocked(int uid, boolean meteredNetwork) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Check that the given uid is restricted from doing networking on metered networks.
     *
     * @param uid The target uid.
     * @return true if the given uid is restricted from doing networking on metered networks.
     */

    public boolean isUidRestrictedOnMeteredNetworks(int uid) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Gets a hint on whether it is desirable to use multipath data transfer on the given network.
     *
     * @return One of the ConnectivityManager.MULTIPATH_PREFERENCE_* constants.
     *
     * @hide
     */
    public int getMultipathPreference(Network network) {
        throw new RuntimeException("Stub!");
    }


    /**
     * @hide
     */
    public static String uidRulesToString(int uidRules) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     */
    public static String uidPoliciesToString(int uidPolicies) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns the default network capabilities
     * ({@link ActivityManager#PROCESS_CAPABILITY_POWER_RESTRICTED_NETWORK
     * ActivityManager.PROCESS_CAPABILITY_*}) of the specified process state.
     * This <b>DOES NOT</b> return all default process capabilities for a proc state.
     * @hide
     */

    public static int getDefaultProcessNetworkCapabilities(int procState) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns true if {@code procState} is considered foreground and as such will be allowed
     * to access network when the device is idle or in battery saver mode. Otherwise, false.
     * @hide
     */
    public static boolean isProcStateAllowedWhileIdleOrPowerSaveMode(UidState uidState) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public static boolean isProcStateAllowedWhileIdleOrPowerSaveMode(int procState, int capability) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public static boolean isProcStateAllowedWhileInLowPowerStandby(UidState uidState) {
        throw new RuntimeException("Stub!");
    }

    /**
     * This is currently only used as an implementation detail for
     * {@link com.android.server.net.NetworkPolicyManagerService}.
     * Only put here to be together with other isProcStateAllowed* methods.
     *
     * @hide
     */
    public static boolean isProcStateAllowedNetworkWhileBackground(UidState uidState) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns true if {@code procState} is considered foreground and as such will be allowed
     * to access network when the device is in data saver mode. Otherwise, false.
     * @hide
     */
    public static boolean isProcStateAllowedWhileOnRestrictBackground(UidState uidState) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public static boolean isProcStateAllowedWhileOnRestrictBackground(int procState, int capabilities) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public static final class UidState {
        public int uid;
        public int procState;
        public long procStateSeq;
        public int capability;

        public UidState(int uid, int procState,
                        long procStateSeq, int capability) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }

    /** @hide */
    public static String resolveNetworkId(WifiConfiguration config) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public static String resolveNetworkId(String ssid) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns the {@code string} representation of {@code blockedReasons} argument.
     *
     * @param blockedReasons Value indicating the reasons for why the network access of an UID is
     *                       blocked.
     * @hide
     */
    public static String blockedReasonsToString(int blockedReasons) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public static String allowedReasonsToString(int allowedReasons) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Register a {@link NetworkPolicyCallback} to listen for changes to network blocked status
     * of apps.
     *
     * Note that when a caller tries to register a new callback, it might replace a previously
     * registered callback if it is considered equal to the new one, based on the
     * {@link Object#equals(Object)} check.
     *
     * @param executor The {@link Executor} to run the callback on.
     * @param callback The {@link NetworkPolicyCallback} to be registered.
     * @hide
     *
     * @deprecated This API is only supported up to Android version
     * {@link Build.VERSION_CODES#VANILLA_ICE_CREAM}. On later versions,
     * {@link android.net.ConnectivityManager.NetworkCallback} should be used wherever possible.
     *
     * @throws UnsupportedOperationException when called on Android versions after
     *                                       {@link Build.VERSION_CODES#VANILLA_ICE_CREAM}.
     */
    @Deprecated
    public void registerNetworkPolicyCallback(Executor executor,
                                             NetworkPolicyCallback callback) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Unregister a previously registered {@link NetworkPolicyCallback}.
     *
     * @param callback The {@link NetworkPolicyCallback} to be unregistered.
     * @hide
     *
     * @deprecated This API is only supported up to Android version
     * {@link Build.VERSION_CODES#VANILLA_ICE_CREAM}. On later versions,
     * {@link android.net.ConnectivityManager.NetworkCallback} should be used wherever possible.
     *
     * @throws UnsupportedOperationException when called on Android versions after
     *                                       {@link Build.VERSION_CODES#VANILLA_ICE_CREAM}.
     */
    @Deprecated
    public void unregisterNetworkPolicyCallback(NetworkPolicyCallback callback) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Interface for the callback to listen for changes to network blocked status of apps.
     *
     * This API is only supported up to Android version
     * {@link Build.VERSION_CODES#VANILLA_ICE_CREAM}. On later versions, this callback will
     * <b>not</b> be called when the network blocked status of an app changes. Instead,
     * {@link android.net.ConnectivityManager.NetworkCallback} should be used wherever possible.
     *
     * @see #registerNetworkPolicyCallback(Executor, NetworkPolicyCallback)
     * @see #unregisterNetworkPolicyCallback(NetworkPolicyCallback)
     *
     * @hide
     */
   
    public interface NetworkPolicyCallback {
        /**
         * Called when the reason for why the network access of an UID is blocked changes.
         *
         * @param uid The UID for which the blocked status changed.
         * @param blockedReasons Value indicating the reasons for why the network access of an
         *                       UID is blocked.
         * @hide
         */
        
        default void onUidBlockedReasonChanged(int uid, int blockedReasons) {}
    }

    /** @hide */
    public static class NetworkPolicyCallbackProxy extends Listener {
        private final Executor mExecutor;
        private final NetworkPolicyCallback mCallback;

        NetworkPolicyCallbackProxy(Executor executor, NetworkPolicyCallback callback) {
            mExecutor = executor;
            mCallback = callback;
        }

        @Override
        public void onBlockedReasonChanged(int uid, int oldBlockedReasons, int newBlockedReasons) {
            throw new RuntimeException("Stub!");
        }
    }

   

    /** @hide */
    public static class SubscriptionCallback {
        /**
         * Notify clients of a new override about a given subscription.
         *
         * @param subId the subscriber this override applies to.
         * @param overrideMask a bitmask that specifies which of the overrides is set.
         * @param overrideValue a bitmask that specifies the override values.
         * @param networkTypes the network types this override applies to.
         */
        public void onSubscriptionOverride(int subId, @SubscriptionOverrideMask int overrideMask,
                                           @SubscriptionOverrideMask int overrideValue, int[] networkTypes) {}

        /**
         * Notify of subscription plans change about a given subscription.
         *
         * @param subId the subscriber id that got subscription plans change.
         * @param plans the list of subscription plans.
         */
        public void onSubscriptionPlansChanged(int subId, SubscriptionPlan[] plans) {}
    }

    /**
     * SubscriptionCallback proxy for SubscriptionCallback object.
     * @hide
     */
    public class SubscriptionCallbackProxy extends Listener {
        private final SubscriptionCallback mCallback;

        SubscriptionCallbackProxy(SubscriptionCallback callback) {
            mCallback = callback;
        }

        @Override
        public void onSubscriptionOverride(int subId, @SubscriptionOverrideMask int overrideMask,
                                           @SubscriptionOverrideMask int overrideValue, int[] networkTypes) {
            mCallback.onSubscriptionOverride(subId, overrideMask, overrideValue, networkTypes);
        }

        @Override
        public void onSubscriptionPlansChanged(int subId, SubscriptionPlan[] plans) {
            mCallback.onSubscriptionPlansChanged(subId, plans);
        }
    }

    /** @hide */
    public static class Listener extends INetworkPolicyListener.Stub {
        @Override public void onUidRulesChanged(int uid, int uidRules) { }
        @Override public void onMeteredIfacesChanged(String[] meteredIfaces) { }
        @Override public void onRestrictBackgroundChanged(boolean restrictBackground) { }
        @Override public void onUidPoliciesChanged(int uid, int uidPolicies) { }
        @Override public void onSubscriptionOverride(int subId, int overrideMask,
                                                     int overrideValue, int[] networkTypes) { }
        @Override public void onSubscriptionPlansChanged(int subId, SubscriptionPlan[] plans) { }
        @Override public void onBlockedReasonChanged(int uid,
                                                     int oldBlockedReasons, int newBlockedReasons) { }
    }
}
