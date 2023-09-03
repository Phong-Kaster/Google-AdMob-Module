package com.example.googleadmodmodule.admob

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.databinding.AdNativeLayoutFullSizeBinding
import com.example.googleadmodmodule.databinding.AdNativeLayoutMediumSizeBinding
import com.example.googleadmodmodule.databinding.AdNativeLayoutStandardBinding
import com.example.googleadmodmodule.utility.UtilityOverall
import com.example.googleadmodmodule.utility.UtilityView.makeGone
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

class AdNative
constructor(
    val adID: String = AdConstant.ADMOD_NATIVE_ID,
    val adLayout: Int? = R.layout.ad_native_layout_full_size,
    val adName: String? = "Native Ad"
) {
    private val TAG = AdConstant.ADMOD_TAG
    private lateinit var builder: AdLoader.Builder
    private lateinit var adLoader: AdLoader
    private var currentNativeAd: NativeAd? = null


    fun loadAd(activity: Activity) {
        // is network connected
        if (!UtilityOverall.isInternetConnected(activity)) {
            return
        }

        builder = AdLoader.Builder(activity, adID)
        builder.forNativeAd { nativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            // If this callback occurs after the activity is destroyed, you must call
            // destroy and return or you may get a memory leak.
            var activityDestroyed = false
            activityDestroyed = activity.isDestroyed
            if (activityDestroyed || activity.isFinishing || activity.isChangingConfigurations) {
                nativeAd.destroy()
                return@forNativeAd
            }


            // You must call destroy on old ads when you are done with them,
            // otherwise you will have a memory leak.
            currentNativeAd?.destroy()
            currentNativeAd = nativeAd
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_NATIVE,
                adName = adName,
                content = "is ready for showing!"
            )
        }

        //3. If video ads are shown then mute sound
        val mutedVideo = true
        val videoOptions = VideoOptions.Builder().setStartMuted(mutedVideo).build()
        val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
        builder.withNativeAdOptions(adOptions)

        //4. Configure adLoader
        val adListener = object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error =
                    """domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}""""
                AdLog.log(
                    tag = AdConstant.ADMOD_TAG,
                    adType = AdConstant.ADMOD_TYPE_NATIVE,
                    adName = adName,
                    content = error
                )
            }
        }
        adLoader = builder.withAdListener(adListener).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun showAd(layoutInflater: LayoutInflater, adContainer: ViewGroup) {
        //1. if current native ad is null then stop
        if (currentNativeAd == null) {
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_NATIVE,
                adName = adName,
                content = "is null !"
            )
            adContainer.makeGone()
            return
        }

        //2. if current native ad is ready then show
        when (adLayout) {
            R.layout.ad_native_layout_full_size -> {
                displayNativeAdFullSize(layoutInflater = layoutInflater, adContainer = adContainer)
            }

            R.layout.ad_native_layout_medium_size -> {
                displayNativeAdMediumSize(
                    layoutInflater = layoutInflater,
                    adContainer = adContainer
                )
            }

            else -> {
                displayNativeAdStandard(layoutInflater = layoutInflater, adContainer = adContainer)
            }
        }
    }

    fun destroyAd() = currentNativeAd?.destroy()


    /*****************************************************************************************/
    /*************************************SHOW AD IN XML LAYOUT*******************************/
    /*****************************************************************************************/
    private fun displayNativeAdFullSize(layoutInflater: LayoutInflater, adContainer: ViewGroup) {
        //1. Inflate the root view
        val nativeAdBinding = AdNativeLayoutFullSizeBinding.inflate(layoutInflater)
        val nativeAdView = nativeAdBinding.root as NativeAdView


        //2. Setup native ad's assets
        nativeAdView.mediaView = nativeAdBinding.adMedia
        nativeAdView.headlineView = nativeAdBinding.adHeadline
        nativeAdView.bodyView = nativeAdBinding.adBody
        nativeAdView.callToActionView = nativeAdBinding.adCallToAction
        nativeAdView.iconView = nativeAdBinding.adAppIcon
        /*nativeAdView.priceView = adView.findViewById(R.id.ad_price)*/
        nativeAdView.starRatingView = nativeAdBinding.adStars
        /*nativeAdView.storeView = adView.findViewById(R.id.ad_store)*/
        nativeAdView.advertiserView = nativeAdBinding.adAdvertiser


        //3. Map data on layout
        nativeAdBinding.adHeadline.text = currentNativeAd?.headline
        currentNativeAd?.mediaContent?.let { nativeAdBinding.adMedia.mediaContent = it }


        //4. These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to check before trying to display them.
        if (currentNativeAd?.body == null) {
            nativeAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adBody.visibility = View.VISIBLE
            nativeAdBinding.adBody.text = currentNativeAd!!.body
        }

        if (currentNativeAd?.callToAction == null) {
            nativeAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adCallToAction.visibility = View.VISIBLE
            nativeAdBinding.adCallToAction.text = currentNativeAd!!.callToAction
        }

        if (currentNativeAd?.icon == null) {
            nativeAdBinding.adAppIcon.visibility = View.GONE
        } else {
            nativeAdBinding.adAppIcon.setImageDrawable(currentNativeAd!!.icon?.drawable)
            nativeAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        /*if (nativeAd.price == null) {
            nativeAdBinding.adPrice.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adPrice.visibility = View.VISIBLE
            nativeAdBinding.adPrice.text = nativeAd.price
        }

        if (nativeAd.store == null) {
            nativeAdBinding.adStore.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adStore.visibility = View.VISIBLE
            nativeAdBinding.adStore.text = nativeAd.store
        }*/

        if (currentNativeAd?.starRating == null) {
            nativeAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adStars.rating = currentNativeAd!!.starRating!!.toFloat()
            nativeAdBinding.adStars.visibility = View.VISIBLE
        }

        if (currentNativeAd?.advertiser == null) {
            nativeAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adAdvertiser.text = currentNativeAd!!.advertiser
            nativeAdBinding.adAdvertiser.visibility = View.VISIBLE
        }


        //5. This method tells the Google Mobile Ads SDK that you have finished populating your native ad view with this native ad.
        nativeAdView.setNativeAd(currentNativeAd!!)


        //6. Get the video controller for the ad. One will always be provided, even if the ad doesn't have a video asset.
        val mediaContent = currentNativeAd!!.mediaContent
        val vc = mediaContent?.videoController


        //7. Updates the UI to say whether or not this ad has a video asset.
        if (vc != null && mediaContent.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.videoLifecycleCallbacks =
                object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.
                        super.onVideoEnd()
                        AdLog.log(
                            tag = AdConstant.ADMOD_TAG,
                            adType = AdConstant.ADMOD_TYPE_NATIVE,
                            adName = adName,
                            content = "ad has video"
                        )
                    }
                }
        } else {
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_NATIVE,
                adName = adName,
                content = "ad has not video"
            )
        }

        //0. populate native ad on Intro Screen
        adContainer.removeAllViews()
        adContainer.addView(nativeAdBinding.root)
    }

    private fun displayNativeAdMediumSize(layoutInflater: LayoutInflater, adContainer: ViewGroup) {
        //1. Inflate the root view
        val nativeAdBinding = AdNativeLayoutMediumSizeBinding.inflate(layoutInflater)
        val nativeAdView = nativeAdBinding.root as NativeAdView


        //2. Setup native ad's assets
        /*nativeAdView.mediaView = nativeAdBinding.adMedia*/
        nativeAdView.headlineView = nativeAdBinding.adHeadline
        nativeAdView.bodyView = nativeAdBinding.adBody
        nativeAdView.callToActionView = nativeAdBinding.adCallToAction
        nativeAdView.iconView = nativeAdBinding.adAppIcon
        /*nativeAdView.priceView = adView.findViewById(R.id.ad_price)*/
        nativeAdView.starRatingView = nativeAdBinding.adStars
        /*nativeAdView.storeView = adView.findViewById(R.id.ad_store)*/
        /*nativeAdView.advertiserView = nativeAdBinding.adAdvertiser*/


        //3. Map data on layout
        nativeAdBinding.adHeadline.text = currentNativeAd?.headline
        /*nativeAd.mediaContent?.let { nativeAdBinding.adMedia.mediaContent = it }*/


        //4. These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to check before trying to display them.
        if (currentNativeAd?.body == null) {
            nativeAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adBody.visibility = View.VISIBLE
            nativeAdBinding.adBody.text = currentNativeAd?.body
        }

        if (currentNativeAd?.callToAction == null) {
            nativeAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adCallToAction.visibility = View.VISIBLE
            nativeAdBinding.adCallToAction.text = currentNativeAd?.callToAction
        }

        if (currentNativeAd?.icon == null) {
            nativeAdBinding.adAppIcon.visibility = View.GONE
        } else {
            nativeAdBinding.adAppIcon.setImageDrawable(currentNativeAd?.icon?.drawable)
            nativeAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        /*if (nativeAd.price == null) {
            nativeAdBinding.adPrice.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adPrice.visibility = View.VISIBLE
            nativeAdBinding.adPrice.text = nativeAd.price
        }

        if (nativeAd.store == null) {
            nativeAdBinding.adStore.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adStore.visibility = View.VISIBLE
            nativeAdBinding.adStore.text = nativeAd.store
        }*/

        if (currentNativeAd?.starRating == null) {
            nativeAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adStars.rating = currentNativeAd?.starRating!!.toFloat()
            nativeAdBinding.adStars.visibility = View.VISIBLE
        }

        /*if (nativeAd.advertiser == null) {
            nativeAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adAdvertiser.text = nativeAd.advertiser
            nativeAdBinding.adAdvertiser.visibility = View.VISIBLE
        }*/


        //5. This method tells the Google Mobile Ads SDK that you have finished populating your native ad view with this native ad.
        nativeAdView.setNativeAd(currentNativeAd!!)


        //6. Get the video controller for the ad. One will always be provided, even if the ad doesn't have a video asset.
        val mediaContent = currentNativeAd?.mediaContent
        val vc = mediaContent?.videoController


        //7. Updates the UI to say whether or not this ad has a video asset.
        if (vc != null && mediaContent.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.videoLifecycleCallbacks =
                object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.
                        super.onVideoEnd()
                        AdLog.log(
                            tag = AdConstant.ADMOD_TAG,
                            adType = AdConstant.ADMOD_TYPE_NATIVE,
                            adName = adName,
                            content = "ad has video"
                        )
                    }
                }
        } else {
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_NATIVE,
                adName = adName,
                content = "ad has not video"
            )
        }

        //0. populate native ad on Intro Screen
        adContainer.removeAllViews()
        adContainer.addView(nativeAdBinding.root)
    }

    private fun displayNativeAdStandard(layoutInflater: LayoutInflater, adContainer: ViewGroup) {
        //1. Inflate the root view
        val nativeAdBinding = AdNativeLayoutStandardBinding.inflate(layoutInflater)
        val nativeAdView = nativeAdBinding.root as NativeAdView


        //2. Setup native ad's assets
        nativeAdView.mediaView = nativeAdBinding.adMedia
        nativeAdView.headlineView = nativeAdBinding.adHeadline
        nativeAdView.bodyView = nativeAdBinding.adBody
        nativeAdView.callToActionView = nativeAdBinding.adCallToAction
        nativeAdView.iconView = nativeAdBinding.adAppIcon
        nativeAdView.priceView = nativeAdBinding.adPrice
        nativeAdView.starRatingView = nativeAdBinding.adStars
        nativeAdView.storeView = nativeAdBinding.adStore
        nativeAdView.advertiserView = nativeAdBinding.adAdvertiser


        //3. Map data on layout
        nativeAdBinding.adHeadline.text = currentNativeAd?.headline
        currentNativeAd?.mediaContent?.let { nativeAdBinding.adMedia.mediaContent = it }


        //4. These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to check before trying to display them.
        if (currentNativeAd?.body == null) {
            nativeAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adBody.visibility = View.VISIBLE
            nativeAdBinding.adBody.text = currentNativeAd?.body
        }

        if (currentNativeAd?.callToAction == null) {
            nativeAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adCallToAction.visibility = View.VISIBLE
            nativeAdBinding.adCallToAction.text = currentNativeAd?.callToAction
        }

        if (currentNativeAd?.icon == null) {
            nativeAdBinding.adAppIcon.visibility = View.GONE
        } else {
            nativeAdBinding.adAppIcon.setImageDrawable(currentNativeAd?.icon?.drawable)
            nativeAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        if (currentNativeAd?.price == null) {
            nativeAdBinding.adPrice.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adPrice.visibility = View.VISIBLE
            nativeAdBinding.adPrice.text = currentNativeAd?.price
        }

        if (currentNativeAd?.store == null) {
            nativeAdBinding.adStore.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adStore.visibility = View.VISIBLE
            nativeAdBinding.adStore.text = currentNativeAd?.store
        }

        if (currentNativeAd?.starRating == null) {
            nativeAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adStars.rating = currentNativeAd?.starRating!!.toFloat()
            nativeAdBinding.adStars.visibility = View.VISIBLE
        }

        if (currentNativeAd?.advertiser == null) {
            nativeAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adAdvertiser.text = currentNativeAd?.advertiser
            nativeAdBinding.adAdvertiser.visibility = View.VISIBLE
        }


        //5. This method tells the Google Mobile Ads SDK that you have finished populating your native ad view with this native ad.
        nativeAdView.setNativeAd(currentNativeAd!!)


        //6. Get the video controller for the ad. One will always be provided, even if the ad doesn't have a video asset.
        val mediaContent = currentNativeAd?.mediaContent
        val vc = mediaContent?.videoController


        //7. Updates the UI to say whether or not this ad has a video asset.
        if (vc != null && mediaContent.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.videoLifecycleCallbacks =
                object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.
                        super.onVideoEnd()
                        AdLog.log(
                            tag = AdConstant.ADMOD_TAG,
                            adType = AdConstant.ADMOD_TYPE_NATIVE,
                            adName = adName,
                            content = "ad has video"
                        )
                    }
                }
        } else {
            AdLog.log(
                tag = AdConstant.ADMOD_TAG,
                adType = AdConstant.ADMOD_TYPE_NATIVE,
                adName = adName,
                content = "ad has not video"
            )
        }

        //0. populate native ad on Intro Screen
        adContainer.removeAllViews()
        adContainer.addView(nativeAdBinding.root)
    }
}

