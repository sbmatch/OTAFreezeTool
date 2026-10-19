/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.pm.pkg.parsing;

import android.content.res.TypedArray;

import java.util.Set;

public class ParsingPackageUtils {

    /**
     * Callback interface for retrieving information that may be needed while parsing
     * a package.
     */
    public interface Callback {
        boolean hasFeature(String feature);
        ParsingPackage startParsingPackage(String packageName, String baseApkPath, String path, TypedArray manifestArray, boolean isCoreApp);
        Set<String> getHiddenApiWhitelistedApps();
        Set<String> getInstallConstraintsAllowlist();
    }
}
