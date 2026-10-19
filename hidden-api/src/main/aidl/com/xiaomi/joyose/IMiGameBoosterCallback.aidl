package com.xiaomi.joyose;

import android.os.Bundle;

interface IMiGameBoosterCallback {
    void onEvent(int event, in Bundle args);
    Bundle onEventSync(int event, in Bundle args);
}