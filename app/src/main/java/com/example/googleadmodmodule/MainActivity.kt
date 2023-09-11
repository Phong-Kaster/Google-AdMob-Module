package com.example.googleadmodmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.googleadmodmodule.core.CoreActivity
import com.example.googleadmodmodule.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : CoreActivity<ActivityMainBinding>(
    layoutRes = R.layout.activity_main
) {
}