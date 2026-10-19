package com.android.server.net;

public class MiuiNetworkManagementService {
    private static final int ALLOW = 0;
    private static final int MIUI_FIREWALL_RESPONSE_CODE = 699;
    private static final int REJECT = 1;
    private static final String TAG = "NetworkManagement";
    private static final int TYPE_ALL = 3;
    private static final int TYPE_MOBILE = 0;
    private static final int TYPE_ROAMING = 2;
    private static final int TYPE_WIFI = 1;

    public static synchronized MiuiNetworkManagementService getInstance(){
        throw new RuntimeException("Stub!");
    }

    public boolean setMiuiFirewallRule(String packageName, int uid, int rule, int type){
        throw new RuntimeException("Stub!");
    }
}
