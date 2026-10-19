package com.xiaomi.joyose;

interface IGameEngineCallback {
    void onUpdateGameInfo(String data);
    void onApplyHardwareResource(String data);
    void onRestoreDefaultConfig();
    void onEventHandle(int event, String org1, String org2);
}