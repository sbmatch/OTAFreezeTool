package vivo.app.net;

import android.os.IInterface;

import java.util.List;

public interface IVivoNetworkAssistanceService extends IInterface {
    boolean setMobileUidFirewall(List<Integer> uids);
    boolean setUidFirewallForUserChain(int networkType, String chain, List<Integer> uids);
    void closeSockets(List<Integer> uids, List<Integer> exemptUids);
    boolean setWifiUidFirewall(List<Integer> uids);

    abstract class Stub extends android.os.Binder implements IVivoNetworkAssistanceService {
        public static IVivoNetworkAssistanceService asInterface(android.os.IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }
}
