package com.android.internal.app;

import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

import java.util.List;

public interface IBatteryStats extends IInterface {
    List<BatteryUsageStats> getBatteryUsageStats(List<BatteryUsageStatsQuery> queries);

    abstract class Stub extends Binder implements IBatteryStats {

        public static IBatteryStats asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
