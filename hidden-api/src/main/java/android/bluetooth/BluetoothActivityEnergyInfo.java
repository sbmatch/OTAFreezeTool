package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public final class BluetoothActivityEnergyInfo implements Parcelable {
    public static final int BT_STACK_STATE_INVALID = 0;
    public static final int BT_STACK_STATE_STATE_ACTIVE = 1;
    public static final int BT_STACK_STATE_STATE_IDLE = 3;
    public static final int BT_STACK_STATE_STATE_SCANNING = 2;
    public static final Parcelable.Creator<BluetoothActivityEnergyInfo> CREATOR = new Parcelable.Creator<BluetoothActivityEnergyInfo>() { // from class: android.bluetooth.BluetoothActivityEnergyInfo.1

        @Override
        public BluetoothActivityEnergyInfo createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public BluetoothActivityEnergyInfo[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };
    private int mBluetoothStackState;
    private long mControllerEnergyUsed;
    private long mControllerIdleTimeMs;
    private long mControllerRxTimeMs;
    private long mControllerTxTimeMs;
    private final long mTimestamp;
    //private List<UidTraffic> mUidTraffic;

    public BluetoothActivityEnergyInfo(long timestamp, int stackState, long txTime, long rxTime, long idleTime, long energyUsed) {
        throw new RuntimeException("Stub!");
    }

    private BluetoothActivityEnergyInfo(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel out, int flags) {
        throw new RuntimeException("Stub!");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getBluetoothStackState() {
        throw new RuntimeException("Stub!");
    }

    public long getControllerTxTimeMillis() {
        throw new RuntimeException("Stub!");
    }

    public long getControllerRxTimeMillis() {
        throw new RuntimeException("Stub!");
    }

    public long getControllerIdleTimeMillis() {
        throw new RuntimeException("Stub!");
    }

    public long getControllerEnergyUsed() {
        throw new RuntimeException("Stub!");
    }

    public long getTimestampMillis() {
        throw new RuntimeException("Stub!");
    }

    public boolean isValid() {
        throw new RuntimeException("Stub!");
    }
}