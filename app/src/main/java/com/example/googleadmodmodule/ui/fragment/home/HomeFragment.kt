package com.example.googleadmodmodule.ui.fragment.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.admob.AdManagerAppOpen
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : CoreFragment<FragmentHomeBinding>(
    layoutRes = R.layout.fragment_home
), HomeNavigator {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onResume() {
        super.onResume()
        AdManagerAppOpen.getInstance().enableAppOpenAd()
        //preload native ads for AdNativeFragment
        MyApplication.adManager.adNativeStandard.loadAd(activity = requireActivity())
        MyApplication.adManager.adNativeFullSize.loadAd(activity = requireActivity())
        MyApplication.adManager.adNativeMediumSize.loadAd(activity = requireActivity())
    }

    override fun setupData() {
        super.setupData()
        makeSomeFakeActionToDatabase()
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

    private fun makeSomeFakeActionToDatabase() {
        //1. Insert some data into Room database
        viewModel.getUserInfo(1)
        viewModel.insertNationality()

        //2. observe what happens
        viewModel.userRelation.observe(viewLifecycleOwner) { userInfo ->
            if (userInfo != null) {
                Log.d(
                    TAG,
                    "userInfo: ${userInfo.userEntity.firstName} ${userInfo.userEntity.lastName}"
                )
                Log.d(TAG, "userInfo: ${userInfo.nationalityAtBirth.nation}")
                Log.d(TAG, "userInfo: ${userInfo.playlists.size}")
            }
        }

        viewModel.nationalityId.observe(viewLifecycleOwner) { nationalityId ->
            Log.d(TAG, "nationalityId: $nationalityId")
        }
    }
}