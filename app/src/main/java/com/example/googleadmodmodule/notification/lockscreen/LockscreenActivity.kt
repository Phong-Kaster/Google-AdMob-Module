package com.example.googleadmodmodule.notification.lockscreen

import android.app.KeyguardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.app.NotificationManagerCompat
import com.example.googleadmodmodule.MainActivity
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.Constant
import com.example.googleadmodmodule.core.CoreActivity
import com.example.googleadmodmodule.databinding.ActivityLockscreenBinding
import com.example.googleadmodmodule.utility.UtilityView.makeGone
import com.example.googleadmodmodule.utility.UtilityView.makeVisible
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

class LockscreenActivity : CoreActivity<ActivityLockscreenBinding>(
    layoutRes = R.layout.activity_lockscreen
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lockscreen)
        enableOverrideLockscreen()
        setupView()
        setupEvent()
    }

    private fun setupView() {
        //0. get today
        val date = Date()
        val datetimeTodayPattern = "EEE, MMM dd"


        //1. show current datetime today
        var formatter = SimpleDateFormat(datetimeTodayPattern, Locale.getDefault())
        val datetimeTodayValue = formatter.format(date)
        binding.datetimeToday.setText(datetimeTodayValue)


        //2. show current datetime for fake record
        val datetimeRecordPattern = "MMM dd, HH:ss"
        formatter = SimpleDateFormat(datetimeRecordPattern, Locale.getDefault())
        val datetimeRecordValue = formatter.format(date)
        binding.datetimeRecord.text = datetimeRecordValue


        //3. Remove notification that triggers this activity
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.cancel(Constant.LOCKSCREEN_NOTIFICATION_ID)


        //4. Random select 'layoutRecord' or 'layoutListenRecord' to show on lockscreen
        val random = Random()
        val maxRange = 2
        val minRange = 0
        val layoutValue = random.nextInt(maxRange - minRange) + minRange
        if (layoutValue == 1) {
            binding.layoutRecord.makeVisible()
            binding.layoutListenRecord.makeGone()
        } else {
            binding.layoutRecord.makeGone()
            binding.layoutListenRecord.makeVisible()
        }
    }

    private fun setupEvent() {
        binding.buttonClose.setOnClickListener(View.OnClickListener { finish() })
        binding.gotoHome.setOnClickListener(View.OnClickListener { v: View? ->
            dismissKeyguard()
            openAppForAction(Constant.ACTION_RECORD)
        })
        binding.gotoRecordList.setOnClickListener(View.OnClickListener { v: View? ->
            dismissKeyguard()
            openAppForAction(Constant.ACTION_LISTEN_RECORD)
        })
    }

    private fun enableOverrideLockscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
        } else {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
        }
    }

    private fun dismissKeyguard() {
        val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            keyguardManager.requestDismissKeyguard(this, null)
        }
    }

    private fun openAppForAction(action: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra(Constant.ACTION, action)
        startActivity(intent)
        finish()
    }

}