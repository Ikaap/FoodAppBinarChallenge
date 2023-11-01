package com.ikapurwanti.foodappbinarchallenge.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.model.UserViewParam

class ProfileViewModel(private val userRepo: UserRepository) : ViewModel() {

    private val _getProfileResult = MutableLiveData<UserViewParam?>()
    val getProfileResult: LiveData<UserViewParam?>
        get() = _getProfileResult

    fun getProfileData() {
        val data = userRepo.getCurrentUser()
        _getProfileResult.postValue(data)
    }

    fun getCurrentUser() = userRepo.getCurrentUser()

    fun doLogout() {
        userRepo.doLogout()
    }
}
