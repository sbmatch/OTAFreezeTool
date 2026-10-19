package android.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Slog {

    private Slog() {
    }

    /**
     * Logs {@code msg} at {@link Log#VERBOSE} level.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     *
     * @see Log#v(String, String)
     */
   
    public static int v(@Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#VERBOSE} level, attaching stack trace of the {@code tr} to
     * the end of the log statement.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     * @param tr an exception to log.
     *
     * @see Log#v(String, String, Throwable)
     */
    public static int v(@Nullable String tag, @NonNull String msg, @Nullable Throwable tr) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#DEBUG} level.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     *
     * @see Log#d(String, String)
     */
    
    public static int d(@Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#DEBUG} level, attaching stack trace of the {@code tr} to
     * the end of the log statement.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     * @param tr an exception to log.
     *
     * @see Log#d(String, String, Throwable)
     */
    
    public static int d(@Nullable String tag, @NonNull String msg, @Nullable Throwable tr) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#INFO} level.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     *
     * @see Log#i(String, String)
     */
    
    public static int i(@Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#INFO} level, attaching stack trace of the {@code tr} to
     * the end of the log statement.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     * @param tr an exception to log.
     *
     * @see Log#i(String, String, Throwable)
     */
    public static int i(@Nullable String tag, @NonNull String msg, @Nullable Throwable tr) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#WARN} level.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     *
     * @see Log#w(String, String)
     */
    
    public static int w(@Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#WARN} level, attaching stack trace of the {@code tr} to
     * the end of the log statement.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     * @param tr an exception to log.
     *
     * @see Log#w(String, String, Throwable)
     */
    
    public static int w(@Nullable String tag, @NonNull String msg, @Nullable Throwable tr) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs stack trace of {@code tr} at {@link Log#WARN} level.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param tr an exception to log.
     *
     * @see Log#w(String, Throwable)
     */
    public static int w(@Nullable String tag, @Nullable Throwable tr) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#ERROR} level.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     *
     * @see Log#e(String, String)
     */
    
    public static int e(@Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs {@code msg} at {@link Log#ERROR} level, attaching stack trace of the {@code tr} to
     * the end of the log statement.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     * @param tr an exception to log.
     *
     * @see Log#e(String, String, Throwable)
     */
    
    public static int e(@Nullable String tag, @NonNull String msg, @Nullable Throwable tr) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs a condition that should never happen.
     *
     * <p>
     * Similar to {@link Log#wtf(String, String)}, but will never cause the caller to crash, and
     * will always be handled asynchronously. Primarily to be used by the system server.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     *
     * @see Log#wtf(String, String)
     */

    public static int wtf(@Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Similar to {@link #wtf(String, String)}, but does not output anything to the log.
     */
    public static void wtfQuiet(@Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs a condition that should never happen, attaching the full call stack to the log.
     *
     * <p>
     * Similar to {@link Log#wtfStack(String, String)}, but will never cause the caller to crash,
     * and will always be handled asynchronously. Primarily to be used by the system server.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     *
     * @see Log#wtfStack(String, String)
     */

    public static int wtfStack(@Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs a condition that should never happen, attaching stack trace of the {@code tr} to the
     * end of the log statement.
     *
     * <p>
     * Similar to {@link Log#wtf(String, Throwable)}, but will never cause the caller to crash,
     * and will always be handled asynchronously. Primarily to be used by the system server.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param tr an exception to log.
     *
     * @see Log#wtf(String, Throwable)
     */
    public static int wtf(@Nullable String tag, @Nullable Throwable tr) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Logs a condition that should never happen, attaching stack trace of the {@code tr} to the
     * end of the log statement.
     *
     * <p>
     * Similar to {@link Log#wtf(String, String, Throwable)}, but will never cause the caller to
     * crash, and will always be handled asynchronously. Primarily to be used by the system server.
     *
     * @param tag identifies the source of a log message.  It usually represents system service,
     *            e.g. {@code PackageManager}.
     * @param msg the message to log.
     * @param tr an exception to log.
     *
     * @see Log#wtf(String, String, Throwable)
     */
    
    public static int wtf(@Nullable String tag, @NonNull String msg, @Nullable Throwable tr) {
        throw new RuntimeException("Stub!");
    }


    public static int println(int priority, @Nullable String tag, @NonNull String msg) {
        throw new RuntimeException("Stub!");
    }
}
