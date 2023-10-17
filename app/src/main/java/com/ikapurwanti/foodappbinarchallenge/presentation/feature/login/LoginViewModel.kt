package com.ikapurwanti.foodappbinarchallenge.presentation.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepo: UserRepository) : ViewModel(){

    private val _loginResult = MutableLiveData<ResultWrapper<Boolean>>()
    val loginResult: LiveData<ResultWrapper<Boolean>>
        get() = _loginResult

    fun doLogin(email: String, password: String){
        viewModelScope.launch {
            userRepo.doLogin(email, password).collect{
                _loginResult.postValue(it)
            }
        }
    }
}