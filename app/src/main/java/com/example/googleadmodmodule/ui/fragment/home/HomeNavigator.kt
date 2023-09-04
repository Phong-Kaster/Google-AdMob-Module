package com.example.googleadmodmodule.ui.fragment.home

import androidx.navigation.NavDirections

/**
 * this interface is used to navigate from all fragments that are contained by home fragment
 *
 * For example: dashboard fragment(child) is contained by home fragment(parent).
 * 1. To navigate from child to other fragments has same level
 * as parent fragment.
 * 2. We have to HomeNavigator to back from previous nav host(parent's nav host)
 * and use it to navigate to other fragments has same level
 */
interface HomeNavigator {
    fun navigateFromHomeToDestination(destination: NavDirections)
}