package com.ikapurwanti.foodappbinarchallenge.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepository
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val menuRepo: MenuRepository
): ViewModel(){

    val menuList : LiveData<ResultWrapper<List<Menu>>>
        get() = menuRepo.getMenu().asLiveData(Dispatchers.IO)
}