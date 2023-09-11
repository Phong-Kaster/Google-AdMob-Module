package com.example.googleadmodmodule.ui.fragment.ad

import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentAdBannerBinding
import com.example.googleadmodmodule.utility.UtilityView.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdBannerFragment : CoreFragment<FragmentAdBannerBinding>(
    layoutRes = R.layout.fragment_ad_banner
) {
    override fun setupEvent() {
        super.setupEvent()
        binding.buttonRefresh.clickWithDebounce(500) {
            showToast("Banner ads is being refresh !")
            showAds()
        }
    }

    override fun setupView() {
        super.setupView()
        showAds()
    }

    private fun showAds(){
        MyApplication.adManager.adBanner.loadAndShowAd(context = requireContext(), adContainer = binding.layoutBanner)
        MyApplication.adManager.adLargeBanner.loadAndShowAd(context = requireContext(), adContainer = binding.layoutLargeBanner)
        MyApplication.adManager.adFullBanner.loadAndShowAd(context = requireContext(), adContainer = binding.layoutFullBanner)

        MyApplication.adManager.adSmartBanner.loadAndShowAd(context = requireContext(), adContainer = binding.layoutSmartBanner)
        MyApplication.adManager.adLeaderBoard.loadAndShowAd(context = requireContext(), adContainer = binding.layoutLeaderBoard)
        MyApplication.adManager.adMediumRectangle.loadAndShowAd(context = requireContext(), adContainer = binding.layoutMediumRectangle)
    }
}