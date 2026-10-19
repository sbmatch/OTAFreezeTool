package android.net.wifi;

import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

import com.android.modules.utils.ParceledListSlice;

public interface IWifiManager extends IInterface {
    //ParceledListSlice<WifiConfiguration> getPrivilegedConfiguredNetworks(String packageName, String featureId, Bundle extras);
    boolean setWifiEnabled(String packageName, boolean enable);
    WifiConfiguration getPrivilegedConnectedNetwork(
            String packageName, String featureId, Bundle extras);

    boolean disconnect(String packageName);
    void connect(WifiConfiguration config, int netId, IActionListener listener,
                        String packageName, Bundle extras);

    int addOrUpdateNetwork(WifiConfiguration config, String packageName, Bundle extras);
    WifiManager.AddNetworkResult addOrUpdateNetworkPrivileged(WifiConfiguration config, String packageName);
    void addWifiStateChangedListener(IWifiStateChangedListener listener);
    void removeWifiStateChangedListener(IWifiStateChangedListener listener);
    boolean addOrUpdatePasspointConfiguration(PasspointConfiguration config, String packageName);
    boolean removePasspointConfiguration(String fqdn, String packageName);

    //ParceledListSlice<PasspointConfiguration> getPasspointConfigurations(String packageName);
    void registerScanResultsCallback(IScanResultsCallback callback);
    void unregisterScanResultsCallback(IScanResultsCallback callback);

    boolean startScan(String packageName, String featureId);
    //ParceledListSlice<ScanResult> getScanResults(String callingPackage, String callingFeatureId);

    int removeNetworkSuggestions(ParceledListSlice<WifiNetworkSuggestion> networkSuggestions,
                                 String packageName, int action);

    //ParceledListSlice<WifiNetworkSuggestion> getNetworkSuggestions(String packageName);


    int getWifiApEnabledState();

    WifiConfiguration getWifiApConfiguration();

    SoftApConfiguration getSoftApConfiguration();

    void queryLastConfiguredTetheredApPassphraseSinceBoot(IStringListener listener);

    boolean setWifiApConfiguration(WifiConfiguration wifiConfig, String packageName);

    boolean setSoftApConfiguration(SoftApConfiguration softApConfig, String packageName);

    abstract class Stub extends Binder implements IWifiManager {

        public static IWifiManager asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
