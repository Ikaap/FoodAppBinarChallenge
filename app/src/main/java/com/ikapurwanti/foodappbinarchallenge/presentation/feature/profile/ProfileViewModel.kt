package com.ikapurwanti.foodappbinarchallenge.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepo: UserRepository): ViewModel() {
    fun getCurrentUser() = userRepo.getCurrentUser()

    fun doLogout(){
        userRepo.doLogout()
    }
}