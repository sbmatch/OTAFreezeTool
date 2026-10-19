package android.app.admin;

import android.accounts.Account;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageDataObserver;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.data.ApnSetting;

import java.util.List;

public interface IDevicePolicyManager extends IInterface {
    
    void setCameraDisabled(ComponentName who, String callerPackageName, boolean disabled, boolean parent);
    boolean getCameraDisabled(ComponentName who, String callerPackageName, int userHandle, boolean parent);

    void setScreenCaptureDisabled(ComponentName who, String callerPackageName, boolean disabled, boolean parent);
    boolean getScreenCaptureDisabled(ComponentName who, int userHandle, boolean parent);

    void setNearbyNotificationStreamingPolicy(int policy);
    int getNearbyNotificationStreamingPolicy(int userId);

    void setNearbyAppStreamingPolicy(int policy);
    int getNearbyAppStreamingPolicy(int userId);

    void setKeyguardDisabledFeatures(ComponentName who, String callerPackageName, int which, boolean parent);
    int getKeyguardDisabledFeatures(ComponentName who, int userHandle, boolean parent);

    void setActiveAdmin(ComponentName policyReceiver, boolean refreshing,
                        int userHandle, String provisioningContext);
    boolean isAdminActive(ComponentName policyReceiver, int userHandle);
    List<ComponentName> getActiveAdmins(int userHandle);
    
    boolean packageHasActiveAdmins(String packageName, int userHandle);
    //void getRemoveWarning(ComponentName policyReceiver, RemoteCallback result, int userHandle);
    void removeActiveAdmin(ComponentName policyReceiver, int userHandle);
    void forceRemoveActiveAdmin(ComponentName policyReceiver, int userHandle);
    boolean hasGrantedPolicy(ComponentName policyReceiver, int usesPolicy, int userHandle);

    boolean setDeviceOwner(ComponentName who, int userId, boolean setProfileOwnerOnCurrentUserIfNecessary);
    boolean setDeviceOwner(ComponentName admin, String ownerName, int userId,
                           boolean setProfileOwnerOnCurrentUserIfNecessary);
    ComponentName getDeviceOwnerComponent(boolean callingUserOnly);
    ComponentName getDeviceOwnerComponentOnUser(int userId);
    boolean hasDeviceOwner();
    String getDeviceOwnerName();
    void clearDeviceOwner(String packageName);
    int getDeviceOwnerUserId();
    UserHandle createAndProvisionManagedProfile(ManagedProfileProvisioningParams provisioningParams, String callerPackage);
    boolean setProfileOwner(ComponentName who, int userHandle);
    ComponentName getProfileOwnerAsUser(int userHandle);
    ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle);
    boolean isSupervisionComponent(ComponentName who);
    String getProfileOwnerName(int userHandle);
    void setProfileEnabled(ComponentName who);
    void setProfileName(ComponentName who, String profileName);
    void clearProfileOwner(ComponentName who);
    void wipeDataWithReason(int flags, String wipeReasonForUser, boolean parent);
    boolean hasUserSetupCompleted();
    boolean isOrganizationOwnedDeviceWithManagedProfile();

    boolean checkDeviceIdentifierAccess(String packageName, int pid, int uid);

    void setDeviceOwnerLockScreenInfo(ComponentName who, CharSequence deviceOwnerInfo);
    CharSequence getDeviceOwnerLockScreenInfo();

    String[] setPackagesSuspended(ComponentName admin,String callerPackage, String[] packageNames, boolean suspended);
    boolean isPackageSuspended(ComponentName admin, String callerPackage, String packageName);
    List<String> listPolicyExemptApps();
    void enforceCanManageCaCerts(ComponentName admin, String callerPackage);
    boolean approveCaCert(String alias, int userHandle, boolean approval);
    boolean isCaCertApproved(String alias, int userHandle);

    void setDelegatedScopes(ComponentName who, String delegatePackage, List<String> scopes);
    List<String> getDelegatedScopes(ComponentName who, String delegatePackage);
    List<String> getDelegatePackages(ComponentName who, String scope);
    boolean setAlwaysOnVpnPackage(ComponentName who, String vpnPackage, boolean lockdown, List<String> lockdownAllowlist);
    String getAlwaysOnVpnPackage(ComponentName who);
    String getAlwaysOnVpnPackageForUser(int userHandle);
    boolean isAlwaysOnVpnLockdownEnabled(ComponentName who);
    boolean isAlwaysOnVpnLockdownEnabledForUser(int userHandle);
    List<String> getAlwaysOnVpnLockdownAllowlist(ComponentName who);

    void addPersistentPreferredActivity(ComponentName admin, String callerPackageName, IntentFilter filter, ComponentName activity);
    void clearPackagePersistentPreferredActivities(ComponentName admin, String callerPackageName, String packageName);

    void setDefaultSmsApplication(ComponentName admin, String callerPackageName, String packageName, boolean parent);
    void setDefaultDialerApplication(String packageName);

    void setApplicationRestrictions(ComponentName who, String callerPackage, String packageName, Bundle settings, boolean parent);
    void setApplicationRestrictions(ComponentName who, String callerPackage, String packageName, Bundle settings);
    Bundle getApplicationRestrictions(ComponentName who, String callerPackage, String packageName, boolean parent);
    Bundle getApplicationRestrictions(ComponentName who, String callerPackage, String packageName);
    boolean setApplicationRestrictionsManagingPackage(ComponentName admin, String packageName);
    String getApplicationRestrictionsManagingPackage(ComponentName admin);
    boolean isCallerApplicationRestrictionsManagingPackage(String callerPackage);

    void setRestrictionsProvider(ComponentName who, ComponentName provider);
    ComponentName getRestrictionsProvider(int userHandle);

    void setUserRestriction(ComponentName who, String callerPackage, String key, boolean enable, boolean parent);
    void setUserRestrictionForUser(String systemEntity, String key, boolean enable, int targetUser);
    void setUserRestrictionGlobally(String callerPackage, String key);
    void setUserRestrictionGloballyFromSystem(String systemEntity, String key, boolean enable);
    Bundle getUserRestrictions(ComponentName who, String callerPackage, boolean parent);
    Bundle getUserRestrictionsGlobally(String callerPackage);

    void addCrossProfileIntentFilter(ComponentName admin, String callerPackageName, IntentFilter filter, int flags);
    void clearCrossProfileIntentFilters(ComponentName admin, String callerPackageName);

    boolean setPermittedAccessibilityServices(ComponentName admin,List<String> packageList);
    List<String> getPermittedAccessibilityServices(ComponentName admin);
    List<String> getPermittedAccessibilityServicesForUser(int userId);
    boolean isAccessibilityServicePermittedByAdmin(ComponentName admin, String packageName, int userId);

    boolean setPermittedInputMethods(ComponentName admin, String callerPackageName, List<String> packageList, boolean parent);
    List<String> getPermittedInputMethods(ComponentName admin, String callerPackageName, boolean parent);
    List<String> getPermittedInputMethodsAsUser(int userId);
    boolean isInputMethodPermittedByAdmin(ComponentName admin, String packageName, int userId, boolean parent);

    boolean setPermittedCrossProfileNotificationListeners(ComponentName admin, List<String> packageList);
    List<String> getPermittedCrossProfileNotificationListeners(ComponentName admin);
    boolean isNotificationListenerServicePermitted(String packageName, int userId);
    Intent createAdminSupportIntent(String restriction);
    Bundle getEnforcingAdminAndUserDetails(int userId, String restriction);
    boolean setApplicationHidden(ComponentName admin, String callerPackage, String packageName, boolean hidden);
    boolean isApplicationHidden(ComponentName admin, String callerPackage, String packageName);
    boolean setApplicationHidden(ComponentName admin, String callerPackage, String packageName, boolean hidden, boolean parent);
    boolean isApplicationHidden(ComponentName admin, String callerPackage, String packageName, boolean parent);

    UserHandle createAndManageUser(ComponentName who, String name, ComponentName profileOwner, PersistableBundle adminExtras, int flags);
    boolean removeUser(ComponentName who, UserHandle userHandle);
    boolean switchUser(ComponentName who, UserHandle userHandle);
    int startUserInBackground(ComponentName who, UserHandle userHandle);
    int stopUser(ComponentName who, UserHandle userHandle);
    int logoutUser(ComponentName who);
    int logoutUserInternal(); // AIDL doesn't allow overloading name (logoutUser())
    int getLogoutUserId();
    List<UserHandle> getSecondaryUsers(ComponentName who);
    void acknowledgeNewUserDisclaimer(int userId);
    boolean isNewUserDisclaimerAcknowledged(int userId);

    void enableSystemApp(ComponentName admin, String callerPackage, String packageName);
    int enableSystemAppWithIntent(ComponentName admin, String callerPackage, Intent intent);
    boolean installExistingPackage(ComponentName admin, String callerPackage, String packageName);

    void setAccountManagementDisabled(ComponentName who, String callerPackageName, String accountType, boolean disabled, boolean parent);
    String[] getAccountTypesWithManagementDisabled(String callerPackageName);
    String[] getAccountTypesWithManagementDisabledAsUser(int userId, String callerPackageName, boolean parent);

    void setSecondaryLockscreenEnabled(ComponentName who, boolean enabled, PersistableBundle options);
    boolean isSecondaryLockscreenEnabled(UserHandle userHandle);

    void setPreferentialNetworkServiceConfigs(
            List<PreferentialNetworkServiceConfig> preferentialNetworkServiceConfigs);
    List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs();

    void setLockTaskPackages(ComponentName who, String callerPackageName, String[] packages);
    String[] getLockTaskPackages(ComponentName who, String callerPackageName);
    boolean isLockTaskPermitted(String pkg);

    void setLockTaskFeatures(ComponentName who, String callerPackageName, int flags);
    int getLockTaskFeatures(ComponentName who, String callerPackageName);

    void setGlobalSetting(ComponentName who, String setting, String value);
    void setSystemSetting(ComponentName who, String setting, String value, boolean parent);
    void setSecureSetting(ComponentName who, String setting, String value);

    void setConfiguredNetworksLockdownState(ComponentName who, String callerPackageName, boolean lockdown);
    boolean hasLockdownAdminConfiguredNetworks(ComponentName who);

    void setLocationEnabled(ComponentName who, boolean locationEnabled);

    boolean setTime(ComponentName who, String callerPackageName, long millis);
    boolean setTimeZone(ComponentName who, String callerPackageName, String timeZone);

    void setMasterVolumeMuted(ComponentName admin, boolean on);
    boolean isMasterVolumeMuted(ComponentName admin);

    void notifyLockTaskModeChanged(boolean isEnabled, String pkg, int userId);

    void setUninstallBlocked(ComponentName admin, String callerPackage, String packageName, boolean uninstallBlocked);
    boolean isUninstallBlocked(String packageName);

    void setCrossProfileCallerIdDisabled(ComponentName who, boolean disabled);
    boolean getCrossProfileCallerIdDisabled(ComponentName who);
    boolean getCrossProfileCallerIdDisabledForUser(int userId);
    void setCrossProfileContactsSearchDisabled(ComponentName who, boolean disabled);
    boolean getCrossProfileContactsSearchDisabled(ComponentName who);
    boolean getCrossProfileContactsSearchDisabledForUser(int userId);
    void startManagedQuickContact(String lookupKey, long contactId, boolean isContactIdIgnored, long directoryId, Intent originalIntent);

    void setManagedProfileCallerIdAccessPolicy(PackagePolicy policy);
    PackagePolicy getManagedProfileCallerIdAccessPolicy();
    boolean hasManagedProfileCallerIdAccess(int userId, String packageName);

    void setCredentialManagerPolicy(PackagePolicy policy);
    PackagePolicy getCredentialManagerPolicy(int userId);

    void setManagedProfileContactsAccessPolicy(PackagePolicy policy);
    PackagePolicy getManagedProfileContactsAccessPolicy();
    boolean hasManagedProfileContactsAccess(int userId, String packageName);

    void setBluetoothContactSharingDisabled(ComponentName who, boolean disabled);
    boolean getBluetoothContactSharingDisabled(ComponentName who);
    boolean getBluetoothContactSharingDisabledForUser(int userId);

    void setTrustAgentConfiguration(ComponentName admin, String callerPackageName,
                                    ComponentName agent, PersistableBundle args, boolean parent);
    List<PersistableBundle> getTrustAgentConfiguration(ComponentName admin,
                                                       ComponentName agent, int userId, boolean parent);

    boolean addCrossProfileWidgetProvider(ComponentName admin, String callerPackageName, String packageName);
    boolean removeCrossProfileWidgetProvider(ComponentName admin, String callerPackageName, String packageName);
    List<String> getCrossProfileWidgetProviders(ComponentName admin, String callerPackageName);

    void setAutoTimeRequired(ComponentName who, boolean required);
    boolean getAutoTimeRequired();

    void setAutoTimeEnabled(ComponentName who, String callerPackageName, boolean enabled);
    boolean getAutoTimeEnabled(ComponentName who, String callerPackageName);

    void setAutoTimePolicy(String callerPackageName, int policy);
    int getAutoTimePolicy(String callerPackageName);

    void setAutoTimeZoneEnabled(ComponentName who, String callerPackageName, boolean enabled);
    boolean getAutoTimeZoneEnabled(ComponentName who, String callerPackageName);

    void setAutoTimeZonePolicy(String callerPackageName, int policy);
    int getAutoTimeZonePolicy(String callerPackageName);

    void setForceEphemeralUsers(ComponentName who, boolean forceEpehemeralUsers);
    boolean getForceEphemeralUsers(ComponentName who);

    boolean isRemovingAdmin(ComponentName adminReceiver, int userHandle);

    void setUserIcon(ComponentName admin, Bitmap icon);

    void setSystemUpdatePolicy(ComponentName who, String callerPackageName, SystemUpdatePolicy policy);
    SystemUpdatePolicy getSystemUpdatePolicy();
    void clearSystemUpdatePolicyFreezePeriodRecord();

    boolean setKeyguardDisabled(ComponentName admin, boolean disabled);
    boolean setStatusBarDisabled(ComponentName who, String callerPackageName, boolean disabled);
    boolean isStatusBarDisabled(String callerPackage);
    boolean getDoNotAskCredentialsOnBoot();

    void notifyPendingSystemUpdate(SystemUpdateInfo info);
    SystemUpdateInfo getPendingSystemUpdate(ComponentName admin, String callerPackage);

    void setPermissionPolicy(ComponentName admin, String callerPackage, int policy);
    int  getPermissionPolicy(ComponentName admin);
    //void setPermissionGrantState(ComponentName admin, String callerPackage, String packageName, String permission, int grantState, RemoteCallback resultReceiver);
    int getPermissionGrantState(ComponentName admin, String callerPackage, String packageName, String permission);
    boolean isProvisioningAllowed(String action, String packageName);
    int checkProvisioningPrecondition(String action, String packageName);
    void setKeepUninstalledPackages(ComponentName admin, String callerPackage, List<String> packageList);
    List<String> getKeepUninstalledPackages(ComponentName admin, String callerPackage);
    boolean isManagedProfile(ComponentName admin);
    String getWifiMacAddress(ComponentName admin, String callerPackageName);
    void reboot(ComponentName admin);

    void setShortSupportMessage(ComponentName admin, String callerPackageName, CharSequence message);
    CharSequence getShortSupportMessage(ComponentName admin, String callerPackageName);
    void setLongSupportMessage(ComponentName admin, CharSequence message);
    CharSequence getLongSupportMessage(ComponentName admin);

    CharSequence getShortSupportMessageForUser(ComponentName admin, int userHandle);
    CharSequence getLongSupportMessageForUser(ComponentName admin, int userHandle);

    void setOrganizationColor(ComponentName admin, int color);
    void setOrganizationColorForUser(int color, int userId);
    void clearOrganizationIdForUser(int userHandle);
    int getOrganizationColor(ComponentName admin);
    int getOrganizationColorForUser(int userHandle);

    void setOrganizationName(ComponentName admin, String callerPackageName, CharSequence title);
    CharSequence getOrganizationName(ComponentName admin, String callerPackageName);
    CharSequence getOrganizationName(ComponentName admin);
    CharSequence getDeviceOwnerOrganizationName();
    CharSequence getOrganizationNameForUser(int userHandle);

    int getUserProvisioningState(int userHandle);
    void setUserProvisioningState(int state, int userHandle);

    void setAffiliationIds(ComponentName admin, List<String> ids);
    List<String> getAffiliationIds(ComponentName admin);
    boolean isCallingUserAffiliated();
    boolean isAffiliatedUser(int userId);

    void setSecurityLoggingEnabled(ComponentName admin, String packageName, boolean enabled);
    boolean isSecurityLoggingEnabled(ComponentName admin, String packageName);
    long forceNetworkLogs();
    long forceSecurityLogs();

    void setAuditLogEnabled(String callerPackage, boolean enabled);
    boolean isAuditLogEnabled(String callerPackage);
    void setAuditLogEventsCallback(String callerPackage, IAuditLogEventsCallback callback);

    boolean isUninstallInQueue(String packageName);
    void uninstallPackageWithActiveAdmins(String packageName);

    boolean isDeviceProvisioned();
    boolean isDeviceProvisioningConfigApplied();
    void setDeviceProvisioningConfigApplied();

    void forceUpdateUserSetupComplete(int userId);

    void setBackupServiceEnabled(ComponentName admin, boolean enabled);
    boolean isBackupServiceEnabled(ComponentName admin);

    void setNetworkLoggingEnabled(ComponentName admin, String packageName, boolean enabled);
    boolean isNetworkLoggingEnabled(ComponentName admin, String packageName);
    List<NetworkEvent> retrieveNetworkLogs(ComponentName admin, String packageName, long batchToken);

    List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName admin);
    boolean isEphemeralUser(ComponentName admin);

    long getLastSecurityLogRetrievalTime();
    long getLastBugReportRequestTime();
    long getLastNetworkLogRetrievalTime();

    boolean setResetPasswordToken(ComponentName admin, String callerPackageName, byte[] token);
    boolean clearResetPasswordToken(ComponentName admin, String callerPackageName);
    boolean isResetPasswordTokenActive(ComponentName admin, String callerPackageName);
    boolean resetPasswordWithToken(ComponentName admin, String callerPackageName, String password, byte[] token, int flags);

    boolean isCurrentInputMethodSetByOwner();
    void clearApplicationUserData(ComponentName admin, String packageName, IPackageDataObserver callback);

    void setLogoutEnabled(ComponentName admin, boolean enabled);
    boolean isLogoutEnabled();

    List<String> getDisallowedSystemApps(ComponentName admin, int userId, String provisioningAction);

    void transferOwnership(ComponentName admin, ComponentName target, PersistableBundle bundle);
    PersistableBundle getTransferOwnershipBundle();

    void setStartUserSessionMessage(ComponentName admin, CharSequence startUserSessionMessage);
    void setEndUserSessionMessage(ComponentName admin, CharSequence endUserSessionMessage);
    CharSequence getStartUserSessionMessage(ComponentName admin);
    CharSequence getEndUserSessionMessage(ComponentName admin);

    List<String> setMeteredDataDisabledPackages(ComponentName admin, List<String> packageNames);
    List<String> getMeteredDataDisabledPackages(ComponentName admin);

    int addOverrideApn(ComponentName admin, ApnSetting apnSetting);
    boolean updateOverrideApn(ComponentName admin, int apnId, ApnSetting apnSetting);
    boolean removeOverrideApn(ComponentName admin, int apnId);
    List<ApnSetting> getOverrideApns(ComponentName admin);
    void setOverrideApnsEnabled(ComponentName admin, boolean enabled);
    boolean isOverrideApnEnabled(ComponentName admin);

    boolean isMeteredDataDisabledPackageForUser(ComponentName admin, String packageName, int userId);

    int setGlobalPrivateDns(ComponentName admin, int mode, String privateDnsHost);
    int getGlobalPrivateDnsMode(ComponentName admin);
    String getGlobalPrivateDnsHost(ComponentName admin);
    void setProfileOwnerOnOrganizationOwnedDevice(ComponentName who, int userId, boolean isProfileOwnerOnOrganizationOwnedDevice);
    void setCrossProfileCalendarPackages(ComponentName admin, List<String> packageNames);
    List<String> getCrossProfileCalendarPackages(ComponentName admin);
    boolean isPackageAllowedToAccessCalendarForUser(String packageName, int userHandle);
    List<String> getCrossProfileCalendarPackagesForUser(int userHandle);

    void setCrossProfilePackages(ComponentName admin, List<String> packageNames);
    List<String> getCrossProfilePackages(ComponentName admin);

    List<String> getAllCrossProfilePackages(int userId);
    List<String> getDefaultCrossProfilePackages();
    boolean isManagedKiosk();
    boolean isUnattendedManagedKiosk();

    boolean startViewCalendarEventInManagedProfile(String packageName, long eventId, long start, long end, boolean allDay, int flags);
    boolean setKeyGrantToWifiAuth(String callerPackage, String alias, boolean hasGrant);
    boolean isKeyPairGrantedToWifiAuth(String callerPackage, String alias);
    void setUserControlDisabledPackages(ComponentName admin, String callerPackageName, List<String> packages);
    List<String> getUserControlDisabledPackages(ComponentName admin, String callerPackageName);

    void setCommonCriteriaModeEnabled(ComponentName admin, String callerPackageName, boolean enabled);
    boolean isCommonCriteriaModeEnabled(ComponentName admin);

    int getPersonalAppsSuspendedReasons(ComponentName admin);
    void setPersonalAppsSuspended(ComponentName admin, boolean suspended);
    long getManagedProfileMaximumTimeOff(ComponentName admin);
    void setManagedProfileMaximumTimeOff(ComponentName admin, long timeoutMs);

    void acknowledgeDeviceCompliant();
    boolean isComplianceAcknowledgementRequired();

    boolean canProfileOwnerResetPasswordWhenLocked(int userId);

    void setNextOperationSafety(int operation, int reason);
    boolean isSafeOperation(int reason);

    String getEnrollmentSpecificId(String callerPackage);
    void setOrganizationIdForUser(String callerPackage, String enterpriseId, int userId);
    void finalizeWorkProfileProvisioning(UserHandle managedProfileUser, Account migratedAccount);

    boolean removeManagedProfile(int userId);

    void setDeviceOwnerType(ComponentName admin, int deviceOwnerType);
    int getDeviceOwnerType(ComponentName admin);

    void resetDefaultCrossProfileIntentFilters(int userId);
    boolean canAdminGrantSensorsPermissions();

    void setUsbDataSignalingEnabled(String callerPackage, boolean enabled);
    boolean isUsbDataSignalingEnabled(String callerPackage);
    boolean canUsbDataSignalingBeDisabled();

    void setMinimumRequiredWifiSecurityLevel(String callerPackageName, int level);
    int getMinimumRequiredWifiSecurityLevel();

    void setWifiSsidPolicy(String callerPackageName, WifiSsidPolicy policy);
    WifiSsidPolicy getWifiSsidPolicy(String callerPackageName);

    boolean isDevicePotentiallyStolen(String callerPackageName);

    List<UserHandle> listForegroundAffiliatedUsers();
    void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState();
    boolean shouldAllowBypassingDevicePolicyManagementRoleQualification();

    List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle);

    void setApplicationExemptions(String callerPackage, String packageName, int[]exemptions);
    int[] getApplicationExemptions(String packageName);

    void setMtePolicy(int flag, String callerPackageName);
    void setMtePolicyBySystem(String systemEntity, int policy);
    int getMtePolicy(String callerPackageName);

    void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy policy);
    ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy();

    boolean isSubscriptionEnterpriseManaged(SubscriptionInfo info, String packageName);

    boolean triggerDevicePolicyEngineMigration(boolean forceMigration);

    boolean isDeviceFinanced(String callerPackageName);
    String getFinancedDeviceKioskRoleHolder(String callerPackageName);

    void calculateHasIncompatibleAccounts();

    void setContentProtectionPolicy(ComponentName who, String callerPackageName, int policy);
    int getContentProtectionPolicy(ComponentName who, String callerPackageName, int userId);

    int[] getSubscriptionIds(String callerPackageName);

    void setMaxPolicyStorageLimit(String callerPackageName, int storageLimit);
    void forceSetMaxPolicyStorageLimit(String callerPackageName, int storageLimit);
    int getMaxPolicyStorageLimit(String callerPackageName);
    int getHeadlessDeviceOwnerMode(String callerPackageName);
    void setAppFunctionsPolicy(String callerPackageName, int policy);
    int getAppFunctionsPolicy(String callerPackageName, int userId);

    abstract class Stub extends Binder implements IDevicePolicyManager {
        public static IDevicePolicyManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}