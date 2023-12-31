package com.example.googleadmodmodule.admob

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class AdInterstitial
constructor(
    val adID: String = AdConstant.ADMOD_NATIVE_ID,
    val adName: String? = "Interstitial Ad"
) {
    private val TAG = AdConstant.ADMOD_TAG
    private var adRequest: AdRequest = AdRequest.Builder().build()
    private var adInterstitial: InterstitialAd? = null


    fun loadAndShowAd(activity: Activity, callback: Callback) {
        InterstitialAd.load(
            activity,
            adID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The adInterstitial reference will be null until an ad is loaded.
                    AdLog.log(
                        tag = AdConstant.ADMOD_TAG,
                        adType = AdConstant.ADMOD_TYPE_INTERSTITIAL,
                        adName = adName,
                        content = "is ready for showing"
                    )
                    adInterstitial = interstitialAd

                    // The adInterstitial is ready for being shown
                    showAd(activity, callback)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    AdLog.log(
                        tag = AdConstant.ADMOD_TAG,
                        adType = AdConstant.ADMOD_TYPE_INTERSTITIAL,
                        adName = adName,
                        content = "has error: ${loadAdError.message}\""
                    )
                    adInterstitial = null
                    callback.onAdFailedToLoad()
                }
            })
    }

    fun showAd(activity: Activity, callback: Callback) {
        adInterstitial?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_INTERSTITIAL,
                    adName = adName,
                    content = "Ad was clicked."
                )
                callback.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_INTERSTITIAL,
                    adName = adName,
                    content = "onAdDismissedFullScreenContent."
                )
                adInterstitial = null
                callback.onAdDismissedFullScreenContent()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_INTERSTITIAL,
                    adName = adName,
                    content = "failed to show fullscreen content.."
                )
                adInterstitial = null
                callback.onAdFailedToShowFullScreenContent()
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_INTERSTITIAL,
                    adName = adName,
                    content = "recorded an impression."
                )
                callback.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_INTERSTITIAL,
                    adName = adName,
                    content = "showed fullscreen content."
                )
                callback.onAdShowedFullScreenContent()
            }
        }

        if (adInterstitial != null) {
            adInterstitial?.show(activity)
        } else {
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_INTERSTITIAL,
                adName = adName,
                content = "ad wasn't ready yet."
            )
        }
    }

    /*This way helps us handle easily for each fragment/ activity*/
    interface Callback {
        fun onAdClicked()
        fun onAdDismissedFullScreenContent()
        fun onAdFailedToShowFullScreenContent()
        fun onAdImpression()
        fun onAdShowedFullScreenContent()
        fun onAdFailedToLoad()
    }
}