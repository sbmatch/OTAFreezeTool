package com.android.internal.pm.pkg.parsing;

import android.os.Bundle;

import com.android.internal.pm.pkg.component.ParsedActivity;

import java.util.List;

public interface ParsingPackage {
    Bundle getMetaData();
    List<ParsedActivity> getReceivers();
}
