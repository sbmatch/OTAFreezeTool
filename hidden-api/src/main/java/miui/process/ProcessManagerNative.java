package miui.process;

import android.os.Binder;
import android.os.IBinder;

public abstract class ProcessManagerNative extends Binder implements IProcessManager {

    public ProcessManagerNative() {
        throw new RuntimeException("Stub!");
    }

    public static IProcessManager asInterface(IBinder obj){
        throw new RuntimeException("Stub!");
    }

    public static IProcessManager getDefault() {
        throw new RuntimeException("Stub!");
    }

}
