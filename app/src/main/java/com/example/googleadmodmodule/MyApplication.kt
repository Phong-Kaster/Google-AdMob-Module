package com.example.googleadmodmodule

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.googleadmodmodule.admob.AdConstant
import com.example.googleadmodmodule.admob.AdManager
import com.example.googleadmodmodule.utility.UtilityOverall
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import java.util.Date


/**
 * Application class that initializes, loads
 * and show ads when activities change states. */
private val TAG = "GoogleAdMobModule"

class MyApplication : Application(), Application.ActivityLifecycleCallbacks, LifecycleObserver  {


    private lateinit var appOpenAdManager: AppOpenAdManager
    private var currentActivity: Activity? = null

    companion object{
        lateinit var adManager: AdManager

        fun showAdOpenApp(activity: Activity, application: MyApplication?){
            /*val application = application as? MyApplication
*/

            // If the application is not an instance of MyApplication, log an error message and
            // start the MainActivity without showing the app open ad.
            if (application == null) {
                Log.e(TAG, "Failed to cast application to MyApplication.")
                return
            }

            // Show the app open ad.
            application.showAdIfAvailable(
                activity,
                object : Callback {
                    override fun showAd() {

                    }
                })
        }
    }


    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        instantiateGoogleAdsSDK()
        setupComponent()
    }

    private fun instantiateGoogleAdsSDK() {
        if (!UtilityOverall.isInternetConnected(this)) {
            return
        }

        Log.d(TAG, "MobileAds initialize !")
        MobileAds.initialize(
            this
        ) {

        }
    }

    private fun setupComponent() {
        //1. In order to be notified of app foregrounding events
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        //2. define appOpenAd Manager
        appOpenAdManager = AppOpenAdManager()

        //3. Ad Manager
        adManager = AdManager()
    }

    /** -------------------------------------Lifecycle methods------------------------------------- */
    /** LifecycleObserver method that shows the app open ad when the app moves to foreground. */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        // Show the ad (if available) when the app moves to foreground.
        currentActivity?.let { appOpenAdManager.showAdIfAvailable(it) }
    }

    /**
     * Shows an app open ad.
     *
     * @param activity the activity that shows the app open ad
     * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
     */
    fun showAdIfAvailable(activity: Activity, callback: Callback) {
        // We wrap the showAdIfAvailable to enforce that other classes only interact with MyApplication
        // class.
        appOpenAdManager.showAdIfAvailable(activity, callback)
    }

    /** -------------------------------------ActivityLifecycleCallback methods------------------------------------- */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated !")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "onActivityStarted !")
        // Updating the currentActivity only when an ad is not showing.
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(TAG, "onActivityResumed !")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d(TAG, "onActivityPaused !")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d(TAG, "onActivityStopped !")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d(TAG, "onActivitySaveInstanceState !")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d(TAG, "onActivityDestroyed !")
    }

    /*****************************************************************************************/
    /*************************************AppOpenAdManager**********************************/
    /*****************************************************************************************/
    /** Inner class that loads and shows app open ads. */
    private class AppOpenAdManager {

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
                object : AppOpenAdLoadCallback() {
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
                object : Callback {
                    override fun showAd() {
                        // Empty because the user will go back to the activity that shows the ad.
                    }
                }
            )
        }


        /** Shows the ad if one isn't already showing. */
        fun showAdIfAvailable(activity: Activity, callback: Callback){
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

    /** Interface definition for a callback to be invoked when an app open ad is complete. */
    interface Callback {
        fun showAd()
    }
}