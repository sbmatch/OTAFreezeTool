package android.app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProfilerInfo implements Parcelable {

    /**
     * Get the value for the clock type corresponding to the option string passed to the activity
     * manager. am profile start / am start-activity start-profiler commands accept clock-type
     * option to choose the source of timestamps when profiling. This function maps the option
     * string to the value of flags that is used when calling VMDebug.startMethodTracing
     */
    public static int getClockTypeFromString(String type) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Return a new ProfilerInfo instance, with fields populated from this object,
     *
     */
    public ProfilerInfo setAgent(String agent, boolean attachAgentDuringBind) {
        throw new RuntimeException("Stub!");
    }

    protected ProfilerInfo(Parcel in) {
    }

    public static final Creator<ProfilerInfo> CREATOR = new Creator<ProfilerInfo>() {
        @Override
        public ProfilerInfo createFromParcel(Parcel in) {
            return new ProfilerInfo(in);
        }

        @Override
        public ProfilerInfo[] newArray(int size) {
            return new ProfilerInfo[size];
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
}
