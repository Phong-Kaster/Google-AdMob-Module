package com.example.googleadmodmodule.core

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
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
}