package com.ikapurwanti.foodappbinarchallenge.presentation.feature.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.AppPreferenceDataSource
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepository
import com.ikapurwanti.foodappbinarchallenge.model.Categories
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val menuRepo: MenuRepository,
    private val appPreferenceDataSource: AppPreferenceDataSource
): ViewModel(){

    val menuList : LiveData<ResultWrapper<List<Menu>>>
        get() = menuRepo.getMenu().asLiveData(Dispatchers.IO)

    val appLayoutGridLiveData = appPreferenceDataSource.getAppLayoutFlow().asLiveData(Dispatchers.IO)

    fun setAppLayoutPref(isGridLayout: Boolean){
        viewModelScope.launch {
            appPreferenceDataSource.setAppLayoutPref(isGridLayout)
        }
    }



}