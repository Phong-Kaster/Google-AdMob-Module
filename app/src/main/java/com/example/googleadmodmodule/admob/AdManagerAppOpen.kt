package com.example.googleadmodmodule.admob

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.googleadmodmodule.MyApplication
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.Date


/**
 * This class is used only for loading and showing app open ads*/
class AdManagerAppOpen {

    private val LOG_TAG = "AppOpenAdManager"

    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false

    /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad.  */
    private var loadTime: Long = 0


    /** Request an ad.  */
    private fun loadAd(context: Context) {
        //1. Do not load ad if there is an unused ad or one is already loading.
        if (isLoadingAd || isAdAvailable()) {
            return
        }


        //2. Start loading ad
        isLoadingAd = true
        val request: AdRequest = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            AdConstant.ADMOD_APP_OPEN_ID,
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    // Called when an app open ad has loaded.
                    Log.d(LOG_TAG, "Ad was loaded.")
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Called when an app open ad has failed to load.
                    Log.d(LOG_TAG, loadAdError.message)
                    isLoadingAd = false
                }
            })
    }


    /**
     * Show the ad if one isn't already showing.
     * @param activity the activity that shows the app open ad
     */
    fun showAdIfAvailable(activity: Activity) {
        showAdIfAvailable(
            activity,
            object : MyApplication.Callback {
                override fun showAd() {
                    // Empty because the user will go back to the activity that shows the ad.
                }
            }
        )
    }


    /** Shows the ad if one isn't already showing. */
    fun showAdIfAvailable(activity: Activity, callback: MyApplication.Callback){
        //1. If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d(LOG_TAG, "The app open ad is already showing.");
            return
        }

        //2. If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable()) {
            Log.d(LOG_TAG, "The app open ad is not ready yet.");
            callback.showAd()
            loadAd(activity)
            return
        }

        //3. Show ad
        Log.d(LOG_TAG, "Will show ad.")

        appOpenAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            /** Called when full screen content is dismissed. */
            override fun onAdDismissedFullScreenContent() {
                // Set the reference to null so isAdAvailable() returns false.
                appOpenAd = null
                isShowingAd = false
                Log.d(LOG_TAG, "onAdDismissedFullScreenContent.")

                callback.showAd()
                loadAd(activity)
            }

            /** Called when fullscreen content failed to show. */
            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isShowingAd = false
                Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.message)

                callback.showAd()
                loadAd(activity)
            }

            /** Called when fullscreen content is shown. */
            override fun onAdShowedFullScreenContent() {
                Log.d(LOG_TAG, "onAdShowedFullScreenContent.")
            }
        }
        isShowingAd = true
        appOpenAd!!.show(activity)
    }


    /** Check if ad exists and can be shown.  */
    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }


    /** Utility method to check if ad was loaded more than n hours ago.  */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference: Long = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }
}