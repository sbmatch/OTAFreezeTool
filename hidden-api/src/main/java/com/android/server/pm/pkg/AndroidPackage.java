package com.android.server.pm.pkg;

import android.os.Bundle;

import com.android.internal.pm.pkg.component.ParsedActivity;

import java.util.List;

public interface AndroidPackage {
    List<ParsedActivity> getReceivers();
}
