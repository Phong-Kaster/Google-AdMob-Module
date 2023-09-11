package com.example.googleadmodmodule

import com.example.googleadmodmodule.core.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor() : CoreViewModel() {
    val tag = "Main View Model"
}