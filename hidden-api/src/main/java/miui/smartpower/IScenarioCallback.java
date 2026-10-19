package miui.smartpower;

import android.app.IProcessObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface IScenarioCallback extends IInterface {
    void onCurrentScenarioChanged(long mainSenarioId, long additionalSenarioId);

    abstract class Stub extends Binder implements IScenarioCallback {
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
