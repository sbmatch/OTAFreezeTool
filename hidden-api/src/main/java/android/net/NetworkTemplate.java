/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.net;

import android.net.wifi.WifiInfo;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Predicate used to match {@link NetworkIdentity}, usually when collecting
 * statistics. (It should probably have been named {@code NetworkPredicate}.)
 *
 * @hide
 */
public final class NetworkTemplate implements Parcelable {
    private static final String TAG = NetworkTemplate.class.getSimpleName();

    /** @hide */
    @Retention(RetentionPolicy.SOURCE)
    public @interface TemplateMatchRule{}

    /** Match rule to match cellular networks with given Subscriber Ids. */
    public static final int MATCH_MOBILE = 1;
    /** Match rule to match wifi networks. */
    public static final int MATCH_WIFI = 4;
    /** Match rule to match ethernet networks. */
    public static final int MATCH_ETHERNET = 5;
    /** Match rule to match bluetooth networks. */
    public static final int MATCH_BLUETOOTH = 8;
    /**
     * Match rule to match networks with {@link ConnectivityManager#TYPE_PROXY} as the legacy
     * network type.
     */
    public static final int MATCH_PROXY = 9;
    /**
     * Match rule to match all networks with subscriberId inside the template. Some carriers
     * may offer non-cellular networks like WiFi, which will be matched by this rule.
     */
    public static final int MATCH_CARRIER = 10;
    /**
     * Match rule to match networks with {@link ConnectivityManager#TYPE_TEST} as the legacy
     * network type.
     */
    public static final int MATCH_TEST = 11;
    /**
     * Match rule to match networks with other filters inside the template.
     *
     * Note that exactly one transport type is needed to build the template with this match rule.
     * @hide
     */
    public static final int MATCH_ALL = 12;

    // TODO: Remove this and replace all callers with WIFI_NETWORK_KEY_ALL.
    /** @hide */
    public static final String WIFI_NETWORKID_ALL = null;

    /**
     * Wi-Fi Network Key is never supposed to be null (if it is, it is a bug that
     * should be fixed), so it's not possible to want to match null vs
     * non-null. Therefore it's fine to use null as a sentinel for Wifi Network Key.
     *
     * @hide
     */
    public static final String WIFI_NETWORK_KEY_ALL = WIFI_NETWORKID_ALL;

    /**
     * Include all network types when filtering. This is meant to merge in with the
     * {@code TelephonyManager.NETWORK_TYPE_*} constants, and thus needs to stay in sync.
     */
    public static final int NETWORK_TYPE_ALL = -1;

    /** @hide */
    public static final long TRANSPORT_TYPES_ALL = 0L;

    /** @hide */
    @Retention(RetentionPolicy.SOURCE)
    public @interface OemManaged{}

    /**
     * Value to match both OEM managed and unmanaged networks (all networks).
     */
    public static final int OEM_MANAGED_ALL = -1;
    /**
     * Value to match networks which are not OEM managed.
     */
    public static int OEM_MANAGED_NO;
    /**
     * Value to match any OEM managed network.
     */
    public static final int OEM_MANAGED_YES = -2;
    public static int OEM_MANAGED_PAID;

    public static int OEM_MANAGED_PRIVATE;

    private static boolean isKnownMatchRule(final int rule) {
        switch (rule) {
            case MATCH_MOBILE:
            case MATCH_WIFI:
            case MATCH_ETHERNET:
            case MATCH_BLUETOOTH:
            case MATCH_PROXY:
            case MATCH_CARRIER:
            case MATCH_TEST:
            case MATCH_ALL:
                return true;

            default:
                return false;
        }
    }

    private static Set<String> setOf(final String item) {
        throw new RuntimeException("Stub!");
    }

    private static void throwAtLeastU() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to match {@link ConnectivityManager#TYPE_MOBILE} networks with
     * the given IMSI.
     *
     * @deprecated Use {@link Builder} to build a template.
     * @hide
     */
    @Deprecated
    public static NetworkTemplate buildTemplateMobileAll(String subscriberId) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to match metered {@link ConnectivityManager#TYPE_MOBILE} networks,
     * regardless of IMSI.
     *
     * @deprecated Use {@link Builder} to build a template.
     * @hide
     */
    @Deprecated
    public static NetworkTemplate buildTemplateMobileWildcard() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to match all metered {@link ConnectivityManager#TYPE_WIFI} networks,
     * regardless of key of the wifi network.
     *
     * @deprecated Use {@link Builder} to build a template.
     * @hide
     */
    @Deprecated
    public static NetworkTemplate buildTemplateWifiWildcard() {
        throw new RuntimeException("Stub!");
    }

    /**
     * @deprecated Use {@link Builder} to build a template.
     * @hide
     */
    @Deprecated
    public static NetworkTemplate buildTemplateWifi() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to combine all {@link ConnectivityManager#TYPE_ETHERNET} style
     * networks together.
     *
     * @deprecated Use {@link Builder} to build a template.
     * @hide
     */
    @Deprecated
    public static NetworkTemplate buildTemplateEthernet() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to combine all {@link ConnectivityManager#TYPE_BLUETOOTH} style
     * networks together.
     *
     * @hide
     */
    // TODO(b/270089918): Remove this method. This can only be done after there are no more callers,
    //  including in OEM code which can access this by linking against the framework.
    public static NetworkTemplate buildTemplateBluetooth() {
        // TODO : this is part of hidden-o txt, does that mean it should be annotated with
        // @UnsupportedAppUsage(maxTargetSdk = O) ? If yes, can't throwAtLeastU() lest apps
        // targeting O- crash on those devices.
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to combine all {@link ConnectivityManager#TYPE_PROXY} style
     * networks together.
     *
     * @hide
     */
    // TODO(b/270089918): Remove this method. This can only be done after there are no more callers,
    //  including in OEM code which can access this by linking against the framework.
    public static NetworkTemplate buildTemplateProxy() {
        // TODO : this is part of hidden-o txt, does that mean it should be annotated with
        // @UnsupportedAppUsage(maxTargetSdk = O) ? If yes, can't throwAtLeastU() lest apps
        // targeting O- crash on those devices.
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to match all metered carrier networks with the given IMSI.
     *
     * @hide
     */
    // TODO(b/273963543): Remove this method. This can only be done after there are no more callers,
    //  including in OEM code which can access this by linking against the framework.
    public static NetworkTemplate buildTemplateCarrierMetered(String subscriberId) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to match cellular networks with the given IMSI, {@code ratType} and
     * {@code metered}. Use {@link #NETWORK_TYPE_ALL} to include all network types when
     * filtering. See {@code TelephonyManager.NETWORK_TYPE_*}.
     *
     * @hide
     */
    // TODO(b/273963543): Remove this method. This can only be done after there are no more callers,
    //  including in OEM code which can access this by linking against the framework.
    public static NetworkTemplate buildTemplateMobileWithRatType(String subscriberId,
                                                                 int ratType, int metered) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to match {@link ConnectivityManager#TYPE_WIFI} networks with the
     * given key of the wifi network.
     *
     * @param wifiNetworkKey key of the wifi network. see {@link WifiInfo#getNetworkKey()}
     *                  to know details about the key.
     * @hide
     */
    // TODO(b/273963543): Remove this method. This can only be done after there are no more callers,
    //  including in OEM code which can access this by linking against the framework.
    public static NetworkTemplate buildTemplateWifi(String wifiNetworkKey) {
        // TODO : this is part of hidden-o txt, does that mean it should be annotated with
        // @UnsupportedAppUsage(maxTargetSdk = O) ? If yes, can't throwAtLeastU() lest apps
        // targeting O- crash on those devices.
        throw new RuntimeException("Stub!");
    }

    /**
     * Template to match all {@link ConnectivityManager#TYPE_WIFI} networks with the given
     * key of the wifi network and IMSI.
     *
     * Call with {@link #WIFI_NETWORK_KEY_ALL} for {@code wifiNetworkKey} to get result regardless
     * of key of the wifi network.
     *
     * @param wifiNetworkKey key of the wifi network. see {@link WifiInfo#getNetworkKey()}
     *                  to know details about the key.
     * @param subscriberId the IMSI associated to this wifi network.
     *
     * @hide
     */
    // TODO(b/273963543): Remove this method. This can only be done after there are no more callers,
    //  including in OEM code which can access this by linking against the framework.
    public static NetworkTemplate buildTemplateWifi(String wifiNetworkKey, String subscriberId) {
        throw new RuntimeException("Stub!");
    }

    private final int mMatchRule;

    /**
     * Ugh, templates are designed to target a single subscriber, but we might
     * need to match several "merged" subscribers. These are the subscribers
     * that should be considered to match this template.
     * <p>
     * Since the merge set is dynamic, it should <em>not</em> be persisted or
     * used for determining equality.
     */
   
    private final String[] mMatchSubscriberIds;

   
    private final String[] mMatchWifiNetworkKeys;

    // Matches for the NetworkStats constants METERED_*, ROAMING_* and DEFAULT_NETWORK_*.
    private final int mMetered;
    private final int mRoaming;
    private final int mDefaultNetwork;
    private final int mRatType;

    // Bitfield containing OEM network properties{@code NetworkIdentity#OEM_*}.
    private final int mOemManaged;

    private final long mTransportTypesBits;

    private static void checkValidMatchSubscriberIds(int matchRule, String[] matchSubscriberIds) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @deprecated Use {@link Builder} to build a template.
     * @hide
     */
    @Deprecated
    public NetworkTemplate(int matchRule, String subscriberId, String wifiNetworkKey) {
        // Older versions used to only match MATCH_MOBILE and MATCH_MOBILE_WILDCARD templates
        // to metered networks. It is now possible to match mobile with any meteredness, but
        // in order to preserve backward compatibility of @UnsupportedAppUsage methods, this
        // constructor passes METERED_YES for these types.
        // For backwards compatibility, still accept old wildcard match rules (6 and 7 for
        // MATCH_{MOBILE,WIFI}_WILDCARD) but convert into functionally equivalent non-wildcard
        // ones.
        throw new RuntimeException("Stub!");
    }

    private static int getBackwardsCompatibleMatchRule(int matchRule) {
        // Backwards compatibility old constants
        // Old MATCH_MOBILE_WILDCARD
        throw new RuntimeException("Stub!");
    }

    private static int getMeterednessForBackwardsCompatibility(int matchRule) {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    // TODO(b/270089918): Remove this method after no callers.
    public NetworkTemplate(int matchRule, String subscriberId, String[] matchSubscriberIds,
                           String wifiNetworkKey) {
        // Older versions used to only match MATCH_MOBILE and MATCH_MOBILE_WILDCARD templates
        // to metered networks. It is now possible to match mobile with any meteredness, but
        // in order to preserve backward compatibility of @UnsupportedAppUsage methods, this
        // constructor passes METERED_YES for these types.
        throw new RuntimeException("Stub!");
        // TODO : this is part of hidden-o txt, does that mean it should be annotated with
        // @UnsupportedAppUsage(maxTargetSdk = O) ? If yes, can't throwAtLeastU() lest apps
        // targeting O- crash on those devices.
    }

    /** @hide */
    // TODO(b/269974916): Remove this method after Android U is released.
    //  This is only used by CTS of Android T.
    public NetworkTemplate(int matchRule, String subscriberId, String[] matchSubscriberIds,
                           String[] matchWifiNetworkKeys, int metered, int roaming,
                           int defaultNetwork, int ratType, int oemManaged, int subscriberIdMatchRule) {
        // subscriberId and subscriberIdMatchRule aren't used since they are replaced by
        // matchSubscriberIds, which could be null to indicate the intention of matching any
        // subscriberIds.
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public NetworkTemplate(int matchRule, String[] matchSubscriberIds,
                           String[] matchWifiNetworkKeys, int metered, int roaming, int defaultNetwork,
                           int ratType, int oemManaged, long transportTypesBits) {
        throw new RuntimeException("Stub!");
    }

    private NetworkTemplate(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public boolean equals(Object obj) {
        throw new RuntimeException("Stub!");
    }

    // TODO(b/270089918): Remove this method. This can only be done after there are no more callers,
    //  including in OEM code which can access this by linking against the framework.
    /** @hide */
    public boolean isMatchRuleMobile() {
        // TODO : this is part of hidden-o txt, does that mean it should be annotated with
        // @UnsupportedAppUsage(maxTargetSdk = O) ? If yes, can't throwAtLeastU() lest apps
        // targeting O- crash on those devices.
        throw new RuntimeException("Stub!");
    }

    /**
     * Get match rule of the template. See {@code MATCH_*}.
     *
     * @throws IllegalStateException if the template is not built from constructors with
     * match rule, which can occur if the template was created with the
     * {@link Builder#Builder()} constructor.
     */
    public int getMatchRule() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get subscriber Id of the template.
     *
     * @deprecated User should use {@link #getSubscriberIds} instead.
     * @hide
     */
    @Deprecated
    public String getSubscriberId() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get set of subscriber Ids of the template.
     */
   
    public Set<String> getSubscriberIds() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get the set of Wifi Network Keys of the template.
     * See {@link WifiInfo#getNetworkKey()}.
     */
   
    public Set<String> getWifiNetworkKeys() {
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    // TODO: Remove this and replace all callers with {@link #getWifiNetworkKeys()}.
    public String getNetworkId() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get meteredness filter of the template.
     */
    public int getMeteredness() {
        return mMetered;
    }

    /**
     * Get roaming filter of the template.
     */
    public int getRoaming() {
        return mRoaming;
    }

    /**
     * Get the default network status filter of the template.
     */
    public int getDefaultNetworkStatus() {
        return mDefaultNetwork;
    }

    /**
     * Get the Radio Access Technology(RAT) type filter of the template.
     */
    public int getRatType() {
        return mRatType;
    }

    /** @hide */
    @Retention(RetentionPolicy.SOURCE)
    public @interface Transport { }

    /**
     * Get the transport type filter of the template.
     *
     * This is only valid when the template is constructed by using
     * {@link NetworkTemplate.Builder}.
     *
     * @return The transport type filter.
     * @throws IllegalStateException if the transport type was not set during template construction.
     */
    
    public @Transport int getTransportType() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get the transport types filter of the template.
     *
     * @hide
     */
   
    public int[] getTransportTypes() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get the OEM managed filter of the template. See {@code OEM_MANAGED_*} or
     * {@code android.net.NetworkIdentity#OEM_*}.
     */
    @OemManaged
    public int getOemManaged() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Test if given {@link NetworkIdentity} matches this template.
     *
     * @hide
     */
//    public boolean matches(NetworkIdentity ident) {
//        
//    }
    

    /**
     * Check if this template matches {@code subscriberId}. Returns true if this
     * template was created with a {@code mMatchSubscriberIds} array that contains
     * {@code subscriberId} or if {@code mMatchSubscriberIds} is empty.
     *
     * @hide
     */
    public boolean matchesSubscriberId(String subscriberId) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Check if network matches key of the wifi network.
     * Returns true when the key matches, or when {@code mMatchWifiNetworkKeys} is
     * empty.
     *
     * @param wifiNetworkKey key of the wifi network. see {@link WifiInfo#getNetworkKey()}
     *                  to know details about the key.
     */
    private boolean matchesWifiNetworkKey(String wifiNetworkKey) {
        // Note that this code accepts null wifi network keys because of a past bug where wifi
        // code was sending a null network key for some connected networks, which isn't expected
        // and ended up stored in the data on many devices.
        // A null network key in the data matches a wildcard template (one where
        // {@code mMatchWifiNetworkKeys} is empty), but not one where {@code MatchWifiNetworkKeys}
        // contains null. See b/266598304.
        throw new RuntimeException("Stub!");
    }
    
    private static String getMatchRuleName(int matchRule) {
        switch (matchRule) {
            case MATCH_MOBILE:
                return "MOBILE";
            case MATCH_WIFI:
                return "WIFI";
            case MATCH_ETHERNET:
                return "ETHERNET";
            case MATCH_BLUETOOTH:
                return "BLUETOOTH";
            case MATCH_PROXY:
                return "PROXY";
            case MATCH_CARRIER:
                return "CARRIER";
            case MATCH_TEST:
                return "TEST";
            case MATCH_ALL:
                return "ALL";
            default:
                return "UNKNOWN(" + matchRule + ")";
        }
    }

    private static String getOemManagedNames(int oemManaged) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Examine the given template and normalize it.
     * We pick the "lowest" merged subscriber as the primary
     * for key purposes, and expand the template to match all other merged
     * subscribers.
     * <p>
     * For example, given an incoming template matching B, and the currently
     * active merge set [A,B], we'd return a new template that matches both A and B.
     *
     * @hide
     */
    public static NetworkTemplate normalize(NetworkTemplate template, String[] merged) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Examine the given template and normalize it.
     * We pick the "lowest" merged subscriber as the primary
     * for key purposes, and expand the template to match all other merged
     * subscribers.
     *
     * There can be multiple merged subscriberIds for multi-SIM devices.
     *
     * <p>
     * For example, given an incoming template matching B, and the currently
     * active merge set [A,B], we'd return a new template that matches both A and B.
     *
     * @hide
     */
    // TODO(b/273963543): Remove this method. This can only be done after there are no more callers,
    //  including in OEM code which can access this by linking against the framework.
    public static NetworkTemplate normalize(NetworkTemplate template, List<String[]> mergedList) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Examine the given template and normalize it.
     * We pick the "lowest" merged subscriber as the primary
     * for key purposes, and expand the template to match all other merged
     * subscribers.
     *
     * There can be multiple merged subscriberIds for multi-SIM devices.
     *
     * <p>
     * For example, given an incoming template matching B, and the currently
     * active merge set [A,B], we'd return a new template that matches both A and B.
     *
     * @hide
     */
    private static NetworkTemplate normalizeImpl(NetworkTemplate template,
                                                 List<String[]> mergedList) {
        // Now there are several types of network which uses SubscriberId to store network
        // information. For instances:
        // The TYPE_WIFI with subscriberId means that it is a merged carrier wifi network.
        // The TYPE_CARRIER means that the network associate to specific carrier network.

        throw new RuntimeException("Stub!");
    }

   
    public static final Creator<NetworkTemplate> CREATOR = new Creator<NetworkTemplate>() {
        @Override
        public NetworkTemplate createFromParcel(Parcel in) {
            return new NetworkTemplate(in);
        }

        @Override
        public NetworkTemplate[] newArray(int size) {
            return new NetworkTemplate[size];
        }
    };

    /**
     * Builder class for NetworkTemplate.
     */
    public static final class Builder {
        private final int mMatchRule;
        // Use a SortedSet to provide a deterministic order when fetching the first one.
       
        private final SortedSet<String> mMatchSubscriberIds =
                new TreeSet<>(Comparator.nullsFirst(Comparator.naturalOrder()));
       
        private final SortedSet<String> mMatchWifiNetworkKeys = new TreeSet<>();

        // Matches for the NetworkStats constants METERED_*, ROAMING_* and DEFAULT_NETWORK_*.
        private int mMetered;
        private int mRoaming;
        private int mDefaultNetwork;
        private int mRatType;
        private long mTransportTypesBits;

        // Bitfield containing OEM network properties {@code NetworkIdentity#OEM_*}.
        private int mOemManaged;

        /**
         * Creates a new Builder with given match rule to construct NetworkTemplate objects.
         *
         * @param matchRule the match rule of the template, see {@code MATCH_*}.
         */
        public Builder(@TemplateMatchRule final int matchRule) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Creates a new Builder to construct NetworkTemplate objects.
         *
         * Note that {@link #setTransportType} MUST be called to specify
         * the transport type filter.
         */
        public Builder() {
            this(MATCH_ALL);
        }

        /**
         * Set the Subscriber Ids. Calling this function with an empty set represents
         * the intention of matching any Subscriber Ids.
         *
         * @param subscriberIds the list of Subscriber Ids.
         * @return this builder.
         */
       
        public Builder setSubscriberIds(Set<String> subscriberIds) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Set the Wifi Network Keys. Calling this function with an empty set represents
         * the intention of matching any Wifi Network Key.
         *
         * @param wifiNetworkKeys the list of Wifi Network Key,
         *                        see {@link WifiInfo#getNetworkKey()}.
         *                        Or an empty list to match all networks.
         *                        Note that {@code getNetworkKey()} might get null key
         *                        when wifi disconnects. However, the caller should never invoke
         *                        this function with a null Wifi Network Key since such statistics
         *                        never exists.
         * @return this builder.
         */
       
        public Builder setWifiNetworkKeys(Set<String> wifiNetworkKeys) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Set the meteredness filter.
         *
         * @param metered the meteredness filter.
         * @return this builder.
         */
       
        public Builder setMeteredness(int metered) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Set the roaming filter.
         *
         * @param roaming the roaming filter.
         * @return this builder.
         */
       
        public Builder setRoaming(int roaming) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Set the default network status filter.
         *
         * @param defaultNetwork the default network status filter.
         * @return this builder.
         */
       
        public Builder setDefaultNetworkStatus(int defaultNetwork) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Set the Radio Access Technology(RAT) type filter.
         *
         * @param ratType the Radio Access Technology(RAT) type filter. Use
         *                {@link #NETWORK_TYPE_ALL} to include all network types when filtering.
         *                See {@code TelephonyManager.NETWORK_TYPE_*}.
         * @return this builder.
         */
       
        public Builder setRatType(int ratType) {
            // Input will be validated with the match rule when building the template.
            throw new RuntimeException("Stub!");
        }

        /**
         * Sets the transport type filter for this template.
         *
         * This is only valid when the template is constructed by using
         * {@link NetworkTemplate.Builder}.
         *
         * @param transportType {@code NetworkCapabilities#TRANSPORT_*} constant.
         * @return This {@code Builder} for chaining.
         * @throws IllegalArgumentException if the builder was not constructed with the default
         *                                  constructor {@link Builder#Builder()}.
         */
       
        public Builder setTransportType(@Transport int transportType) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Sets the transport types filter for this template.
         *
         * - If the array is empty, matches any transport type (wildcard).
         * - If not empty, matches networks possessing ALL specified transport types.
         * For example, a set with {@code TRANSPORT_WIFI} and {@code TRANSPORT_VPN}
         * requires a network to have both.
         *
         * @param transportTypes The array of {@code NetworkCapabilities#TRANSPORT_*} constants.
         *                       An empty array acts as a wildcard for all transports.
         * @return This {@code Builder} for chaining.
         * @hide
         */
       
        private Builder setTransportTypes(int[] transportTypes) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Set the OEM managed filter.
         *
         * @param oemManaged the match rule to match different type of OEM managed network or
         *                   unmanaged networks. See {@code OEM_MANAGED_*}.
         * @return this builder.
         */
       
        public Builder setOemManaged(@OemManaged int oemManaged) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Check whether the match rule is requestable.
         *
         * @param matchRule the target match rule to be checked.
         */
        private static void assertRequestableMatchRule(final int matchRule) {
            throw new RuntimeException("Stub!");
        }

        private void assertRequestableParameters() {
            throw new RuntimeException("Stub!");
        }

        private void validateWifiNetworkKeys() {
            throw new RuntimeException("Stub!");
        }

        /**
         * Validates that:
         * 1. For MATCH_ALL, exactly one transport type is specified.
         * 2. For all other match rules, no transport type should be specified.
         */
        private void validateTransportTypes() {
            throw new RuntimeException("Stub!");
        }

        /**
         * Builds the instance of the NetworkTemplate.
         *
         * @return the built instance of NetworkTemplate.
         * @throws IllegalArgumentException if the parameters set in the builder are invalid
         *                                  (e.g., calling {@link #setTransportType} on a builder
         *                                  not created with {@link Builder#Builder()}).
         */
       
        public NetworkTemplate build() {
            throw new RuntimeException("Stub!");
        }
    }
}
