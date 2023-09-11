package com.example.googleadmodmodule.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.core.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UtilitySharedPreference
@Inject
constructor(@ApplicationContext context: Context) {

    private var sharedPreference: SharedPreferences

    companion object {
        const val MY_MESSAGE = "my_message"
        const val IS_LANGUAGE_SELECTED_IN_THE_FIRST_TIME = "is_language_selected_in_the_first_time"
        const val IS_INTRO_SHOWN = "is_intro_shown"
    }

    init {
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    }

    var myMessage: String
        get() = sharedPreference.getString(MY_MESSAGE, "") ?: "This is default message"
        set(value) {
            sharedPreference.edit()?.putString(MY_MESSAGE, value)?.apply()
        }

    var isLanguageSelectedInTheFirstTime: Boolean
        get() = sharedPreference.getBoolean(IS_LANGUAGE_SELECTED_IN_THE_FIRST_TIME, true)
        set(value) {
            sharedPreference.edit()?.putBoolean(IS_LANGUAGE_SELECTED_IN_THE_FIRST_TIME, value)
                ?.apply()
        }

    var isIntroShown: Boolean
        get() = sharedPreference.getBoolean(IS_INTRO_SHOWN, true)
        set(value) {
            sharedPreference.edit()?.putBoolean(IS_INTRO_SHOWN, value)
                ?.apply()
        }
}