package com.ikapurwanti.foodappbinarchallenge.presentation.feature.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepo : UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<ResultWrapper<Boolean>>()
    val registerResult: LiveData<ResultWrapper<Boolean>>
        get() = _registerResult

    fun doRegister(fullName: String, email: String, password: String){
        viewModelScope.launch {
            userRepo.doRegister(fullName, email, password).collect{
                _registerResult.postValue(it)
            }
        }
    }
}