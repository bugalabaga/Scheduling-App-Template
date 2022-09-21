package com.example.apptemplate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MeetingNotification extends BroadcastReceiver {

    SharedPreferences sharedPreferences;
    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("Meeting Information",Context.MODE_PRIVATE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "meetingNotify");
        builder.setSmallIcon(R.mipmap.avatar2).setContentTitle("Tutoring")
                .setContentText( sharedPreferences.getString("SUBJECT"," ") +" tutoring with "
                + sharedPreferences.getString("NAME","")
                + " in "
                + sharedPreferences.getInt("NOTIF MARGIN", 10) +
                        " minutes.").setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(0,builder.build());
    }
}
