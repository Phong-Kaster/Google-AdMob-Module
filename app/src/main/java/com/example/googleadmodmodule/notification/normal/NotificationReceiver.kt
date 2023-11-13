package com.example.googleadmodmodule.notification.normal

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.googleadmodmodule.MainActivity
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.Constant
import com.example.googleadmodmodule.enumclass.Quote

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        /*0. Start repeating notification if the device was shut down and then reboot*/
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            NotificationManger.setupNotification(context = context)
        }

        NotificationManger.log("content -> Notification Receiver is received !")

        //AppOpenManager.getInstance().disableAppResume()
        /*1.Action show notification*/

        popupNotification(context)

        /*2.Action prepare for next notification*/
        NotificationManger.setupNotification(context = context)
    }


    private fun popupNotification(context: Context) {
        /*0. generate a random number as notification ID*/

        //1. Create an explicit intent for an Activity in your app
        val destinationIntent = Intent(context, MainActivity::class.java)
        destinationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(
            context,
            1896,
            destinationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        //2. set customized view
        val qoute = Quote.generateRandomQoute()
        val contentShort: Int = qoute.short
        val contentFull: Int = qoute.full


        //2.1 map data on notification layout
        val layoutSmall = RemoteViews(context.packageName, R.layout.layout_notification_small)
        layoutSmall.setTextViewText(R.id.title, context.getString(contentShort))
        //layoutSmall.setOnClickPendingIntent(R.id.notificationButton, pendingIntent)

        val layoutBig = RemoteViews(context.packageName, R.layout.layout_notification_big)
        layoutBig.setTextViewText(R.id.title, context.getString(contentFull))
        /*layoutBig.setTextViewText(R.id.content, context.getString(notificationContent))*/


        //3. define notification builder
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, Constant.NORMAL_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.qoute_1_full))
                .setCustomContentView(layoutSmall)
                .setCustomBigContentView(layoutBig)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_swastika)
                .setContentIntent(pendingIntent)


        //4. Show notification with notificationId which is a unique int for each notification that you must define
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(Constant.NORMAL_NOTIFICATION_ID, builder.build())
    }
}