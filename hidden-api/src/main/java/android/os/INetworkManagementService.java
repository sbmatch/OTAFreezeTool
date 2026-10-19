package android.os;

import android.net.INetworkManagementEventObserver;
import android.net.InterfaceConfiguration;

public interface INetworkManagementService extends IInterface {

    /**
     * Register an observer to receive events.
     */
    void registerObserver(INetworkManagementEventObserver obs);

    /**
     * Unregister an observer from receiving events.
     */
    void unregisterObserver(INetworkManagementEventObserver obs);


    /**
     * Returns a list of currently known network interfaces
     */
    String[] listInterfaces();

    /**
     * Retrieves the specified interface config
     *
     */
    InterfaceConfiguration getInterfaceConfig(String iface);

    /**
     * Sets the configuration of the specified interface
     */
    void setInterfaceConfig(String iface,  InterfaceConfiguration cfg);

    /**
     * Clear all IP addresses on the specified interface
     */
    void clearInterfaceAddresses(String iface);

    /**
     * Set interface down
     */
    void setInterfaceDown(String iface);

    /**
     * Set interface up
     */
    void setInterfaceUp(String iface);

    /**
     * Set interface IPv6 privacy extensions
     */
    void setInterfaceIpv6PrivacyExtensions(String iface, boolean enable);

    /**
     * Disable IPv6 on an interface
     */
    void disableIpv6(String iface);

    /**
     * Enable IPv6 on an interface
     */
    void enableIpv6(String iface);

    /**
     * Set IPv6 autoconf address generation mode.
     * This is a no-op if an unsupported mode is requested.
     */
    void setIPv6AddrGenMode(String iface, int mode);
    /**
     * Returns true if IP forwarding is enabled
     */

    boolean getIpForwardingEnabled();

    /**
     * Enables/Disables IP Forwarding
     */

    void setIpForwardingEnabled(boolean enabled);

    /**
     * Start tethering services with the specified dhcp server range
     * arg is a set of start end pairs defining the ranges.
     */
     void startTethering(String[] dhcpRanges);

    /**
     * Stop currently running tethering services
     */
    void stopTethering();

    /**
     * Returns true if tethering services are started
     */
    boolean isTetheringStarted();

    /**
     * Tethers the specified interface
     */
    void tetherInterface(String iface);

    /**
     * Untethers the specified interface
     */
    void untetherInterface(String iface);

    /**
     * Returns a list of currently tethered interfaces
     */
    String[] listTetheredInterfaces();

    boolean isNetworkRestricted(int uid);

    void denyProtect(int uid);
    void allowProtect(int uid);
    void setUidOnMeteredNetworkDenylist(int uid, boolean enable);
    IBinder getMiuiNetworkManager();
    void setFirewallUidRule(int chain, int uid, int rule);

    abstract class Stub extends Binder implements INetworkManagementService {
        public static INetworkManagementService asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }

}
