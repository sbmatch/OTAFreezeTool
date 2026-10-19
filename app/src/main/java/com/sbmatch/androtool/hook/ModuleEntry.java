package com.sbmatch.androtool.hook;

import miui.util.ReflectionUtils;
import java.lang.reflect.Method;
import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface;

public class ModuleEntry extends XposedModule {
    private static final String TAG = "AndroTool";

    @Override
    public void onSystemServerStarting(XposedModuleInterface.SystemServerStartingParam param) {
        try {
            ClassLoader cl = param.getClassLoader();
            if (cl == null) cl = ClassLoader.getSystemClassLoader();
            Class<?> pmsClass = cl.loadClass("com.android.server.pm.PackageManagerService");
            Method systemReady = pmsClass.getDeclaredMethod("systemReady");

            hook(systemReady).intercept(new XposedInterface.Hooker() {
                @Override
                public Object intercept(XposedInterface.Chain chain) throws Throwable {
                    Object result = chain.proceed();
                    Object pms = chain.getThisObject();
                    injectSystemSignatures(pms, "com.sbmatch.androtool");
                    return result;
                }
            });
        } catch (Throwable t) {
            log(android.util.Log.ERROR, TAG, "PMS hook failed", t);
        }
    }

    public void injectSystemSignatures(Object pm, String packageName) {
        try {
            Object mSettings = ReflectionUtils.getObjectField(pm, "mSettings", Object.class);

            Object ps = ReflectionUtils.callMethod(mSettings, "getPackageLPr", Object.class, packageName);
            Object ourSigns = ReflectionUtils.callMethod(ps, "getSigningDetails", Object.class);

            Object ourCert = java.lang.reflect.Array.get(
                    ReflectionUtils.callMethod(ourSigns, "getSignatures", Object.class), 0
            );

            ReflectionUtils.callMethod(ourCert, "setFlags", Void.class, 2);

            Object newSysCerts = java.lang.reflect.Array.newInstance(ourCert.getClass(), 2);
            java.lang.reflect.Array.set(newSysCerts, 0, ourCert);
            java.lang.reflect.Array.set(newSysCerts, 1, ourCert);

            Object sus = ReflectionUtils.callMethod(
                    mSettings, "getSharedUserLPw", Object.class,
                    "android.uid.system", 0, 0, false
            );
            Object sysSigns = ReflectionUtils.callMethod(sus, "getSigningDetails", Object.class);

            ReflectionUtils.setObjectField(sysSigns, "mPastSigningCertificates", newSysCerts);
            log(android.util.Log.ERROR, "AndroTool", "[+] Injection successful, forcing packages.xml flush");

            ReflectionUtils.callMethod(pm, "writeSettings", Void.class, true);
            log(android.util.Log.ERROR, "AndroTool", "[+] Persistence successful, reinstalling...");

        } catch (Throwable e) {
            log(android.util.Log.ERROR, "AndroTool", "[x] Exception in injectSystemSignatures: ", e);
        }
    }
}