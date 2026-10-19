package miui.security;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

import java.util.List;

public interface IAppRunningControlManager extends IInterface {
    void setDisallowRunningList(List<String> list, Intent intent);

    Intent getBlockActivityIntent(String packageName, Intent intent, boolean fromActivity, int requestCode);

    void setBlackListEnable(boolean isEnable);
    boolean matchRule(String pkgName, int wakeType);
    List<String> getNotDisallowList();

    abstract class Stub extends Binder implements IAppRunningControlManager{

        public static IAppRunningControlManager asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }

}
