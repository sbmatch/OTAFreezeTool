package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface INetd extends IInterface {
    int FIREWALL_ALLOWLIST = 0;
    int FIREWALL_BLACKLIST = 1;
    int FIREWALL_CHAIN_DOZABLE = 1;
    int FIREWALL_CHAIN_NONE = 0;
    int FIREWALL_CHAIN_POWERSAVE = 3;
    int FIREWALL_CHAIN_RESTRICTED = 4;
    int FIREWALL_CHAIN_STANDBY = 2;
    int FIREWALL_DENYLIST = 1;
    int FIREWALL_RULE_ALLOW = 1;
    int FIREWALL_RULE_DENY = 2;
    int FIREWALL_WHITELIST = 0;
    IBinder getOemNetd();

    abstract class Stub extends Binder implements INetd {

        public static INetd asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
