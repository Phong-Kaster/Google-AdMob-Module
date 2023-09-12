package com.example.googleadmodmodule.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.googleadmodmodule.core.CoreViewModel
import com.example.googleadmodmodule.database.entity.NationalityEntity
import com.example.googleadmodmodule.database.relation.UserRelation
import com.example.googleadmodmodule.database.repository.NationalityRepository
import com.example.googleadmodmodule.database.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor() : CoreViewModel() {
    //1. Here we inject 2 repositories we need
    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var nationalityRepository: NationalityRepository

    //2. This section has all variable that serves for some function in Home Fragment
    val userRelation: MutableLiveData<UserRelation?> = MutableLiveData()
    val nationalityId: MutableLiveData<Long?> = MutableLiveData()


    fun getUserInfo(uid: Int) {
        try {
            viewModelScope.launch {
                val info = withContext(Dispatchers.IO) { userRepository.getUserInfo(uid) }
                userRelation.postValue(info)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun insertNationality() {
        try {
            viewModelScope.launch {
               val id = withContext(Dispatchers.IO){
                    val element = NationalityEntity(language = "German", nation = "Germany", uid = 2)
                    nationalityRepository.insert(element)
                }
                nationalityId.postValue(id)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}