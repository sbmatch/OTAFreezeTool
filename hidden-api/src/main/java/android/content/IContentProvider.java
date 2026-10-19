package android.content;

import android.app.IActivityManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;

public interface IContentProvider extends IInterface {

    Cursor query(AttributionSource attributionSource, Uri url,
                 String[] projection,
                 Bundle queryArgs, ICancellationSignal cancellationSignal);

    Bundle call(AttributionSource attributionSource, String authority,
                String method, String arg, Bundle extras);

    Uri insert(AttributionSource attributionSource, Uri url,
               ContentValues initialValues, Bundle extras);

    int update(AttributionSource attributionSource, Uri url, ContentValues values,
               Bundle extras);

    int checkUriPermission(AttributionSource attributionSource, Uri uri,
                           int uid, int modeFlags);

    abstract class Stub extends Binder implements IContentProvider {

        public static IContentProvider asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }

    int QUERY_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION;
    int GET_TYPE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 1;
    int INSERT_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 2;
    int DELETE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 3;
    int UPDATE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 9;
    int BULK_INSERT_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 12;
    int OPEN_FILE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 13;
    int OPEN_ASSET_FILE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 14;
    int APPLY_BATCH_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 19;
    int CALL_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 20;
    int GET_STREAM_TYPES_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 21;
    int OPEN_TYPED_ASSET_FILE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 22;
    int CREATE_CANCELATION_SIGNAL_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 23;
    int CANONICALIZE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 24;
    int UNCANONICALIZE_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 25;
    int REFRESH_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 26;
    int CHECK_URI_PERMISSION_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 27;
    int GET_TYPE_ASYNC_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 28;
    int CANONICALIZE_ASYNC_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 29;
    int UNCANONICALIZE_ASYNC_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 30;
    int GET_TYPE_ANONYMOUS_ASYNC_TRANSACTION = IBinder.FIRST_CALL_TRANSACTION + 31;
}
