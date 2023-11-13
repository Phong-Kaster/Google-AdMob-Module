package com.example.googleadmodmodule.notification.normal

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.Constant
import java.util.Calendar

object NotificationManger {

    fun log(content: String) = Log.d(Constant.NORMAL_TAG, "content:  $content")

    /**
     * Note: Create the NotificationChannel, but only on API 26+ because the NotificationChannel
     * class is new and not in the support library
     */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //1. define variable
            val name: CharSequence = context.getString(R.string.app_name)
            val description = context.getString(R.string.notification_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constant.NORMAL_CHANNEL_ID, name, importance)
            channel.description = description


            //2. Register the channel with the system; you can't change the importance or other notification behaviors after this
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    fun isNotificationAccessed(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    }

    fun setupNotification(context: Context) {
        log("--------------------------------")
        log("--> setup daily notification !")
        //1. define current time
        val now = Calendar.getInstance()
        val currentHour = now[Calendar.HOUR_OF_DAY]
        val currentMinute = now[Calendar.MINUTE]
        val month = now[Calendar.MONTH] + 1
        val date = now[Calendar.DATE]
        val year = now[Calendar.YEAR]


        //2. find time of next notification
        val hour = if (currentHour < 9) 9 else 15



        // 3. fire notification at specific time
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmTime = Calendar.getInstance()
        alarmTime.timeInMillis = System.currentTimeMillis()
        alarmTime[Calendar.HOUR_OF_DAY] = hour
        alarmTime[Calendar.MINUTE] = 1
        alarmTime[Calendar.SECOND] = 0
        if (now.after(alarmTime)) {
            alarmTime.add(Calendar.DATE, 1)
        }

        log("--> now is $date/$month/$year $currentHour:$currentMinute")
        log("--> next notification at $hour:1")

        //Final. set up notification at specific time
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.timeInMillis, pendingIntent)
    }
}