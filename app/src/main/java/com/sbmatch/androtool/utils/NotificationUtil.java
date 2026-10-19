package com.sbmatch.androtool.utils;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

public class NotificationUtil {

    private NotificationUtil() {}

    public static void createNotificationChannel(NotificationChannel channel) {
        NotificationManager nm = (NotificationManager) AppGlobals.getInitialApplication().getSystemService("notification");
        nm.createNotificationChannel(channel);
    }

    /**
     * @param channelId 渠道 ID
     * @param id 通知 ID，同一 ID 会覆盖旧通知
     * @param title 标题
     * @param content 内容
     */
    public static void sendNotification(String channelId, int id,
            String title, String content) {
        sendNotification(channelId, id, title, content, null);
    }

    /**
     * 发送通知（带点击跳转）。
     *
     * @param intent 点击通知后跳转的 Intent，可为 null
     */
    public static void sendNotification(String channelId, int id,
            String title, String content,
            Intent intent) {
        sendNotification(channelId, id, title, content, intent, 0);
    }

    /**
     * 发送通知
     *
     * @param intent 点击通知跳转的 Intent，可为 null
     * @param iconRes 小图标资源 id，传 0 用默认
     */
    public static void sendNotification(String channelId, int id,
            String title, String content,
            Intent intent, int iconRes) {

        Context ctx = AppGlobals.getInitialApplication();
        NotificationManager nm = (NotificationManager) ctx.getSystemService("notification");

        if (iconRes == 0) {
            iconRes = ctx.getApplicationInfo().icon;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content)
                        .setBigContentTitle(title))
        ;

        if (intent != null) {
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                flags |= PendingIntent.FLAG_IMMUTABLE;
            }
            PendingIntent pi = PendingIntent.getActivity(ctx, id, intent, flags);
            builder.setContentIntent(pi);
        }

        nm.notify(id, builder.build());
    }

}