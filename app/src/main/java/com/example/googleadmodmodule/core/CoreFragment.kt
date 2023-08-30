package com.example.googleadmodmodule.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

open class CoreFragment<dataBinding : ViewDataBinding>
constructor(
    @LayoutRes val layoutRes: Int
) : Fragment(), CoreInterface {

    /*1. Define variable*/

    /*Default TAG*/
    val TAG = "Google AdMob Module"

    /*Default data binding*/
    lateinit var binding: dataBinding

    /*For reusing all functions that implemented from CoreActivity*/
    private var coreInterface: CoreInterface? = null


    /*2. Configure what happens for all fragment in this application*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_splash, container, false)*/
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupView()
        setupEvent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CoreActivity<*>) {
            coreInterface = context
        }
    }

    protected open fun setupData() {}
    protected open fun setupView(){}
    protected open fun setupEvent() {}

    fun navigateTo(destination: NavDirections) {
        findNavController().navigate(destination)
    }

    /*3. Override functions from CoreActivity that has implemented from Core Interface*/
    override fun showToast(content: String) {
        coreInterface?.showToast(content)
    }
}