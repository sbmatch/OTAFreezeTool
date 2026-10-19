package android.os;

import androidx.annotation.NonNull;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public final class BatteryUsageStats implements Parcelable, Closeable {
    private BatteryUsageStats(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    public List<UidBatteryConsumer> getUidBatteryConsumers(){
        throw new RuntimeException("Stub!");
    }

    public static final Creator<BatteryUsageStats> CREATOR = new Creator<BatteryUsageStats>() {
        @Override
        public BatteryUsageStats createFromParcel(Parcel in) {
            return new BatteryUsageStats(in);
        }

        @Override
        public BatteryUsageStats[] newArray(int size) {
            return new BatteryUsageStats[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
