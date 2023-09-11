package com.example.googleadmodmodule.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.core.Constant
import javax.inject.Inject
import javax.inject.Singleton

class UtilitySharedPreference
constructor() {

    /*private var sharedPreference: SharedPreferences*/

    companion object {
        const val MY_MESSAGE = "my_message"
    }

    init {
        /*sharedPreference = PreferenceManager.getDefaultSharedPreferences(myApplication)*/
    }

    /*var myMessage: String?
        get() = sharedPreference.getString(MY_MESSAGE, "")
        set(value) {
            sharedPreference.edit()?.putString(MY_MESSAGE, value)?.apply()
        }*/
}