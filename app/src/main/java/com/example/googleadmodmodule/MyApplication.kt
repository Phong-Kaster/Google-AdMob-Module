package com.example.googleadmodmodule

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.googleadmodmodule.admob.AdManager
import com.example.googleadmodmodule.admob.AdManagerAppOpen
import com.example.googleadmodmodule.utility.UtilityOverall
import com.google.android.gms.ads.MobileAds


/**
 * Application class that initializes, loads
 * and show ads when activities change states. */
private val TAG = "GoogleAdMobModule"

class MyApplication : Application(), Application.ActivityLifecycleCallbacks, LifecycleObserver  {


    private lateinit var adManagerAppOpen: AdManagerAppOpen
    private var currentActivity: Activity? = null

    companion object{
        lateinit var adManager: AdManager

        fun showAdOpenApp(activity: Activity, application: MyApplication?){
            /*val application = application as? MyApplication*/

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
        adManagerAppOpen = AdManagerAppOpen()

        //3. Ad Manager
        adManager = AdManager()
    }

    /** -------------------------------------Lifecycle methods------------------------------------- */
    /** LifecycleObserver method that shows the app open ad when the app moves to foreground. */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        // Show the ad (if available) when the app moves to foreground.
        currentActivity?.let { adManagerAppOpen.showAdIfAvailable(it) }
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
        adManagerAppOpen.showAdIfAvailable(activity, callback)
    }

    /** -------------------------------------ActivityLifecycleCallback methods------------------------------------- */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated !")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "onActivityStarted !")
        // Updating the currentActivity only when an ad is not showing.
        if (!adManagerAppOpen.isShowingAd) {
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

    /** Interface definition for a callback to be invoked when an app open ad is complete. */
    interface Callback {
        fun showAd()
    }
}