package android.app;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class NotificationHistory implements Parcelable {

    public NotificationHistory(){
        throw new RuntimeException("Stub!");
    }

    protected NotificationHistory(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    public boolean hasNextNotification() {
        throw new RuntimeException("Stub!");
    }

    public HistoricalNotification getNextNotification() {
        throw new RuntimeException("Stub!");
    }

    public List<HistoricalNotification> getNotificationsToWrite() {
        throw new RuntimeException("Stub!");
    }

    public boolean removeChannelFromWrite(String packageName, String channelId) {
        throw new RuntimeException("Stub!");
    }

    public static final Creator<NotificationHistory> CREATOR = new Creator<NotificationHistory>() {
        @Override
        public NotificationHistory createFromParcel(Parcel in) {
            return new NotificationHistory(in);
        }

        @Override
        public NotificationHistory[] newArray(int size) {
            return new NotificationHistory[size];
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

    public final class HistoricalNotification {
        private String mChannelId;
        private String mChannelName;
        private String mConversationId;
        private Icon mIcon;
        private String mPackage;
        private long mPostedTimeMs;
        private String mText;
        private String mTitle;
        private int mUid;
        private int mUserId;

        private HistoricalNotification() {
        }
    }

}
