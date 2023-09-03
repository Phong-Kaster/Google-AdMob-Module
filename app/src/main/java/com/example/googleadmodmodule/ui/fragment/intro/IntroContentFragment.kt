package com.example.googleadmodmodule.ui.fragment.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentIntroContentBinding

/**
 * show content of an intro
 */
class IntroContentFragment : CoreFragment<FragmentIntroContentBinding>(
    layoutRes = R.layout.fragment_intro_content
) {
    companion object {
        const val PHOTO = "photo"
        const val TITLE = "title"
        const val CONTENT = "content"
        fun newInstance(onboarding: Intro): IntroContentFragment {

            val bundle = Bundle()
            bundle.putInt(PHOTO, onboarding.photo)
            bundle.putInt(TITLE, onboarding.title)
            bundle.putInt(CONTENT, onboarding.content)

            val fragment = IntroContentFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setupView() {
        val photo = requireArguments().getInt(PHOTO)
        val title = requireArguments().getInt(TITLE)
        val content = requireArguments().getInt(CONTENT)

        binding.elementPhoto.setImageResource(photo)
        binding.elementTitle.text = requireContext().resources.getString(title)
        binding.elementContent.text = requireContext().resources.getString(content)
    }
}