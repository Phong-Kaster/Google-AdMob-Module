package com.example.googleadmodmodule.admob

import android.util.Log

object AdLog {

    fun log(tag: String, adType: String, adName: String?, content: String) {
        Log.d(tag, "$adType - $adName $content")
    }
}