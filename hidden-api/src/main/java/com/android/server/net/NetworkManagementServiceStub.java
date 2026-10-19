package com.android.server.net;

public interface NetworkManagementServiceStub {

    boolean setMiuiFirewallRule(String packageName, int uid, int rule, int type);

    static NetworkManagementServiceStub getInstance() {
        throw new RuntimeException("Stub!");
    }

}
