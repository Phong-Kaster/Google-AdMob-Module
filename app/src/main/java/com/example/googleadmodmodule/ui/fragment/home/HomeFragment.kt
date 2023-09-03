package com.example.googleadmodmodule.ui.fragment.home

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentHomeBinding


class HomeFragment : CoreFragment<FragmentHomeBinding>(
    layoutRes = R.layout.fragment_home
) {
    override fun setupView() {
        super.setupView()
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }
}