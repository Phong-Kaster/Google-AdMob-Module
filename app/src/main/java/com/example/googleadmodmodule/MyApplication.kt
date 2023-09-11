package com.example.googleadmodmodule

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.example.googleadmodmodule.admob.AdManager
import com.example.googleadmodmodule.admob.AdManagerAppOpen
import com.example.googleadmodmodule.utility.UtilityOverall
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp


/**
 * Application class that initializes, loads
 * and show ads when activities change states. */
private val TAG = "GoogleAdMobModule"

@HiltAndroidApp
class MyApplication : Application(), Application.ActivityLifecycleCallbacks  {



    companion object{
        lateinit var adManager: AdManager
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
        /*ProcessLifecycleOwner.get().lifecycle.addObserver(this)*/

        //2. define appOpenAd Manager
        /*adManagerAppOpen = AdManagerAppOpen()*/

        //3. Ad Manager
        adManager = AdManager()
    }


    /** -------------------------------------ActivityLifecycleCallback methods------------------------------------- */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated !")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "onActivityStarted !")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(TAG, "onActivityResumed !")
        if(AdManagerAppOpen.getInstance().canAppOpenAdShown){
            AdManagerAppOpen.getInstance().showAd(activity, activity.application as MyApplication)
        }
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