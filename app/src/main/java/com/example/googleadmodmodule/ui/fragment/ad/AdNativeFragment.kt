package com.example.googleadmodmodule.ui.fragment.ad

import androidx.navigation.fragment.findNavController
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentAdNativeBinding
import com.example.googleadmodmodule.utility.UtilityView.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdNativeFragment : CoreFragment<FragmentAdNativeBinding>(
    layoutRes = R.layout.fragment_ad_native
) {
    override fun setupData() {
        super.setupData()
        showAds()
    }

    override fun setupEvent() {
        super.setupEvent()
        binding.buttonRefresh.clickWithDebounce(500){
            findNavController().navigateUp()
        }
    }

    private fun showAds(){
        MyApplication.adManager.adNativeStandard.showAd(layoutInflater = layoutInflater, adContainer = binding.layoutNativeStandard)
        MyApplication.adManager.adNativeFullSize.showAd(layoutInflater = layoutInflater, adContainer = binding.layoutNativeFullSize)
        MyApplication.adManager.adNativeMediumSize.showAd(layoutInflater = layoutInflater, adContainer = binding.layoutNativeMediumSize)
    }
}