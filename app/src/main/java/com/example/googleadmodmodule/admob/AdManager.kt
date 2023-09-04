package com.example.googleadmodmodule.admob

import com.example.googleadmodmodule.R
import com.google.android.gms.ads.AdSize

class AdManager {

    /*NATIVE ADS*/
    val adNativeIntro: AdNative = AdNative(
        adID = AdConstant.ADMOD_NATIVE_ID,
        adLayout = R.layout.ad_native_layout_full_size,
        adName = "Ad Native Intro"
    )

    val adNativeStandard: AdNative = AdNative(
        adID = AdConstant.ADMOD_NATIVE_ID,
        adLayout = R.layout.ad_native_layout_standard,
        adName = "ad Native Standard"
    )

    val adNativeFullSize: AdNative = AdNative(
        adID = AdConstant.ADMOD_NATIVE_ID,
        adLayout = R.layout.ad_native_layout_full_size,
        adName = "ad Native Full Size"
    )


    val adNativeMediumSize: AdNative = AdNative(
        adID = AdConstant.ADMOD_NATIVE_ID,
        adLayout = R.layout.ad_native_layout_medium_size,
        adName = "ad NativeMedium Size"
    )


    /*INTERSTITIAL ADS*/
    val adInterstitialSplash: AdInterstitial = AdInterstitial(
        adID = AdConstant.ADMOD_INTERSTITIAL_ID,
        adName = "Ad Interstitial Splash"
    )


    /*BANNER ADS*/
    val adBanner: AdBanner =
        AdBanner(adID = AdConstant.ADMOD_BANNER_ID, adName = "Ad Banner", adSize = AdSize.BANNER)
    val adFullBanner: AdBanner = AdBanner(
        adID = AdConstant.ADMOD_BANNER_ID,
        adName = "Ad Banner",
        adSize = AdSize.FULL_BANNER
    )
    val adLargeBanner: AdBanner = AdBanner(
        adID = AdConstant.ADMOD_BANNER_ID,
        adName = "Ad Banner",
        adSize = AdSize.LARGE_BANNER
    )

    val adSmartBanner: AdBanner = AdBanner(
        adID = AdConstant.ADMOD_BANNER_ID,
        adName = "Ad Banner",
        adSize = AdSize.SMART_BANNER
    )
    val adLeaderBoard: AdBanner = AdBanner(
        adID = AdConstant.ADMOD_BANNER_ID,
        adName = "Ad Banner",
        adSize = AdSize.LEADERBOARD
    )
    val adMediumRectangle: AdBanner = AdBanner(
        adID = AdConstant.ADMOD_BANNER_ID,
        adName = "Ad Banner",
        adSize = AdSize.MEDIUM_RECTANGLE
    )
}