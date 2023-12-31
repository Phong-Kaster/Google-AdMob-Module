package com.example.googleadmodmodule.core

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.gms.ads.MobileAds

open class CoreActivity<dataBinding : ViewDataBinding>
constructor(
    @LayoutRes val layoutRes: Int
) : AppCompatActivity(), CoreInterface {

    open val TAG = "Google Admob Module"
    lateinit var binding: dataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //data binding view
        binding = DataBindingUtil.setContentView(this, layoutRes)

        //Initialize the Google Mobile Ads SDK
        MobileAds.initialize(this) {}
    }


    override fun showToast(content: String) {
        Toast.makeText(this@CoreActivity, content, Toast.LENGTH_SHORT).show()
    }

    override fun isInternetConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm?.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    override fun hideSystemNavigationBar() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                run {
                    /*val windowInsetController = ViewCompat.getWindowInsetsController(window.decorView)*/
                    val windowInsetController = WindowCompat.getInsetsController(window, window.decorView)
                    windowInsetController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    windowInsetController.hide(WindowInsetsCompat.Type.navigationBars())

                    if (window.decorView.rootWindowInsets != null) {
                        window.decorView.rootWindowInsets.getInsetsIgnoringVisibility(
                            WindowInsetsCompat.Type.navigationBars()
                        )
                    }
                    window.setDecorFitsSystemWindows(true)
                }
            } else {
                window.decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}