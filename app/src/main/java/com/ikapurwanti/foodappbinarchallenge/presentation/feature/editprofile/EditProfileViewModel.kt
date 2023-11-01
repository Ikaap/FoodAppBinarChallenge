package com.ikapurwanti.foodappbinarchallenge.presentation.feature.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userRepo : UserRepository): ViewModel() {

    private val _changeProfileResult = MutableLiveData<ResultWrapper<Boolean>>()
    val changeProfileResult: LiveData<ResultWrapper<Boolean>>
        get() = _changeProfileResult

    fun getCurrentUser() = userRepo.getCurrentUser()

    fun changeProfile(fullName: String){
        viewModelScope.launch {
            userRepo.updateProfile(fullName).collect{
                _changeProfileResult.postValue(it)
            }
        }
    }

    fun createChangePasswordRequest(){
        userRepo.sendChangePasswordRequestByEmail()
    }

}