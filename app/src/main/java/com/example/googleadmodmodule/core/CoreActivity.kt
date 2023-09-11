package com.example.googleadmodmodule.core

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
}