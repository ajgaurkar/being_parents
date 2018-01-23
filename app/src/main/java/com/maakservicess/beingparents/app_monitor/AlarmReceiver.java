package com.maakservicess.beingparents.app_monitor;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

/**
 * Created by SAI PC on 9/17/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static int NOTIFICATION_ID = 0;
    private NotificationManager mNotificationManager;
    Bundle bundle;
    DatabaseHandler databaseHandler;
    private Cursor notificationCursor;
    private String notificationHeader = null;
    private String notificationDescription = null;
    SessionManager sessionManager;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Count of on recive   ");
        bundle = intent.getExtras();
        databaseHandler = new DatabaseHandler(context);

        sessionManager = new SessionManager(context);

        Boolean notificationCheck = Boolean.valueOf(sessionManager.getSpecificBabyDetail(SessionManager.KEY_ALLOW_NOTIFICATION_CHECK));

        if (notificationCheck) {
            System.out.println("NOTIFICATION RECEIVED CHECK TRUE");

            Integer notification_ID = bundle.getInt("Id", 0);
            System.out.println("notification_ID--------" + notification_ID);

            notificationCursor = databaseHandler.updateNotification(String.valueOf(System.currentTimeMillis()), 1, String.valueOf(notification_ID));

            System.out.println(" notificationCursor.getCount()--------" + notificationCursor.getCount());

            if (notificationCursor.moveToFirst()) {
                notificationHeader = notificationCursor.getString(notificationCursor.getColumnIndex("notificationHeader"));
                notificationDescription = notificationCursor.getString(notificationCursor.getColumnIndex("notificationText"));
                System.out.println("notificationCursor--------" + notificationCursor);
            } else {
                System.out.println("notificationCursor--------NO DATA");

            }

            sendNotification(context);
        } else {

            System.out.println("NOTIFICATION RECEIVED : BLOCKED BY CHECK FALSE");
        }
    }

    private void sendNotification(Context _context) {
        mNotificationManager = (NotificationManager)
                _context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(_context, 0, new Intent(_context, MainActivity.class), 0);

        //$#$#$# in descriptionstring has to be replaced by baby name with apostrophe s. ex: Ajinkya's

        if (notificationDescription.contains("$#$#$#")) {
            notificationDescription.replace("$#$#$#", sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_NAME) + "'s");
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(_context)
                        .setSmallIcon(R.mipmap.baby_app_icon)
                        .setContentTitle(notificationHeader)
                        .setContentText(notificationDescription)
                        .setSound(alarmSound)
                        //also try this
//        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                        .setVibrate(new long[]{500, 500});

        mBuilder.setContentIntent(contentIntent);
        NOTIFICATION_ID = NOTIFICATION_ID + 1;
        System.out.println("NOTIFICATION_ID++    " + NOTIFICATION_ID);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

}
