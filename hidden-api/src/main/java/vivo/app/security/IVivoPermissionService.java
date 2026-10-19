package vivo.app.security;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

import com.vivo.services.security.client.IVivoPermissionCallback;
import com.vivo.services.security.client.VivoPermissionInfo;

import java.util.List;
import java.util.Map;

public interface IVivoPermissionService extends IInterface {

    VivoPermissionInfo getAppPermission(String packageName);
    VivoPermissionInfo getAppPermissionForUserId(String packageName, int userId);

    void setAppPermissionForUserId(String packageName, int vpTypeId, int result, int userId);

    void setAppPermissionExtForUserId(VivoPermissionInfo vpi, int userId);

    int checkPermission(String permission, int pid, int uid, IVivoPermissionCallback cb);

    List<String> getPermissionWhiteList(int vpTypeId);

    void setWhiteListApp(String packageName, boolean enable);

    void setPermissionConfig(int op, int result, int userId);
    Map<Integer, Integer> getPermissionConfig(int userId);
    List<VivoPermissionInfo> getMonitorAppListForUserId(int userId);

    abstract class Stub extends Binder implements IVivoPermissionService {

        public static IVivoPermissionService asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
