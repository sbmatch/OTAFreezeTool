package android.permission;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

import com.android.internal.infra.AndroidFuture;

public interface IPermissionController extends IInterface {
    void revokeRuntimePermissions(Bundle request, boolean doDryRun, int reason, String callerPackageName, AndroidFuture callback);
    void revokeRuntimePermission(String packageName, String permissionName);

    void setRuntimePermissionGrantStateByDeviceAdminFromParams(String callerPackageName, AdminPermissionControlParams params, AndroidFuture callback);

    abstract class Stub extends Binder implements IPermissionController {

        public static IPermissionController asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
