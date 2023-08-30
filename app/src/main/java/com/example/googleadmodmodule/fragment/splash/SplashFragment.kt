package com.example.googleadmodmodule.fragment.splash

import android.os.Looper
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentSplashBinding


class SplashFragment : CoreFragment<FragmentSplashBinding>(
    R.layout.fragment_splash
) {
    override fun setupData() {

    }

    override fun setupEvent() {
        goToNextScreen()
    }

    private fun goToNextScreen() {
        val runnable = Runnable(function = {
            val destination: NavDirections = SplashFragmentDirections.actionSplashToIntro()
            navigateTo(destination)
        })

        val handler = android.os.Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, 2000)
    }
}