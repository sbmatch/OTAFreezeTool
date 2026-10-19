package com.sbmatch.androtool.utils;

import android.content.pm.PackageParser;

import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.pkg.ParsedPackage;

import java.io.File;

public class ParsedPackageHelper {

    public static ParsedPackage parsePackage2(String archiveFilePath){
        return parsePackage2(archiveFilePath, null);
    }
    public static ParsedPackage parsePackage2(String archiveFilePath, PackageParser2.Callback callback2){
        return parsePackage2(archiveFilePath, false, callback2);
    }
    public static ParsedPackage parsePackage2(String archiveFilePath, boolean useCaches, PackageParser2.Callback callback2){
        return parsePackage2(new File(archiveFilePath), 0, useCaches, callback2);
    }

    public static ParsedPackage parsePackage2(File packageFile, int flags, boolean useCaches, PackageParser2.Callback callback2) {
        try (PackageParser2 parser2 = new PackageParser2(null, null, null, callback2)) {
            return parser2.parsePackage(packageFile, flags, useCaches);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static PackageParser.Package parsePackage(File packageFile){
        return parsePackage(packageFile, 0, true);
    }

    public static PackageParser.Package parsePackage(File packageFile, int flags){
        return parsePackage(packageFile, flags, false);
    }
    public static PackageParser.Package parsePackage(File packageFile, boolean useCaches){
        return parsePackage(packageFile, 0, useCaches);
    }
    public static PackageParser.Package parsePackage(File packageFile, int flags, boolean useCaches){
        try {
            PackageParser parsedPackage = new PackageParser();
            return useCaches ? parsedPackage.parsePackage(packageFile, flags, true) : parsedPackage.parsePackage(packageFile, flags);
        }catch (PackageParser.PackageParserException e){
            return null;
        }
    }
}
