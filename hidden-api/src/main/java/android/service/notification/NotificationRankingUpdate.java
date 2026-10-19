package android.service.notification;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NotificationRankingUpdate implements Parcelable {
    protected NotificationRankingUpdate(Parcel in) {
    }

    public static final Creator<NotificationRankingUpdate> CREATOR = new Creator<NotificationRankingUpdate>() {
        @Override
        public NotificationRankingUpdate createFromParcel(Parcel in) {
            return new NotificationRankingUpdate(in);
        }

        @Override
        public NotificationRankingUpdate[] newArray(int size) {
            return new NotificationRankingUpdate[size];
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
