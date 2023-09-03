package com.example.googleadmodmodule.admob

import android.app.Activity
import android.util.Log
import com.example.googleadmodmodule.utility.UtilityOverall
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
                    Log.d(TAG, "ad Interstitial - name '${adName}' is ready for showing !")
                    adInterstitial = interstitialAd

                    // The adInterstitial is ready for being shown
                    showAd(activity, callback)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d(TAG, "ad Interstitial - name '${adName}' has error: ${loadAdError.message}")
                    adInterstitial = null
                }
            })
    }

    fun showAd(activity: Activity, callback: Callback) {
        Log.d(TAG, "showAd is running !")
        adInterstitial?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
                callback.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                adInterstitial = null
                callback.onAdDismissedFullScreenContent()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                adInterstitial = null
                callback.onAdFailedToShowFullScreenContent()
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
                callback.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
                callback.onAdShowedFullScreenContent()
            }
        }

        if (adInterstitial != null) {
            adInterstitial?.show(activity)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    /*This way helps us handle easily for each fragment/ activity*/
    interface Callback {
        fun onAdClicked()
        fun onAdDismissedFullScreenContent()
        fun onAdFailedToShowFullScreenContent()
        fun onAdImpression()
        fun onAdShowedFullScreenContent()
    }
}