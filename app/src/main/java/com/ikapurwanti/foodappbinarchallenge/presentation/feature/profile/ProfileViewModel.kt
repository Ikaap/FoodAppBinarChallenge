package com.ikapurwanti.foodappbinarchallenge.presentation.feature.profile

import androidx.lifecycle.ViewModel
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository

class ProfileViewModel(private val userRepo: UserRepository): ViewModel() {
    fun getCurrentUser() = userRepo.getCurrentUser()

    fun doLogout(){
        userRepo.doLogout()
    }
}