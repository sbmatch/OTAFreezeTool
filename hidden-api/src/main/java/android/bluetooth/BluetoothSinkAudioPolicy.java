package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public final class BluetoothSinkAudioPolicy implements Parcelable {
    public static final Parcelable.Creator<BluetoothSinkAudioPolicy> CREATOR = new Parcelable.Creator<BluetoothSinkAudioPolicy>() { // from class: android.bluetooth.BluetoothSinkAudioPolicy.1
        @Override
        public BluetoothSinkAudioPolicy createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public BluetoothSinkAudioPolicy[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };
    public static final String HFP_SET_SINK_AUDIO_POLICY_ID = "SINKAUDIOPOLICY";
    public static final int POLICY_ALLOWED = 1;
    public static final int POLICY_NOT_ALLOWED = 2;
    public static final int POLICY_UNCONFIGURED = 0;
    private final int mCallEstablishPolicy;
    private final int mConnectingTimePolicy;
    private final int mInBandRingtonePolicy;

    public BluetoothSinkAudioPolicy(int callEstablishPolicy, int connectingTimePolicy, int inBandRingtonePolicy) {
        throw new RuntimeException("Stub!");
    }
    public int getCallEstablishPolicy() {
        throw new RuntimeException("Stub!");
    }
    public int getActiveDevicePolicyAfterConnection() {
        throw new RuntimeException("Stub!");
    }
    public int getInBandRingtonePolicy() {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        throw new RuntimeException("Stub!");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public static final class Builder {
        private int mCallEstablishPolicy;
        private int mConnectingTimePolicy;
        private int mInBandRingtonePolicy;

        public Builder() {
            throw new RuntimeException("Stub!");
        }

        public Builder(BluetoothSinkAudioPolicy policies) {
            throw new RuntimeException("Stub!");
        }

      
        public Builder setCallEstablishPolicy(int callEstablishPolicy) {
            throw new RuntimeException("Stub!");
        }

      
        public Builder setActiveDevicePolicyAfterConnection(int connectingTimePolicy) {
            throw new RuntimeException("Stub!");
        }

      
        public Builder setInBandRingtonePolicy(int inBandRingtonePolicy) {
            throw new RuntimeException("Stub!");
        }

        public BluetoothSinkAudioPolicy build() {
            throw new RuntimeException("Stub!");
        }
    }
}
