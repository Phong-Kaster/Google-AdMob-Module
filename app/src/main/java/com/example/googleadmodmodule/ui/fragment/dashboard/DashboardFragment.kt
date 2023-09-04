package com.example.googleadmodmodule.ui.fragment.dashboard

import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentDashboardBinding
import com.example.googleadmodmodule.ui.fragment.home.HomeFragment
import com.example.googleadmodmodule.ui.fragment.home.HomeFragmentDirections
import com.example.googleadmodmodule.ui.fragment.home.HomeNavigator
import com.example.googleadmodmodule.utility.UtilityView.clickWithDebounce


class DashboardFragment : CoreFragment<FragmentDashboardBinding>(
    layoutRes = R.layout.fragment_dashboard
) {

    private var homeNavigator: HomeNavigator? = null

    override fun setupData() {
        super.setupData()
        if (parentFragment?.parentFragment is HomeFragment) {
            homeNavigator = parentFragment?.parentFragment as HomeFragment
        }
    }

    override fun setupEvent() {
        super.setupEvent()
        binding.buttonAdBanner.clickWithDebounce(500) {
            val destination = HomeFragmentDirections.actionHomeToAdBanner()
            homeNavigator?.navigateFromHomeToDestination(destination)
        }

        binding.buttonAdNative.clickWithDebounce(500) {
            val destination = HomeFragmentDirections.actionHomeToAdNative()
            homeNavigator?.navigateFromHomeToDestination(destination)
        }
    }
}