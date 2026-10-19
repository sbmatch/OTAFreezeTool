package android.app;

import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.service.notification.INotificationListener;

public interface INotificationManager extends IInterface {

    void setNotificationsEnabledWithImportanceLockForPackage(String pkg, int uid, boolean enabled);
    void setNotificationsEnabledForPackage(String pkg, int uid, boolean enabled);
    boolean areNotificationsEnabled(String pkg);
    boolean isImportanceLocked(String pkg, int uid);
    int getPackageImportance(String pkg);
    void requestNotificationPermission(String pkg);
    ParceledListSlice<NotificationChannelGroup> getNotificationChannelGroups(String pkg);
    ParceledListSlice<NotificationChannel> getNotificationChannels(String callingPkg, String targetPkg, int userId);
    NotificationChannel getNotificationChannelForPackage(String pkg, int uid, String channelId, String conversationId, boolean includeDeleted);
    void registerListener(INotificationListener listener, ComponentName component, int userid);
    NotificationHistory getNotificationHistory(String callingPkg, String callingAttributionTag);
    ParceledListSlice<NotificationChannel> getNotificationChannelsForPackage(String pkg, int uid, boolean includeDeleted);
    void updateNotificationChannelGroupForPackage(String pkg, int uid, NotificationChannelGroup group);
    void updateNotificationChannelForPackage(String pkg, int uid, NotificationChannel channel);
    void unlockNotificationChannel(String pkg, int uid, String channelId);

    void enqueueNotificationWithTag(String pkg, String opPkg, String tag, int id, Notification notification, int userId);

    void deleteNotificationChannel(String pkg, String channelId);

    abstract class Stub extends Binder implements INotificationManager {

        public static INotificationManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
