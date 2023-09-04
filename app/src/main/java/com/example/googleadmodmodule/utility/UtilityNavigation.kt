package com.example.googleadmodmodule.utility

import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

object UtilityNavigation {
    fun Fragment.gotoDestination(action: NavDirections) {
        val currentDestination: NavDestination? = findNavController().currentDestination
        val className = (currentDestination as? FragmentNavigator.Destination)?.className
        if (this::class.java.name.equals(className, true)) {
            findNavController().navigate(action)
        }
    }

    fun Fragment.goBack() {
        val navDirections: NavDestination? = findNavController().currentDestination
        val className = (navDirections as? FragmentNavigator.Destination)?.className
        if (this::class.java.name.equals(className, true)) {
            findNavController().navigateUp()
        }
    }

    fun Fragment.popBackStack(id: Int, inclusive: Boolean, saveState: Boolean) {
        val navDirections: NavDestination? = findNavController().currentDestination
        val className = (navDirections as? FragmentNavigator.Destination)?.className
        if (this::class.java.name.equals(className, true)) {
            findNavController().popBackStack(id, inclusive = inclusive, saveState = saveState)
        }
    }
}