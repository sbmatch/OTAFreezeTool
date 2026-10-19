package android.app.admin;

import android.accounts.Account;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

/**
 * Params required to provision a managed profile, see
 * {@link DevicePolicyManager#createAndProvisionManagedProfile}.
 *
 * @hide
 */

public final class ManagedProfileProvisioningParams implements Parcelable {
    private static final String LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM =
            "LEAVE_ALL_SYSTEM_APPS_ENABLED";
    private static final String ORGANIZATION_OWNED_PROVISIONING_PARAM =
            "ORGANIZATION_OWNED_PROVISIONING";
    private static final String ACCOUNT_TO_MIGRATE_PROVIDED_PARAM = "ACCOUNT_TO_MIGRATE_PROVIDED";
    private static final String KEEP_MIGRATED_ACCOUNT_PARAM = "KEEP_MIGRATED_ACCOUNT";

    private final ComponentName mProfileAdminComponentName;
    private final String mOwnerName;
    private final String mProfileName;
    private final Account mAccountToMigrate;
    private final boolean mLeaveAllSystemAppsEnabled;
    private final boolean mOrganizationOwnedProvisioning;
    private final boolean mKeepAccountOnMigration;
    private final PersistableBundle mAdminExtras;

    private ManagedProfileProvisioningParams(
            ComponentName profileAdminComponentName,
            String ownerName,
            String profileName,
            Account accountToMigrate,
            boolean leaveAllSystemAppsEnabled,
            boolean organizationOwnedProvisioning,
            boolean keepAccountOnMigration,
            PersistableBundle adminExtras) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns the profile owner's {@link ComponentName}.
     */
    
    public ComponentName getProfileAdminComponentName() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns the profile owner's name.
     */
    
    public String getOwnerName() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns the profile's name if set, otherwise returns {@code null}.
     */
    
    public String getProfileName() {
        throw new RuntimeException("Stub!");
    }

    /**
     * If set, it returns the {@link Account} to migrate from the parent profile to the managed
     * profile after provisioning, otherwise returns {@code null}.
     */
    
    public Account getAccountToMigrate() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns {@code true} if system apps should be left enabled after provisioning.
     */
    public boolean isLeaveAllSystemAppsEnabled() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns {@code true} if this is an org owned device.
     */
    public boolean isOrganizationOwnedProvisioning() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns {@code true} if the migrated account from {@link #getAccountToMigrate()} should be
     * kept in parent profile.
     */
    public boolean isKeepingAccountOnMigration() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns a copy of the admin extras bundle.
     *
     * @see DevicePolicyManager#EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE
     */
    public PersistableBundle getAdminExtras() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs the provisioning params using {@link DevicePolicyEventLogger}.
     *
     * @hide
     */
    public void logParams(String callerPackage) {
        throw new RuntimeException("Stub!");
    }

    private void logParam(String callerPackage, String param, boolean value) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Builder class for {@link ManagedProfileProvisioningParams} objects.
     */
    public static final class Builder {
        private final ComponentName mProfileAdminComponentName;
        private final String mOwnerName;
        private String mProfileName;
        private Account mAccountToMigrate;
        private boolean mLeaveAllSystemAppsEnabled;
        private boolean mOrganizationOwnedProvisioning;
        private boolean mKeepingAccountOnMigration;
        private PersistableBundle mAdminExtras;

        /**
         * Initialize a new {@link Builder) to construct a {@link ManagedProfileProvisioningParams}.
         * <p>
         * See {@link DevicePolicyManager#createAndProvisionManagedProfile}
         *
         * @param profileAdminComponentName The admin {@link ComponentName} to be set as the profile
         * owner.
         * @param ownerName The name of the profile owner.
         *
         * @throws NullPointerException if {@code profileAdminComponentName} or
         * {@code ownerName} are null.
         */
        public Builder(
                ComponentName profileAdminComponentName, String ownerName) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Sets the profile name of the created profile when
         * {@link DevicePolicyManager#createAndProvisionManagedProfile} is called. Defaults to
         * {@code null} if not set.
         */
        
        public Builder setProfileName(String profileName) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Sets the {@link Account} to migrate from the parent profile to the created profile when
         * {@link DevicePolicyManager#createAndProvisionManagedProfile} is called. If not set, or
         * set to {@code null}, no accounts will be migrated.
         */
        
        public Builder setAccountToMigrate(Account accountToMigrate) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Sets whether non-required system apps should be installed on
         * the created profile when {@link DevicePolicyManager#createAndProvisionManagedProfile}
         * is called. Defaults to {@code false} if not set.
         */
        
        public Builder setLeaveAllSystemAppsEnabled(boolean leaveAllSystemAppsEnabled) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Sets if this device is owned by an organization. Defaults to {@code false}
         * if not set.
         */
        
        public Builder setOrganizationOwnedProvisioning(boolean organizationOwnedProvisioning) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Sets whether to keep the account on the parent profile during account migration.
         * Defaults to {@code false}.
         */
        
        public Builder setKeepingAccountOnMigration(boolean keepingAccountOnMigration) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Sets a {@link Bundle} that contains admin-specific extras.
         */
        
        //TODO(b/235783053) The adminExtras parameter is actually .
        public Builder setAdminExtras(PersistableBundle adminExtras) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Combines all of the attributes that have been set on this {@code Builder}.
         *
         * @return a new {@link ManagedProfileProvisioningParams} object.
         */
        
        public ManagedProfileProvisioningParams build() {
            throw new RuntimeException("Stub!");
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     */
    @Override
    public String toString() {
        return "ManagedProfileProvisioningParams{"
                + "mProfileAdminComponentName=" + mProfileAdminComponentName
                + ", mOwnerName=" + mOwnerName
                + ", mProfileName=" + (mProfileName == null ? "null" : mProfileName)
                + ", mAccountToMigrate=" + (mAccountToMigrate == null ? "null" : mAccountToMigrate)
                + ", mLeaveAllSystemAppsEnabled=" + mLeaveAllSystemAppsEnabled
                + ", mOrganizationOwnedProvisioning=" + mOrganizationOwnedProvisioning
                + ", mKeepAccountOnMigration=" + mKeepAccountOnMigration
                + ", mAdminExtras=" + mAdminExtras
                + '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }

    public static final Creator<ManagedProfileProvisioningParams> CREATOR =
            new Creator<ManagedProfileProvisioningParams>() {
                @Override
                public ManagedProfileProvisioningParams createFromParcel(Parcel in) {
                    throw new RuntimeException("Stub!");
                }

                @Override
                public ManagedProfileProvisioningParams[] newArray(int size) {
                    throw new RuntimeException("Stub!");
                }
            };
}
