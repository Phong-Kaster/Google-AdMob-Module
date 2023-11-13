package com.example.googleadmodmodule.core

object Constant {
    const val APPLICATION_NAME: String = "GoogleAbMobModule"
    const val APPLICATION_DATABASE: String = "application_database"

    // for creating lockscreen-styled notification
    const val LOCKSCREEN_CHANNEL_ID: String = "lockscreen_channel_id"
    const val LOCKSCREEN_NOTIFICATION_ID: Int = 888

    // for creating normal notification
    const val NORMAL_CHANNEL_ID = "normal_channel_id"
    const val NORMAL_NOTIFICATION_ID = 999
    const val NORMAL_TAG = "Notification"

    // for clicking on button in Lockscreen-styled activity
    const val ACTION                     = "action"
    const val ACTION_RECORD              = "record"
    const val ACTION_LISTEN_RECORD       = "listenRecord"
}