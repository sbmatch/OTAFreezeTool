//
// Decompiled by Jadx - 648ms
//
package android.miui;

import android.os.Environment;
import android.os.UserHandle;
import android.text.TextUtils;

public class Shell {
 
    private static final String TAG = "Shell";

    public static boolean chmod(String path, int mode) {
        throw new RuntimeException("Stub!");
    }

    public static boolean chmodRecursion(String path, int mode) {
        throw new RuntimeException("Stub!");
    }

    public static boolean chown(String path, int owner, int group) {
        throw new RuntimeException("Stub!");
    }

    public static boolean chownRecursion(String path, int owner, int group) {
        throw new RuntimeException("Stub!");
    }

    public static boolean write(String path, String buffer) {
        throw new RuntimeException("Stub!");
    }

    public static boolean copy(String source, String dest) {
        throw new RuntimeException("Stub!");
    }

    public static boolean link(String oldPath, String newPath) {
        throw new RuntimeException("Stub!");
    }

    public static boolean mkdirs(String path) {
        throw new RuntimeException("Stub!");
    }

    public static boolean move(String oldPath, String newPath) {
        throw new RuntimeException("Stub!");
    }

    public static boolean remove(String path) {
        throw new RuntimeException("Stub!");
    }

    public static boolean run(String command, Object... args) {
        throw new RuntimeException("Stub!");
    }

    public static boolean runShell(String command, Object... args) {
        throw new RuntimeException("Stub!");
    }

    public static final boolean setfilecon(String path, String scontext) {
        throw new RuntimeException("Stub!");
    }

    public static final boolean setProperty(String key, String value) {
        throw new RuntimeException("Stub!");
    }

    public static final boolean writeByteArray(String path, boolean append, byte[] bytes) {
        throw new RuntimeException("Stub!");
    }

    public static final byte[] readByteArray(String path) {
        throw new RuntimeException("Stub!");
    }

    private static String getSystemVisibleExternalPath(String path) {
        throw new RuntimeException("Stub!");
    }
}
