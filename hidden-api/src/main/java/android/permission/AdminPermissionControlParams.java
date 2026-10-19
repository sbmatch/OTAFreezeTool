package android.permission;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public final class AdminPermissionControlParams implements Parcelable {
    public AdminPermissionControlParams(String granteePackageName, String permission, int grantState, boolean canAdminGrantSensorsPermissions){
        throw new RuntimeException("Stub!");
    }

    public static final Creator<AdminPermissionControlParams> CREATOR = new Creator<AdminPermissionControlParams>() {
        @Override
        public AdminPermissionControlParams createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public AdminPermissionControlParams[] newArray(int size) {
            throw new RuntimeException("Stub!");
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

    public String getGranteePackageName() {
        throw new RuntimeException("Stub!");
    }

    public String getPermission() {
        throw new RuntimeException("Stub!");
    }

    public int getGrantState() {
        throw new RuntimeException("Stub!");
    }

    public boolean canAdminGrantSensorsPermissions() {
        throw new RuntimeException("Stub!");
    }

    public String toString(){
        throw new RuntimeException("Stub!");
    }

}
