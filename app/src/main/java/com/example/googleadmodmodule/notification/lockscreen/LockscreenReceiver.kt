package com.example.googleadmodmodule.notification.lockscreen

import android.Manifest
import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.PowerManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.googleadmodmodule.MainActivity
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.Constant
import java.util.Random

class LockscreenReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        /*0. Start repeating notification if the device was shut down and then reboot*/
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            LockscreenManager.setupLockscreenNotification(context)
        }


        val powerManager = context.getSystemService(PowerManager::class.java)
        val keyguardManager = context.getSystemService(KeyguardManager::class.java)

        if (!powerManager?.isInteractive!! || keyguardManager?.isKeyguardLocked == true) {
            popupLockscreenNotification(context)
        } else {
            popupNormalNotification(context)
        }

        //4. Set again this alarm manager
        LockscreenManager.setupLockscreenNotification(context)
    }

    private fun popupLockscreenNotification(context: Context) {
        //1. Create pending
        val lockscreenIntent = Intent(context, LockscreenActivity::class.java)
        lockscreenIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val lockscreenPendingIntent =
            PendingIntent.getActivity(context, 0, lockscreenIntent, PendingIntent.FLAG_IMMUTABLE)


        //2. Setup notification builder
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, Constant.LOCKSCREEN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_google)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setFullScreenIntent(lockscreenPendingIntent, true)


        //3. Show notification with notificationId which is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(Constant.LOCKSCREEN_NOTIFICATION_ID, builder.build())
    }

    /*send a normal notification instead of lockscreen-styled notification*/
    private fun popupNormalNotification(context: Context) {
        //1. random a content for notification
        val random = Random()
        val maxContent = 4
        val minContent = 0
        val contentId = random.nextInt(maxContent - minContent) + minContent
        val contentNotification: Int = R.string.app_description

        //2.
        //1. Create an explicit intent for an Activity in your app
        val destinationIntent = Intent(context, MainActivity::class.java)
        destinationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context,
            1896,
            destinationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        //3. define notification builder
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, Constant.LOCKSCREEN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_google)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(contentNotification))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(context.getString(contentNotification))
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)


        //4. Show notification with notificationId which is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(Constant.LOCKSCREEN_NOTIFICATION_ID, builder.build())
    }
}