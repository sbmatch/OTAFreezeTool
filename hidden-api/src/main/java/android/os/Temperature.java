/*
 * Copyright (c) 2017 The Android Open Source Project
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

package android.os;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Temperature values used by IThermalService.
 *
 * @hide
 */
public final class Temperature implements Parcelable {
    /** Temperature value */
    private final float mValue;
    /** A Temperature type from ThermalHAL */
    private final int mType;
    /** Name of this Temperature */
    private final String mName;
    /** The level of the sensor is currently in throttling */
    private final int mStatus;

    /** Keep in sync with hardware/interfaces/thermal/aidl/android/hardware/thermal
     * /ThrottlingSeverity.aidl */

    public static final int THROTTLING_CRITICAL = 4;
    public static final int THROTTLING_EMERGENCY = 5;
    public static final int THROTTLING_LIGHT = 1;
    public static final int THROTTLING_MODERATE = 2;
    public static final int THROTTLING_NONE = 0;
    public static final int THROTTLING_SEVERE = 3;
    public static final int THROTTLING_SHUTDOWN = 6;

    /** Keep in sync with hardware/interfaces/thermal/aidl/android/hardware/thermal
     * /TemperatureType.aidl */

    public static final int TYPE_AMBIENT = 18;
    public static final int TYPE_BATTERY = 2;
    public static final int TYPE_BCL_CURRENT = 7;
    public static final int TYPE_BCL_PERCENTAGE = 8;
    public static final int TYPE_BCL_VOLTAGE = 6;
    public static final int TYPE_CAMERA = 15;
    public static final int TYPE_CPU = 0;
    public static final int TYPE_DISPLAY = 11;
    public static final int TYPE_FLASHLIGHT = 16;
    public static final int TYPE_GPU = 1;
    public static final int TYPE_MODEM = 12;
    public static final int TYPE_NPU = 9;
    public static final int TYPE_POGO = 19;
    public static final int TYPE_POWER_AMPLIFIER = 5;
    public static final int TYPE_SKIN = 3;
    public static final int TYPE_SOC = 13;
    public static final int TYPE_SPEAKER = 17;
    public static final int TYPE_TPU = 10;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_USB_PORT = 4;
    public static final int TYPE_WIFI = 14;

    /**
     * Verify a valid Temperature type.
     *
     * @return true if a Temperature type is valid otherwise false.
     */
    public static boolean isValidType(int type) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Verify a valid throttling status.
     *
     * @return true if a status is valid otherwise false.
     */
    public static boolean isValidStatus(int status) {
        throw new RuntimeException("Stub!");
    }

    public Temperature(float value, int type,
                       String name, int status) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Return the Temperature value.
     *
     * @return a Temperature value in floating point could be NaN.
     */
    public float getValue() {
        return mValue;
    }

    /**
     * Return the Temperature type.
     *
     * @return a Temperature type: TYPE_*
     */
    public int getType() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Return the Temperature name.
     *
     * @return a Temperature name as String.
     */
    public String getName() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Return the Temperature throttling status.
     *
     * @return a Temperature throttling status: THROTTLING_*
     */
    public int getStatus() {
        throw new RuntimeException("Stub!");
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
    public boolean equals(Object o) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public void writeToParcel(Parcel p, int flags) {
        throw new RuntimeException("Stub!");
    }

    public static final Parcelable.Creator<Temperature> CREATOR =
            new Parcelable.Creator<Temperature>() {
                @Override
                public Temperature createFromParcel(Parcel p) {
                    throw new RuntimeException("Stub!");
                }

                @Override
                public Temperature[] newArray(int size) {
                    throw new RuntimeException("Stub!");
                }

            };

    @Override
    public int describeContents() {
        return 0;
    }
}
