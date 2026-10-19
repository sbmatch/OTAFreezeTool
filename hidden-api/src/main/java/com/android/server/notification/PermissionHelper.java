package com.android.server.notification;

import android.content.Context;
import android.content.pm.IPackageManager;
import android.permission.IPermissionManager;

public final class PermissionHelper {
    private static final String TAG = "PermissionHelper";
    private final Context mContext;
    private final IPackageManager mPackageManager;
    private final IPermissionManager mPermManager;
    public PermissionHelper(Context context, IPackageManager packageManager,
                            IPermissionManager permManager) {
        mContext = context;
        mPackageManager = packageManager;
        mPermManager = permManager;
    }

    /**
     * Grants or revokes the notification permission for a given package/user. UserSet should
     * only be true if this method is being called to migrate existing user choice, because it
     * can prevent the user from seeing the in app permission dialog. Must not be called
     * with a lock held.
     */
    public void setNotificationPermission(String packageName, int userId, boolean grant,
                                          boolean userSet){
        throw new RuntimeException("Stub!");
    }

    boolean isPermissionGrantedByDefaultOrRole(String packageName, int userId){
        throw new RuntimeException("Stub!");
    }

    public boolean isPermissionFixed(String packageName, int userId){
        throw new RuntimeException("Stub!");
    }
}
