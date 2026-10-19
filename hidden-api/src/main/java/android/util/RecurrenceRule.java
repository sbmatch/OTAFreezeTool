/*
 * Copyright (C) 2017 The Android Open Source Project
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

package android.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Objects;

/**
 * Description of an event that should recur over time at a specific interval
 * between two anchor points in time.
 *
 * @hide
 */

public class RecurrenceRule implements Parcelable {
    private static final String TAG = "RecurrenceRule";
    private static final boolean LOGD = Log.isLoggable(TAG, Log.DEBUG);

    private static final int VERSION_INIT = 0;
    public static Clock sClock;

    public final ZonedDateTime start;
    public final ZonedDateTime end;
    public final Period period;

    public RecurrenceRule(ZonedDateTime start, ZonedDateTime end, Period period) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static RecurrenceRule buildNever() {
        return new RecurrenceRule(null, null, null);
    }

    @Deprecated
    public static RecurrenceRule buildRecurringMonthly(int dayOfMonth, ZoneId zone) {
        // Assume we started last January, since it has all possible days
        throw new RuntimeException("Stub!");
    }

    private RecurrenceRule(Parcel source) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }

    public RecurrenceRule(DataInputStream in) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeToStream(DataOutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    @Override
    public String toString() {
        return new StringBuilder("RecurrenceRule{")
                .append("start=").append(start)
                .append(" end=").append(end)
                .append(" period=").append(period)
                .append("}").toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, period);
    }

    @Override
    public boolean equals(Object obj) {
        throw new RuntimeException("Stub!");
    }

    public static final Parcelable.Creator<RecurrenceRule> CREATOR = new Parcelable.Creator<RecurrenceRule>() {
        @Override
        public RecurrenceRule createFromParcel(Parcel source) {
            return new RecurrenceRule(source);
        }

        @Override
        public RecurrenceRule[] newArray(int size) {
            return new RecurrenceRule[size];
        }
    };

    public boolean isRecurring() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public boolean isMonthly() {
        throw new RuntimeException("Stub!");
    }

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        throw new RuntimeException("Stub!");
    }

    private class NonrecurringIterator implements Iterator<Range<ZonedDateTime>> {
        boolean hasNext;

        public NonrecurringIterator() {
            throw new RuntimeException("Stub!");
        }

        @Override
        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        @Override
        public Range<ZonedDateTime> next() {
            throw new RuntimeException("Stub!");
        }
    }

    private class RecurringIterator implements Iterator<Range<ZonedDateTime>> {
        int i;
        ZonedDateTime cycleStart;
        ZonedDateTime cycleEnd;

        public RecurringIterator() {
            throw new RuntimeException("Stub!");
        }

        private void updateCycle() {
            throw new RuntimeException("Stub!");
        }

        private ZonedDateTime roundBoundaryTime(ZonedDateTime boundary) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        @Override
        public Range<ZonedDateTime> next() {
            throw new RuntimeException("Stub!");
        }
    }

    public static String convertZonedDateTime(ZonedDateTime time) {
        throw new RuntimeException("Stub!");
    }

    public static ZonedDateTime convertZonedDateTime(String time) {
        throw new RuntimeException("Stub!");
    }

    public static String convertPeriod(Period period) {
        throw new RuntimeException("Stub!");
    }

    public static Period convertPeriod(String period) {
        throw new RuntimeException("Stub!");
    }
}
