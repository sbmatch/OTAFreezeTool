package android.os;

import androidx.annotation.NonNull;

public final class BatteryUsageStatsQuery implements Parcelable {
    private BatteryUsageStatsQuery(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    private BatteryUsageStatsQuery(Builder builder){
        throw new RuntimeException("Stub!");
    }

    public static final Creator<BatteryUsageStatsQuery> CREATOR = new Creator<BatteryUsageStatsQuery>() {
        @Override
        public BatteryUsageStatsQuery createFromParcel(Parcel in) {
            return new BatteryUsageStatsQuery(in);
        }

        @Override
        public BatteryUsageStatsQuery[] newArray(int size) {
            return new BatteryUsageStatsQuery[size];
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

    public static final class Builder {
        public BatteryUsageStatsQuery build() {
            throw new RuntimeException("Stub!");
        }
        public Builder includeBatteryHistory(){
            throw new RuntimeException("Stub!");
        }
        public Builder includeProcessStateData(){
            throw new RuntimeException("Stub!");
        }
    }
}
