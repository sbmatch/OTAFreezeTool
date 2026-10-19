package android.app;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IVivoProcessObserver extends IInterface {

    void onProcessCreated(int pid, int uid, String packageName, String processName);
    void onProcessDied(int pid, int uid, String packageName, String processName);
    void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities, String packageName, String processName, ComponentName cpn);
    void onForegroundServicesChanged(int pid, int uid, int serviceTypes, String packageName, String processName);

    abstract class Stub extends Binder implements IVivoProcessObserver {
        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }
    }
}
