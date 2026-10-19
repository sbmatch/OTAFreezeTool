/*
 * Copyright (C) 2021 The Android Open Source Project
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

package android.telephony.data;

import android.net.QosSessionAttributes;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Provides Qos attributes of an NR bearer.
 *
 * @hide
 */
public final class NrQosSessionAttributes implements Parcelable, QosSessionAttributes {
    private static final String TAG = NrQosSessionAttributes.class.getSimpleName();
    private final int m5Qi;
    private final int mQfi;
    private final long mMaxUplinkBitRate;
    private final long mMaxDownlinkBitRate;
    private final long mGuaranteedUplinkBitRate;
    private final long mGuaranteedDownlinkBitRate;
    private final long mAveragingWindow;
     private final List<InetSocketAddress> mRemoteAddresses;

    /**
     * 5G QOS Identifier (5QI), see 3GPP TS 24.501 and 23.501.
     * The allowed values are standard values(1-9, 65-68, 69-70, 75, 79-80, 82-85)
     * defined in the spec and operator specific values in the range 128-254.
     *
     * @return the 5QI of the QOS flow
     */
    public int getQosIdentifier() {
        return m5Qi;
    }

    /**
     * QOS flow identifier of the QOS flow description in the
     * range of 1 to 63. see 3GPP TS 24.501 and 23.501.
     *
     * @return the QOS flow identifier of the session
     */
    public int getQosFlowIdentifier() {
        return mQfi;
    }

    /**
     * Minimum bit rate in kbps that is guaranteed to be provided by the network on the uplink.
     *
     * see 3GPP TS 24.501 section 6.2.5
     *
     * Note: The Qos Session may be shared with OTHER applications besides yours.
     *
     * @return the guaranteed bit rate in kbps
     */
    public long getGuaranteedUplinkBitRateKbps() {
        return mGuaranteedUplinkBitRate;
    }

    /**
     * Minimum bit rate in kbps that is guaranteed to be provided by the network on the downlink.
     *
     * see 3GPP TS 24.501 section 6.2.5
     *
     * Note: The Qos Session may be shared with OTHER applications besides yours.
     *
     * @return the guaranteed bit rate in kbps
     */
    public long getGuaranteedDownlinkBitRateKbps() {
        return mGuaranteedDownlinkBitRate;
    }

    /**
     * The maximum uplink kbps that the network will accept.
     *
     * see 3GPP TS 24.501 section 6.2.5
     *
     * Note: The Qos Session may be shared with OTHER applications besides yours.
     *
     * @return the max uplink bit rate in kbps
     */
    public long getMaxUplinkBitRateKbps() {
        return mMaxUplinkBitRate;
    }

    /**
     * The maximum downlink kbps that the network can provide.
     *
     * see 3GPP TS 24.501 section 6.2.5
     *
     * Note: The Qos Session may be shared with OTHER applications besides yours.
     *
     * @return the max downlink bit rate in kbps
     */
    public long getMaxDownlinkBitRateKbps() {
        return mMaxDownlinkBitRate;
    }

    /**
     * The duration in milliseconds over which the maximum bit rates and guaranteed bit rates
     * are calculated
     *
     * see 3GPP TS 24.501 section 6.2.5
     *
     * @return the averaging window duration in milliseconds
     */
    
    public Duration getBitRateWindowDuration() {
        throw new RuntimeException("Stub!");
    }

    /**
     * List of remote addresses associated with the Qos Session.  The given uplink bit rates apply
     * to this given list of remote addresses.
     *
     * Note: In the event that the list is empty, it is assumed that the uplink bit rates apply to
     * all remote addresses that are not contained in a different set of attributes.
     *
     * @return list of remote socket addresses that the attributes apply to
     */
    
    public List<InetSocketAddress> getRemoteAddresses() {
        return mRemoteAddresses;
    }

    /**
     * ..ctor for attributes
     *
     * @param fiveQi 5G quality class indicator
     * @param qfi QOS flow identifier
     * @param maxDownlinkBitRate the max downlink bit rate in kbps
     * @param maxUplinkBitRate the max uplink bit rate in kbps
     * @param guaranteedDownlinkBitRate the guaranteed downlink bit rate in kbps
     * @param guaranteedUplinkBitRate the guaranteed uplink bit rate in kbps
     * @param averagingWindow the averaging window duration in milliseconds
     * @param remoteAddresses the remote addresses that the uplink bit rates apply to
     *
     * @hide
     */
    public NrQosSessionAttributes(final int fiveQi, final int qfi,
                                  final long maxDownlinkBitRate, final long maxUplinkBitRate,
                                  final long guaranteedDownlinkBitRate, final long guaranteedUplinkBitRate,
                                  final long averagingWindow,  final List<InetSocketAddress> remoteAddresses) {
        throw new RuntimeException("Stub!");
    }

    private static List<InetSocketAddress> copySocketAddresses(final List<InetSocketAddress> remoteAddresses) {
        throw new RuntimeException("Stub!");
    }

    private NrQosSessionAttributes( final Parcel in) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public boolean equals(Object o) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }


    
    public static final Creator<NrQosSessionAttributes> CREATOR =
            new Creator<NrQosSessionAttributes>() {
                
                @Override
                public NrQosSessionAttributes createFromParcel( final Parcel in) {
                    return new NrQosSessionAttributes(in);
                }

                
                @Override
                public NrQosSessionAttributes[] newArray(final int size) {
                    return new NrQosSessionAttributes[size];
                }
            };
}