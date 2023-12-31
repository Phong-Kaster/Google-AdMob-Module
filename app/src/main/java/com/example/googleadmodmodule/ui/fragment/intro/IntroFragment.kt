package com.example.googleadmodmodule.ui.fragment.intro

import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.bloodsugar.utils.transformation.ZoomOutSlideTransformation
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.admob.AdManagerAppOpen
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentIntroBinding
import com.example.googleadmodmodule.ui.adapter.IntroAdapter
import com.example.googleadmodmodule.utility.UtilitySharedPreference
import com.example.googleadmodmodule.utility.UtilityView.autoScroll
import com.example.googleadmodmodule.utility.UtilityView.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroFragment : CoreFragment<FragmentIntroBinding>(
    R.layout.fragment_intro
) {
    private var adapter: IntroAdapter? = null



    override fun setupData() {
        super.setupData()
        MyApplication.adManager.adNativeIntro.showAd(layoutInflater = layoutInflater, adContainer = binding.layoutNativeAd)
    }

    override fun setupView() {
        super.setupView()
        prepareViewPager()
    }

    override fun setupEvent() {
        binding.start.clickWithDebounce(1000) {
            sharedPreference.isIntroShown = false
            val destination = IntroFragmentDirections.actionIntroToHome()
            navigateTo(destination)
        }

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
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        MyApplication.adManager.adNativeIntro.destroyAd()
        super.onDestroyView()
    }

    private fun prepareViewPager() {
        adapter = IntroAdapter(childFragmentManager)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.autoScroll(2500)
        binding.viewPager.setPageTransformer(true, ZoomOutSlideTransformation())
    }

    override fun onResume() {
        super.onResume()
        AdManagerAppOpen.getInstance().disableAppOpenAd()
    }
}