package com.android.server.pm;

import java.util.Set;

public class UserRestrictionsUtils {
    public static boolean canProfileOwnerChange(String restriction, boolean isMainUser, boolean isProfileOwnerOnOrgOwnedDevice){
        throw new RuntimeException("Stub!");
    }
    public static boolean canProfileOwnerChange(String restriction, int userId){
        throw new RuntimeException("Stub!");
    }

    public static boolean canProfileOwnerChange(String restriction, boolean isMainUser){
        throw new RuntimeException("Stub!");
    }
    public static boolean canDeviceOwnerChange(String restriction){
        throw new RuntimeException("Stub!");
    }



    /**
     * Returns whether the given restriction name is valid (and logs it if it isn't).
     */
    public static boolean isValidRestriction(String restriction){
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns the user restrictions that default to {@code true} for managed profile owners.
     */
    public static Set<String> getDefaultEnabledForManagedProfiles() {
        throw new RuntimeException("Stub!");
    }

}
