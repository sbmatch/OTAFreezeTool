package android.net;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashSet;

public class InterfaceConfiguration implements Parcelable {
    private String mHwAddr;
    private LinkAddress mAddr;
    private HashSet<String> mFlags = new HashSet<>();

    // Must be kept in sync with constant in INetd.aidl
    private static final String FLAG_UP = "up";
    private static final String FLAG_DOWN = "down";

    private static final  String[] EMPTY_STRING_ARRAY = new String[0];

    public InterfaceConfiguration() {
    }

    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }

    public Iterable<String> getFlags() {
        throw new RuntimeException("Stub!");
    }

    public boolean hasFlag(String flag) {
        throw new RuntimeException("Stub!");
    }

    public void clearFlag(String flag) {
        throw new RuntimeException("Stub!");
    }

    public void setFlag(String flag) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Set flags to mark interface as up.
     */

    public void setInterfaceUp() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Set flags to mark interface as down.
     */

    public void setInterfaceDown() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Set flags so that no changes will be made to the up/down status.
     */
    public void ignoreInterfaceUpDownStatus() {
        throw new RuntimeException("Stub!");
    }

    public LinkAddress getLinkAddress() {
        throw new RuntimeException("Stub!");
    }

    public void setLinkAddress(LinkAddress addr) {
        throw new RuntimeException("Stub!");
    }

    public String getHardwareAddress() {
        throw new RuntimeException("Stub!");
    }

    public void setHardwareAddress(String hwAddr) {
        throw new RuntimeException("Stub!");
    }

    /**
     * This function determines if the interface is up and has a valid IP
     * configuration (IP address has a non zero octet).
     *
     * Note: It is supposed to be quick and hence should not initiate
     * any network activity
     */
    public boolean isActive() {
        throw new RuntimeException("Stub!");
    }

    public boolean isUp() {
        throw new RuntimeException("Stub!");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }

    public static final Parcelable.Creator<InterfaceConfiguration> CREATOR = new Creator<
            InterfaceConfiguration>() {
        public InterfaceConfiguration createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        public InterfaceConfiguration[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };

    private static void validateFlag(String flag) {
        throw new RuntimeException("Stub!");
    }
}

