package android.app;

import android.content.pm.IPackageManager;

public final class ActivityThread {
    public static void main(String[] args) {
    }

    public ApplicationThread getApplicationThread()
    {
        throw new RuntimeException("Stub!");
    }
    public static ActivityThread systemMain() {
        throw new RuntimeException("STUB");
    }

    public static ActivityThread currentActivityThread() {
        throw new RuntimeException("STUB");
    }

    public static Application currentApplication() {
        throw new RuntimeException("STUB");
    }
    public ContextImpl getSystemContext() {
        throw new RuntimeException("STUB");
    }

    public Application getApplication() {
        throw new RuntimeException("STUB");
    }

    public static boolean isSystem() { throw new RuntimeException("STUB"); }
    public static String currentPackageName() { throw new RuntimeException("STUB");}

    public static IPackageManager getPackageManager() { throw new RuntimeException("STUB");}
    private static void initializeSystemThread(ActivityThread thread){ throw new RuntimeException("STUB");}

    private class ApplicationThread extends IApplicationThread.Stub {

    }

}
