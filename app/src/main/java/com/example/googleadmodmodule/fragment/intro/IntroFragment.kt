package com.example.googleadmodmodule.fragment.intro

import android.os.Build
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.bloodsugar.utils.transformation.ZoomOutSlideTransformation
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.Constant
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.AdNativeLayoutFullSizeBinding
import com.example.googleadmodmodule.databinding.AdNativeLayoutMediumSizeBinding
import com.example.googleadmodmodule.databinding.FragmentIntroBinding
import com.example.googleadmodmodule.fragment.adapter.IntroAdapter
import com.example.googleadmodmodule.utility.UtilityView.autoScroll
import com.example.googleadmodmodule.utility.UtilityView.clickWithDebounce
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

class IntroFragment : CoreFragment<FragmentIntroBinding>(
    R.layout.fragment_intro
) {
    var adapter: IntroAdapter? = null

    /*native ad*/
    private lateinit var adRequest: AdRequest
    private lateinit var adLoader: AdLoader
    private var currentNativeAd: NativeAd? = null


    override fun setupView() {
        super.setupView()
        adapter = IntroAdapter(childFragmentManager)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.autoScroll(2500)
        binding.viewPager.setPageTransformer(true, ZoomOutSlideTransformation())

        prepareAd()
    }

    override fun setupEvent() {
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        binding.start.clickWithDebounce(1000) {
            showToast("App is refreshing a new advertisement !")
            prepareAd()
        }
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        currentNativeAd?.destroy()
        super.onDestroyView()
    }

    /*****************************************************************************************/
    /*************************************ADMOD ADVERTISEMENT**********************************/
    /*****************************************************************************************/
    private fun prepareAd() {
        //1. define ad builder
        val builder = AdLoader.Builder(requireContext(), Constant.ADMOD_NATIVE_ID)


        //2. handle ad callbacks
        builder.forNativeAd { nativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            // If this callback occurs after the activity is destroyed, you must call
            // destroy and return or you may get a memory leak.
            var activityDestroyed = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activityDestroyed = requireActivity().isDestroyed
            }
            if (activityDestroyed || requireActivity().isFinishing || requireActivity().isChangingConfigurations) {
                nativeAd.destroy()
                return@forNativeAd
            }


            // You must call destroy on old ads when you are done with them,
            // otherwise you will have a memory leak.
            currentNativeAd?.destroy()
            currentNativeAd = nativeAd


            //populate ad in XML layout
            /*displayNativeAdFullSize(nativeAd = nativeAd)*/
            displayNativeAdMediumSize(nativeAd = nativeAd)
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
                showToast("Failed to load native ad with error $error")
                Log.d(TAG, "onAdFailedToLoad - error: $error")
            }
        }
        adLoader = builder.withAdListener(adListener).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }


    /**
     * display a native ad with full size
     */
    private fun displayNativeAdFullSize(nativeAd: NativeAd) {
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
        nativeAdBinding.adHeadline.text = nativeAd.headline
        nativeAd.mediaContent?.let { nativeAdBinding.adMedia.mediaContent = it }



        //4. These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to check before trying to display them.
        if (nativeAd.body == null) {
            nativeAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adBody.visibility = View.VISIBLE
            nativeAdBinding.adBody.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            nativeAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adCallToAction.visibility = View.VISIBLE
            nativeAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            nativeAdBinding.adAppIcon.visibility = View.GONE
        } else {
            nativeAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
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

        if (nativeAd.starRating == null) {
            nativeAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            nativeAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            nativeAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adAdvertiser.text = nativeAd.advertiser
            nativeAdBinding.adAdvertiser.visibility = View.VISIBLE
        }


        //5. This method tells the Google Mobile Ads SDK that you have finished populating your native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)


        //6. Get the video controller for the ad. One will always be provided, even if the ad doesn't have a video asset.
        val mediaContent = nativeAd.mediaContent
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
                        Log.d(TAG, "populateAd - ad has video")
                    }
                }
        } else {
            Log.d(TAG, "populateAd - ad has not video")
        }

        //0. populate native ad on Intro Screen
        binding.layoutNativeAd.removeAllViews()
        binding.layoutNativeAd.addView(nativeAdBinding.root)
    }

    private fun displayNativeAdMediumSize(nativeAd: NativeAd) {
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
        nativeAdBinding.adHeadline.text = nativeAd.headline
        /*nativeAd.mediaContent?.let { nativeAdBinding.adMedia.mediaContent = it }*/



        //4. These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to check before trying to display them.
        if (nativeAd.body == null) {
            nativeAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adBody.visibility = View.VISIBLE
            nativeAdBinding.adBody.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            nativeAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adCallToAction.visibility = View.VISIBLE
            nativeAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            nativeAdBinding.adAppIcon.visibility = View.GONE
        } else {
            nativeAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
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

        if (nativeAd.starRating == null) {
            nativeAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            nativeAdBinding.adStars.visibility = View.VISIBLE
        }

        /*if (nativeAd.advertiser == null) {
            nativeAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            nativeAdBinding.adAdvertiser.text = nativeAd.advertiser
            nativeAdBinding.adAdvertiser.visibility = View.VISIBLE
        }*/


        //5. This method tells the Google Mobile Ads SDK that you have finished populating your native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)


        //6. Get the video controller for the ad. One will always be provided, even if the ad doesn't have a video asset.
        val mediaContent = nativeAd.mediaContent
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
                        Log.d(TAG, "populateAd - ad has video")
                    }
                }
        } else {
            Log.d(TAG, "populateAd - ad has not video")
        }

        //0. populate native ad on Intro Screen
        binding.layoutNativeAd.removeAllViews()
        binding.layoutNativeAd.addView(nativeAdBinding.root)
    }
}