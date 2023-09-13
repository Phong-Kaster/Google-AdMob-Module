package com.example.googleadmodmodule.notification.normal

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

object NotificationManger {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun isPermissionAccessed(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    }
}