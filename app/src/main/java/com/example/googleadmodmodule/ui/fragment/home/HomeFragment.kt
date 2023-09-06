package com.example.googleadmodmodule.ui.fragment.home

import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.admob.AdManagerAppOpen
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentHomeBinding


class HomeFragment : CoreFragment<FragmentHomeBinding>(
    layoutRes = R.layout.fragment_home
), HomeNavigator {

    override fun onResume() {
        super.onResume()
        AdManagerAppOpen.getInstance().enableAppOpenAd()
        //preload native ads for AdNativeFragment
        MyApplication.adManager.adNativeStandard.loadAd(activity = requireActivity())
        MyApplication.adManager.adNativeFullSize.loadAd(activity = requireActivity())
        MyApplication.adManager.adNativeMediumSize.loadAd(activity = requireActivity())
    }

    override fun setupView() {
        super.setupView()
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun navigateFromHomeToDestination(destination: NavDirections) {
        findNavController().navigate(destination)
    }
}