package miui.process;

import android.os.IInterface;

public interface IProcessManager extends IInterface {
    void registerPreloadCallback(IPreloadCallback iPreloadCallback, int type);
    void updateConfig(ProcessConfig processConfig);
    void updateCloudData(ProcessCloudData processCloudData);
}
