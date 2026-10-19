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

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Range;
import android.util.RecurrenceRule;

import java.io.DataInputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;

/**
 * Policy for networks matching a {@link NetworkTemplate}, including usage cycle
 * and limits to be enforced.
 *
 * @hide
 */
public class NetworkPolicy implements Parcelable, Comparable<NetworkPolicy> {
    private static final String TAG = NetworkPolicy.class.getSimpleName();
    private static final int VERSION_INIT = 1;
    private static final int VERSION_RULE = 2;
    private static final int VERSION_RAPID = 3;

    /**
     * Initial Version of the NetworkTemplate backup serializer.
     */
    private static final int TEMPLATE_BACKUP_VERSION_1_INIT = 1;
    private static final int TEMPLATE_BACKUP_VERSION_2_UNSUPPORTED = 2;
    /**
     * Version of the NetworkTemplate backup serializer that added carrier template support.
     */
    private static final int TEMPLATE_BACKUP_VERSION_3_SUPPORT_CARRIER_TEMPLATE = 3;
    /**
     * Latest Version of the NetworkTemplate Backup Serializer.
     */
    private static final int TEMPLATE_BACKUP_VERSION_LATEST =
            TEMPLATE_BACKUP_VERSION_3_SUPPORT_CARRIER_TEMPLATE;

    public static final int CYCLE_NONE = -1;
    public static final long WARNING_DISABLED = -1;
    public static final long LIMIT_DISABLED = -1;
    public static final long SNOOZE_NEVER = -1;
    public NetworkTemplate template;
    public RecurrenceRule cycleRule;

    public long warningBytes = WARNING_DISABLED;

    public long limitBytes = LIMIT_DISABLED;
    public long lastWarningSnooze = SNOOZE_NEVER;
    public long lastLimitSnooze = SNOOZE_NEVER;
    public long lastRapidSnooze = SNOOZE_NEVER;

    @Deprecated public boolean metered = true;

    public boolean inferred = false;

    private static final long DEFAULT_MTU = 1500;

    public static RecurrenceRule buildRule(int cycleDay, ZoneId cycleTimezone) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate template, int cycleDay, String cycleTimezone,
                         long warningBytes, long limitBytes, boolean metered) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate template, int cycleDay, String cycleTimezone,
                         long warningBytes, long limitBytes, long lastWarningSnooze, long lastLimitSnooze,
                         boolean metered, boolean inferred) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate template, RecurrenceRule cycleRule, long warningBytes,
                         long limitBytes, long lastWarningSnooze, long lastLimitSnooze, boolean metered,
                         boolean inferred) {
        throw new RuntimeException("Stub!");
    }

    public NetworkPolicy(NetworkTemplate template, RecurrenceRule cycleRule, long warningBytes,
                         long limitBytes, long lastWarningSnooze, long lastLimitSnooze, long lastRapidSnooze,
                         boolean metered, boolean inferred) {
        throw new RuntimeException("Stub!");
    }

    private NetworkPolicy(Parcel source) {
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

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Test if given measurement is over {@link #warningBytes}.
     */
    public boolean isOverWarning(long totalBytes) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Test if given measurement is near enough to {@link #limitBytes} to be
     * considered over-limit.
     */

    public boolean isOverLimit(long totalBytes) {
        // over-estimate, since kernel will trigger limit once first packet
        // trips over limit.
        throw new RuntimeException("Stub!");
    }

    /**
     * Clear any existing snooze values, setting to {@link #SNOOZE_NEVER}.
     */
    public void clearSnooze() {
        lastWarningSnooze = SNOOZE_NEVER;
        lastLimitSnooze = SNOOZE_NEVER;
        lastRapidSnooze = SNOOZE_NEVER;
    }

    /**
     * Test if this policy has a cycle defined, after which usage should reset.
     */
    public boolean hasCycle() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int compareTo(NetworkPolicy another) {
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

    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }


    public static final  Creator<NetworkPolicy> CREATOR = new Creator<NetworkPolicy>() {
        @Override
        public NetworkPolicy createFromParcel(Parcel in) {
            return new NetworkPolicy(in);
        }

        @Override
        public NetworkPolicy[] newArray(int size) {
            return new NetworkPolicy[size];
        }
    };

    public byte[] getBytesForBackup() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public static NetworkPolicy getNetworkPolicyFromBackup(DataInputStream in) {
        throw new RuntimeException("Stub!");
    }

}
