package com.example.googleadmodmodule.admob

import android.content.Context
import android.view.ViewGroup
import com.example.googleadmodmodule.utility.UtilityView.makeGone
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class AdBanner
constructor(
    val adID: String = AdConstant.ADMOD_BANNER_ID,
    val adSize: AdSize = AdSize.BANNER,
    val adName: String? = "Banner Ad"
) {
    private val TAG = AdConstant.ADMOD_TAG
    private lateinit var adView: AdView
    private val adRequest: AdRequest = AdRequest.Builder().build()

    fun loadAndShowAd(context: Context, adContainer: ViewGroup) {
        //1. Create AdView programmatically
        adView = AdView(context)
        adView.setAdSize(adSize)
        adView.adUnitId = adID


        //2. Load Ad
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_BANNER,
                    adName = adName,
                    "onAdClicked"
                )
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_BANNER,
                    adName = adName,
                    "onAdClosed"
                )
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_BANNER,
                    adName = adName,
                    "onAdFailedToLoad"
                )
                adContainer.removeAllViews()
                adContainer.makeGone()
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_BANNER,
                    adName = adName,
                    "onAdImpression"
                )
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_BANNER,
                    adName = adName,
                    "onAdLoaded"
                )
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_BANNER,
                    adName = adName,
                    "onAdOpened"
                )
            }
        }

        //3. Show Ad
        adContainer.removeAllViews()
        adContainer.addView(adView)

    }

    interface Callback {
        fun onAdClicked()
        fun onAdClosed()
        fun onAdFailedToLoad()
        fun onAdImpression()
        fun onAdLoaded()
        fun onAdOpened()
    }
}