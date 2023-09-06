package com.example.googleadmodmodule.admob

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentContainerView
import com.example.googleadmodmodule.MainActivity
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.utility.UtilityView.makeGone
import com.example.googleadmodmodule.utility.UtilityView.makeVisible
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.Date


/**
 * This class is used only for loading and showing app open ads*/
class AdManagerAppOpen {

    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false

    var canAppOpenAdShown: Boolean = true

    /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad.  */
    private var loadTime: Long = 0

    companion object{

        @Volatile
        private var INSTANCE: AdManagerAppOpen? = null
        @Synchronized
        fun getInstance(): AdManagerAppOpen {
            if (INSTANCE == null) {
                INSTANCE = AdManagerAppOpen()
            }
            return INSTANCE as AdManagerAppOpen
        }
    }




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
                    AdLog.log(
                        tag = AdConstant.ADMOD_TAG,
                        adType = AdConstant.ADMOD_TYPE_APP_OPEN,
                        adName = "App open ad",
                        content = "Ad was loaded."
                    )
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Called when an app open ad has failed to load.
                    AdLog.log(
                        tag = AdConstant.ADMOD_TAG,
                        adType = AdConstant.ADMOD_TYPE_APP_OPEN,
                        adName = "App open ad",
                        content = loadAdError.message
                    )
                    isLoadingAd = false
                }
            })
    }


    fun showAd(activity: Activity, application: MyApplication?){
        // If the application is not an instance of MyApplication, log an error message and
        // start the MainActivity without showing the app open ad.
        if (application == null) {
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_APP_OPEN,
                adName = "App open ad",
                content = "Failed to cast application to MyApplication."
            )
            return
        }

        // Show the app open ad.
        /*application.showAdIfAvailable(
            activity,
            object : MyApplication.Callback {
                override fun showAd() {

                }
            })*/

        showAdIfAvailable(activity)
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
    private fun showAdIfAvailable(activity: Activity, callback: MyApplication.Callback){
        //1. If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_APP_OPEN,
                adName = "App open ad",
                content = "The app open ad is already showing."
            )
            return
        }

        //2. If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable()) {
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_APP_OPEN,
                adName = "App open ad",
                content = "The app open ad is not ready yet."
            )
            callback.showAd()
            loadAd(activity)
            return
        }

        //3. Show ad
        AdLog.log(
            tag = AdConstant.ADMOD_TAG,
            adType = AdConstant.ADMOD_TYPE_APP_OPEN,
            adName = "App open ad",
            content = "Will show app open ad."
        )

        appOpenAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            /** Called when full screen content is dismissed. */
            override fun onAdDismissedFullScreenContent() {
                // Set the reference to null so isAdAvailable() returns false.
                appOpenAd = null
                isShowingAd = false
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_APP_OPEN,
                    adName = "App open ad",
                    content = "onAdDismissedFullScreenContent."
                )
                callback.showAd()
                loadAd(activity)
                (activity as MainActivity).findViewById<FragmentContainerView>(R.id.navHostMain).makeVisible()
            }

            /** Called when fullscreen content failed to show. */
            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isShowingAd = false
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_APP_OPEN,
                    adName = "App open ad",
                    content = "onAdFailedToShowFullScreenContent: " + adError.message
                )
                callback.showAd()
                loadAd(activity)
                (activity as MainActivity).findViewById<FragmentContainerView>(R.id.navHostMain).makeVisible()
            }

            /** Called when fullscreen content is shown. */
            override fun onAdShowedFullScreenContent() {
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_APP_OPEN,
                    adName = "App open ad",
                    content = "onAdShowedFullScreenContent."
                )
                (activity as MainActivity).findViewById<FragmentContainerView>(R.id.navHostMain).makeGone()
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

    fun enableAppOpenAd(){
        this.canAppOpenAdShown = true
    }

    fun disableAppOpenAd(){
        this.canAppOpenAdShown = false
    }
}