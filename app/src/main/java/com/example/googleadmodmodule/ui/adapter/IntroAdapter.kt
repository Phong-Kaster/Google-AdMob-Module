package com.example.googleadmodmodule.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.ui.fragment.intro.Intro
import com.example.googleadmodmodule.ui.fragment.intro.IntroContentFragment

class IntroAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private val list = mutableListOf(
        Intro(R.drawable.ic_google, R.string.dummy_title, R.string.dummy_content),
        Intro(R.drawable.ic_google, R.string.dummy_title, R.string.dummy_content),
        Intro(R.drawable.ic_google, R.string.dummy_title, R.string.dummy_content)
    )

    companion object {
        val PAGE_INDEX_1 = 0
        val PAGE_INDEX_2 = 1
        val PAGE_INDEX_3 = 2

        val NUMBER_PAGE = 3
    }

    override fun getItem(position: Int): Fragment {

        val onboarding = list[position]
        val fragment = IntroContentFragment.newInstance(onboarding)
        return fragment
    }

    override fun getCount(): Int {
        return NUMBER_PAGE
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "$position"
    }
}