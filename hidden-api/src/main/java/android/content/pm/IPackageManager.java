package android.content.pm;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

public interface IPackageManager extends IInterface {
    boolean isPackageAvailable(String packageName, int userId);

    boolean getApplicationHiddenSettingAsUser(String packageName, int userId);

    ApplicationInfo getApplicationInfo(String packageName, int flags, int userId);

    ApplicationInfo getApplicationInfo(String packageName, long flags, int userId);

    PackageInfo getPackageInfo(String packageName, int flags, int userId);
    PackageInfo getPackageInfo(String packageName, long flags, int userId);

    int getPackageUid(String packageName, int flags, int userId);
    int getPackageUid(String packageName, long flags, int userId);

    String[] getPackagesForUid(int uid);

    String getNameForUid(int uid);

    ParceledListSlice<ProviderInfo> queryContentProviders(
            String processName, int uid, long flags, String metaDataKey);

    ParceledListSlice<PackageInfo> getInstalledPackages(int flags, int userId);

    ParceledListSlice<PackageInfo> getInstalledPackages(long flags, int userId);

    ParceledListSlice<ApplicationInfo> getInstalledApplications(int flags, int userId);

    ParceledListSlice<ApplicationInfo> getInstalledApplications(long flags, int userId);

    int getUidForSharedUser(String sharedUserName);

    void grantRuntimePermission(String packageName, String permissionName, int userId);

    void revokeRuntimePermission(String packageName, String permissionName, int userId);

    int getPermissionFlags(String permissionName, String packageName, int userId);

    void updatePermissionFlags(String permissionName, String packageName, int flagMask, int flagValues, int userId);

    int checkPermission(String permName, String pkgName, int userId);

    int checkUidPermission(String permName, int uid);

    IPackageInstaller getPackageInstaller();

    int installExistingPackageAsUser(String packageName, int userId, int installFlags,
                                     int installReason);


    int installExistingPackageAsUser(String packageName, int userId, int installFlags,
                                     int installReason, List<String> whiteListedPermissions);

    ParceledListSlice<ResolveInfo> queryIntentActivities(Intent intent, String resolvedType, int flags, int userId);

    ParceledListSlice<ResolveInfo> queryIntentActivities(Intent intent,
                                                         String resolvedType, long flags, int userId);
    ParceledListSlice<ResolveInfo> queryIntentServices(Intent intent,
                                          String resolvedType, long flags, int userId);
    ParceledListSlice<ResolveInfo> queryIntentServices(Intent intent,
                                          String resolvedType, int flags, int userId);
    ParceledListSlice<ResolveInfo> queryIntentReceivers(Intent intent,
                                           String resolvedType, long flags, int userId);
    ParceledListSlice<ResolveInfo> queryIntentReceivers(Intent intent,
                                           String resolvedType, int flags, int userId);


    void setLastChosenActivity(Intent intent, String resolvedType, int flags,
                               IntentFilter filter, int match, ComponentName activity);

    // not work when api >= Android S(12)
    void addPreferredActivity(IntentFilter filter, int match,
                              ComponentName[] set, ComponentName activity, int userId);

    void addPreferredActivity(IntentFilter filter, int match,
                              ComponentName[] set, ComponentName activity, int userId, boolean removeExisting);


    void clearPackagePreferredActivities(String packageName);

    void addPersistentPreferredActivity(IntentFilter filter, ComponentName activity, int userId);

    void clearPackagePersistentPreferredActivities(String packageName, int userId);

    void flushPackageRestrictionsAsUser(int userId);
    void deletePackageAsUser(String packageName, int versionCode, IPackageDeleteObserver observer, int userId, int flags);
    void deleteExistingPackageAsUser(VersionedPackage versionedPackage,
                                     IPackageDeleteObserver2 observer, int userId);

    abstract class Stub extends Binder implements IPackageManager {

        public static IPackageManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
