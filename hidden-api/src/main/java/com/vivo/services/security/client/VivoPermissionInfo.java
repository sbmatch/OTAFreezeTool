package com.vivo.services.security.client;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class VivoPermissionInfo implements Parcelable {
    private int[] mPermissionResults;
    protected VivoPermissionInfo(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    public VivoPermissionInfo(String packageName) {
        throw new RuntimeException("Stub!");
    }

    public boolean isBlackListApp() {
        throw new RuntimeException("Stub!");
    }

    public void setBlackListApp(boolean isBlackListApp) {
        throw new RuntimeException("Stub!");
    }

    public void grantAllPermissions() {
        throw new RuntimeException("Stub!");
    }

    public boolean isWhiteListApp() {
        throw new RuntimeException("Stub!");
    }

    public void setWhiteListApp(boolean isWhiteListApp) {
        throw new RuntimeException("Stub!");
    }

    public int getPermissionResult(int vpTypeId) {
        throw new RuntimeException("Stub!");
    }

    public void setPermissionResult(int vpTypeId, int permResult) {
        throw new RuntimeException("Stub!");
    }

    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }

    public static boolean isValidPermissionResult(int premResult) {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }

    public static final Creator<VivoPermissionInfo> CREATOR = new Creator<VivoPermissionInfo>() {
        @Override
        public VivoPermissionInfo createFromParcel(Parcel in) {
            return new VivoPermissionInfo(in);
        }

        @Override
        public VivoPermissionInfo[] newArray(int size) {
            return new VivoPermissionInfo[size];
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
