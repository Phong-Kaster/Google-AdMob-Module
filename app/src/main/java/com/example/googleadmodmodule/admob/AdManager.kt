package com.example.googleadmodmodule.admob

import com.example.googleadmodmodule.R

class AdManager {

    val adNativeIntro: AdNative = AdNative(
        adID = AdConstant.ADMOD_NATIVE_ID,
        adLayout = R.layout.ad_native_layout_full_size,
        adName = "Ad Native Intro"
    )

    val adInterstitialSplash: AdInterstitial = AdInterstitial(
        adID = AdConstant.ADMOD_INTERSTITIAL_ID,
        adName = "Ad Interstitial Splash"
    )

    val adBanner: AdBanner = AdBanner(
        adID = AdConstant.ADMOD_BANNER_ID,
        adName = "Ad Banner"
    )
}