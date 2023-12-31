package com.example.googleadmodmodule.ui.fragment.splash

import androidx.navigation.NavDirections
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.admob.AdInterstitial
import com.example.googleadmodmodule.admob.AdManagerAppOpen
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : CoreFragment<FragmentSplashBinding>(
    R.layout.fragment_splash
) {
    override fun setupData() {
        MyApplication.adManager.adNativeIntro.loadAd(requireActivity())
    }

    override fun setupEvent() {
        super.setupEvent()
        showAdIfReady()
    }

    override fun onResume() {
        super.onResume()
        AdManagerAppOpen.getInstance().disableAppOpenAd()
    }


    private fun showAdIfReady() {
        // device has internet connection when show an interstitial advertisement
        if (isInternetConnected() == true) {
            MyApplication
                .adManager
                .adInterstitialSplash
                .loadAndShowAd(
                    activity = requireActivity(),
                    callback = object : AdInterstitial.Callback {
                        override fun onAdClicked() {
                            //TODO("Not yet implemented")
                        }

                        override fun onAdDismissedFullScreenContent() {
                            goToNextScreen()
                        }

                        override fun onAdFailedToShowFullScreenContent() {
                            goToNextScreen()
                        }

                        override fun onAdImpression() {
                            goToNextScreen()
                        }

                        override fun onAdShowedFullScreenContent() {
                            goToNextScreen()
                        }

                        override fun onAdFailedToLoad() {
                            goToNextScreen()
                        }
                    })
        } else { // device does not has internet connection then go ahead
            goToNextScreen()
        }
    }

    private fun goToNextScreen() {
        val destination: NavDirections = if (sharedPreference.isIntroShown) {
            SplashFragmentDirections.actionSplashToIntro()
        } else {
            SplashFragmentDirections.actionSplashToHome()
        }
        navigateTo(destination)
    }
}