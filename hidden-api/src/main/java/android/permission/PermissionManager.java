package android.permission;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public final class PermissionManager {
    /**
     * The permission is granted.
     */
    public static final int PERMISSION_GRANTED = 0;
    /**
     * The permission is denied. Applicable only to runtime permissions.
     * <p>
     * The app isn't expecting the permission to be denied so that a "no-op" action should be taken,
     * such as returning an empty result.
     */
    public static final int PERMISSION_SOFT_DENIED = 1;
    /**
     * The permission is denied.
     * <p>
     * The app should receive a {@code SecurityException}, or an error through a relevant callback.
     */
    public static final int PERMISSION_HARD_DENIED = 2;

    public PermissionManager(Context context) {
        throw new RuntimeException("Stub!");
    }

    private String getPersistentDeviceId(int deviceId) {
        throw new RuntimeException("Stub!");
    }

    public static final class PermissionState implements Parcelable {
        private final boolean mGranted;
        private final int mFlags;

        public PermissionState(boolean granted, int flags) {
            throw new RuntimeException("Stub!");
        }

        private PermissionState(Parcel in) {
            throw new RuntimeException("Stub!");
        }


        /**
         * Returns whether this permission is granted
         */
        public boolean isGranted() {
            throw new RuntimeException("Stub!");
        }

        /**
         * Returns the flags associated with this permission state
         *
         * @see PackageManager#getPermissionFlags
         */

        public int getFlags() {
            throw new RuntimeException("Stub!");
        }

        public static final Creator<PermissionState> CREATOR = new Creator<PermissionState>() {
            @Override
            public PermissionState createFromParcel(Parcel in) {
                return new PermissionState(in);
            }

            @Override
            public PermissionState[] newArray(int size) {
                return new PermissionState[size];
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

        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
}
