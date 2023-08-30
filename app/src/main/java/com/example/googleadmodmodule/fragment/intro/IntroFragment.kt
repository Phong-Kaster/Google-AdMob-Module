package com.example.googleadmodmodule.fragment.intro

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.bloodsugar.utils.transformation.ZoomOutSlideTransformation
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.Constant
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentIntroBinding
import com.example.googleadmodmodule.fragment.adapter.IntroAdapter
import com.example.googleadmodmodule.utility.UtilityView.autoScroll
import com.example.googleadmodmodule.utility.UtilityView.clickWithDebounce
import com.example.googleadmodmodule.utility.UtilityView.makeGone
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

class IntroFragment : CoreFragment<FragmentIntroBinding>(
    R.layout.fragment_intro
) {
    var adapter: IntroAdapter? = null

    var isDestroyed = false


    override fun onDestroy() {
        super.onDestroy()
        isDestroyed = true
    }
    // native ad
    val adRequest = AdRequest.Builder().build()
    val adLoader = AdLoader.Builder(requireContext(), Constant.NATIVE_AD_FAKE_ID)
        .forNativeAd { ad : NativeAd ->
            // Show the ad.
            Log.d(TAG, "ad is ready! ")
            if(isDestroyed){
                ad.destroy()
                return@forNativeAd
            }
        }
        .withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Handle the failure by logging, altering the UI, and so on.
                Log.d(TAG, "onAdFailedToLoad: $adError")
            }
        })
        .withNativeAdOptions(
            NativeAdOptions.Builder()
            // Methods in the NativeAdOptions.Builder class can be
            // used here to specify individual options settings.
            .build())
        .build()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adLoader.loadAd(adRequest)
    }

    override fun setupView() {
        super.setupView()
        adapter = IntroAdapter(childFragmentManager)

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.autoScroll(2500)
        binding.viewPager.setPageTransformer(true, ZoomOutSlideTransformation())

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
            binding.layoutNativeAdvertisement.makeGone()
        }
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }

}