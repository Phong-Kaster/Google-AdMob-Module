package com.example.googleadmodmodule.notification.lockscreen

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.Constant
import java.util.Calendar

/**
 * 1. What is lockscreen-styled notification?
 * Lockscreen-styled is notification that override device's lockscreen. They maybe have layout that
 * look like as device's lockscreen or any layout that we arrange by XML layout or Jetpack Compose.
 * With this module, XML layout is selected to represent appearance for the notifications
 *
 * 2. Lockscreen Manager stores all functions that operates
 * lockscreen-styled notification: set alarm manager, set notification builder, etc,.....
 */
object LockscreenManager {
    fun createLockscreenChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //1. define variable
            val name: CharSequence = context.getString(R.string.app_name)
            val description = context.getString(R.string.lockscreen_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constant.LOCKSCREEN_CHANNEL_ID, name, importance)
            channel.description = description


            //2. Register the channel with the system; you can't change the importance or other notification behaviors after this
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun setupLockscreenNotification(context: Context) {
        //1. alarm manager sends a lockscreen-styled notification at 4 AM everyday
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmTime = Calendar.getInstance()
        val now = Calendar.getInstance()


        //2. fire daily lockscreen notification after 5 seconds
        alarmTime.timeInMillis = System.currentTimeMillis()
        alarmTime[Calendar.HOUR] = 8
        alarmTime[Calendar.MINUTE] = 0
        alarmTime[Calendar.SECOND] = 5
        if (now.after(alarmTime)) {
            alarmTime.add(Calendar.DATE, 1)
        }


        //Final. set up notification at specific time
        val intent = Intent(context, LockscreenReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_IMMUTABLE)
        try {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.timeInMillis, pendingIntent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}