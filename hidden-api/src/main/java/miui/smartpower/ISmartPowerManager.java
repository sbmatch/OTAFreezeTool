package miui.smartpower;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface ISmartPowerManager extends IInterface {

    void registThermalScenarioCallback(IScenarioCallback callback);

    abstract class Stub extends Binder implements ISmartPowerManager {
        public static ISmartPowerManager asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
