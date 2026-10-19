package android.permission;

import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

import java.util.Map;

public interface IPermissionManager extends IInterface {

    void grantRuntimePermission(String packageName, String permissionName, String persistentDeviceId, int userId);
    void grantRuntimePermission(String packageName, String permissionName, int userId);

    void revokeRuntimePermission(String packageName, String permissionName, String persistentDeviceId, int userId, String reason);
    void revokeRuntimePermission(String packageName, String permissionName, int userId, String reason);

    int getPermissionFlags(String packageName, String permissionName, int userId);
    int getPermissionFlags(String packageName, String permissionName, String persistentDeviceId, int userId);

    void updatePermissionFlags(String packageName, String permissionName, int flagMask,
                               int flagValues, boolean checkAdjustPolicyFlagPermission, int userId);
    void updatePermissionFlags(String packageName, String permissionName, int flagMask,
                               int flagValues, boolean checkAdjustPolicyFlagPermission, String persistentDeviceId,
                               int userId);
    void updatePermissionFlagsForAllApps(int flagMask, int flagValues, int userId);

    int checkPermission(String packageName, String permissionName, String persistentDeviceId, int userId);
    int checkUidPermission(int uid, String permissionName, int userId);

    void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener);
    void removeOnPermissionsChangeListener(IOnPermissionsChangeListener listener);
    PermissionInfo getPermissionInfo(String permissionName, String packageName, int flags);
    Map<String, PermissionManager.PermissionState> getAllPermissionStates(String packageName, String persistentDeviceId, int userId);

    ParceledListSlice<PermissionGroupInfo> getAllPermissionGroups(int flags);
    ParceledListSlice<PermissionInfo> queryPermissionsByGroup(String groupName, int flags);

    abstract class Stub extends Binder implements IPermissionManager {
        public static IPermissionManager asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
