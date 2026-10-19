package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

import java.util.List;

public interface IActivityManagerRunningTaskInfoChangeListener extends IInterface {
    void onActivityManagerRunningTaskInfoChangeCallback(List<ActivityManager.RunningTaskInfo> list);

    abstract class Stub extends Binder implements IActivityManagerRunningTaskInfoChangeListener {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
