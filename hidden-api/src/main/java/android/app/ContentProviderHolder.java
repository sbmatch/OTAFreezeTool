package android.app;

import android.content.IContentProvider;
import android.content.pm.ProviderInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ContentProviderHolder implements Parcelable {
    public IContentProvider provider;
    public IBinder connection;

    public boolean noReleaseNeeded;
    /**
     * Whether the provider here is a local provider or not.
     */
    public boolean mLocal;
    protected ContentProviderHolder(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    public ContentProviderHolder(ProviderInfo _info) {
        throw new RuntimeException("Stub!");
    }

    public static final Creator<ContentProviderHolder> CREATOR = new Creator<ContentProviderHolder>() {
        @Override
        public ContentProviderHolder createFromParcel(Parcel in) {
            return new ContentProviderHolder(in);
        }

        @Override
        public ContentProviderHolder[] newArray(int size) {
            return new ContentProviderHolder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }
}
