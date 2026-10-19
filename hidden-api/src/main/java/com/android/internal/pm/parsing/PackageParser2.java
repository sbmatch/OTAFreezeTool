package com.android.internal.pm.parsing;

import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;

import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;

import java.io.File;

public class PackageParser2 implements AutoCloseable {

    public PackageParser2(String[] separateProcesses, DisplayMetrics displayMetrics, IPackageCacher cacher, Callback callback) {
        throw new RuntimeException("Stub!");
    }

    public ParsedPackage parsePackage(File packageFile, int flags, boolean useCaches)
            throws PackageParserException{
        throw new RuntimeException("Stub!");
    }

    @Override
    public void close() throws Exception {
        throw new RuntimeException("Stub!");
    }

    public abstract static class Callback implements ParsingPackageUtils.Callback {
        public abstract boolean isChangeEnabled(long j, ApplicationInfo applicationInfo);

        public final ParsingPackage startParsingPackage(String packageName, String baseCodePath, String codePath, TypedArray manifestArray, boolean isCoreApp) {
            throw new RuntimeException("Stub!");
        }
    }

}
