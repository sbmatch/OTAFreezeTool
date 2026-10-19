package com.android.server.am;

import android.util.proto.ProtoOutputStream;

import com.android.server.wm.WindowProcessListener;

public class ProcessRecord implements WindowProcessListener {
    @Override
    public void clearProfilerIfNeeded() {

    }

    @Override
    public void updateServiceConnectionActivities() {

    }

    @Override
    public void setPendingUiClean(boolean pendingUiClean) {

    }

    @Override
    public void setPendingUiCleanAndForceProcessStateUpTo(int newState) {

    }

    @Override
    public void updateProcessInfo(boolean updateServiceConnectionActivities, boolean activityChange, boolean updateOomAdj) {

    }

    @Override
    public boolean isRemoved() {
        return false;
    }

    @Override
    public boolean isCached() {
        return false;
    }

    @Override
    public long getCpuTime() {
        return 0;
    }

    @Override
    public void onStartActivity(int topProcessState, boolean setProfileProc, String packageName, long versionCode) {

    }

    @Override
    public void appDied(String reason) {

    }

    @Override
    public void dumpDebug(ProtoOutputStream proto, long fieldId) {

    }

    @Override
    public void setRunningRemoteAnimation(boolean runningRemoteAnimation) {

    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
