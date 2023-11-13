package com.example.googleadmodmodule.notification.normal

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.googleadmodmodule.MainActivity
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.Constant
import com.example.googleadmodmodule.notification.lockscreen.LockscreenManager

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        /*0. Start repeating notification if the device was shut down and then reboot*/
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            NotificationManger.setupNotification(context = context)
            LockscreenManager.setupLockscreenNotification(context = context)
        }
        //AppOpenManager.getInstance().disableAppResume()
        /*1.Action show notification*/
        popupNotification(context, intent)

        /*2.Action prepare for next notification*/
        prepareNextNotification(context, intent)

        Log.d("Notification", "NotificationReceiver - onReceive")
    }


    private fun popupNotification(context: Context, intent: Intent) {
        /*0. generate a random number as notification ID*/
        val notificationId: Int = Constant.NORMAL_NOTIFICATION_ID

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
        val notificationTitle: Int = R.string.app_name
        val notificationContent1: Int = R.string.tri_tue_con_nguoi_hinh_thanh_trong_hoc_tap
        val notificationContent2: Int = R.string.nhan_cach_con_nguoi_hinh_thanh_trong_bao_to


        //2.1 map data on notification layout
        val layoutSmall = RemoteViews(context.packageName, R.layout.layout_notification_small)
        layoutSmall.setTextViewText(R.id.title, context.getString(notificationTitle))
        //layoutSmall.setOnClickPendingIntent(R.id.notificationButton, pendingIntent)

        val layoutBig = RemoteViews(context.packageName, R.layout.layout_notification_big)
        layoutBig.setTextViewText(R.id.title, context.getString(notificationContent1))
        layoutBig.setTextViewText(R.id.content, context.getString(notificationContent2))


        //2.2 define notification builder

        //3. define notification builder
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, Constant.NORMAL_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_google)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.app_description))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(context.getString(notificationContent1))
                )
                .setCustomContentView(layoutBig)
                .setCustomBigContentView(layoutBig)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)


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
        notificationManager.notify(notificationId, builder.build())
    }


    private fun prepareNextNotification(context: Context, intent: Intent) {
        NotificationManger.setupNotification(context = context)
    }
}