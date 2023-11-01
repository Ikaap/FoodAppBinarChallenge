package com.ikapurwanti.foodappbinarchallenge.presentation.feature.splashscreen

import androidx.lifecycle.ViewModel
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository

class SplashScreenViewModel(private val userRepo: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = userRepo.isLoggedIn()
}
