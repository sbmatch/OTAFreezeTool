package miui.process;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class ProcessCloudData implements Parcelable {
    public ProcessCloudData() {

    }
    protected ProcessCloudData(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    public static final Creator<ProcessCloudData> CREATOR = new Creator<ProcessCloudData>() {
        @Override
        public ProcessCloudData createFromParcel(Parcel in) {
            return new ProcessCloudData(in);
        }

        @Override
        public ProcessCloudData[] newArray(int size) {
            return new ProcessCloudData[size];
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

    public List<String> getCloudWhiteList() {
        throw new RuntimeException("Stub!");
    }

    public void setCloudWhiteList(List<String> mCloudWhiteList) {
        throw new RuntimeException("Stub!");
    }

    public Map<String, Integer> getAppProtectMap() {
        throw new RuntimeException("Stub!");
    }

    public void setAppProtectMap(Map<String, Integer> mAppProtectMap) {
        throw new RuntimeException("Stub!");
    }

    public Map<String, Integer> getFgProtectMap() {
        throw new RuntimeException("Stub!");
    }

    public void setFgProtectMap(Map<String, Integer> mFgProtectMap) {
        throw new RuntimeException("Stub!");
    }

    public List<String> getFastBootList() {
        throw new RuntimeException("Stub!");
    }

    public void setFastBootList(List<String> mFastBootList) {
        throw new RuntimeException("Stub!");
    }

    public List<String> getSecretlyProtectAppList() {
        throw new RuntimeException("Stub!");
    }

    public void setSecretlyProtectAppList(List<String> secretlyProtectAppList) {
        throw new RuntimeException("Stub!");
    }



    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
