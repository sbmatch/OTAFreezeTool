package android.app;

public abstract class IMiuiProcessObserver extends IProcessObserver.Stub{
    public abstract void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities);

    public abstract void onImportanceChanged(int pid, int uid, int importance);

    public abstract void onProcessDied(int pid, int uid);

    public abstract void onProcessStateChanged(int pid, int uid, int procState);


}
