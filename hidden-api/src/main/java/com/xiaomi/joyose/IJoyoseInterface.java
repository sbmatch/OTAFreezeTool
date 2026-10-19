package com.xiaomi.joyose;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

import java.util.List;

public interface IJoyoseInterface extends IInterface {

    String querySettingsByAppName(String name);
    List<String> getGameMotorAppList();
    boolean checkIfSupportFrameInsert(String packageName);
    void registerMonitorSurface(String mSurfaceName, IMonitorSurface ms);
    void unregisterMonitorSurface(String mSurfaceName, IMonitorSurface ms);

    abstract class Stub extends Binder implements IJoyoseInterface {
        public static final String SERVICE_NAME = "xiaomi.joyose";
        public static int REGISTER_FAIL = -1;
        public static int REGISTER_ALREADY = 0;
        public static int REGISTER_SUCCESS = 1;
        public static int UNREGISTER_FAIL = -1;
        public static int UNREGISTER_SUCCESS = 1;
        public static IJoyoseInterface asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
