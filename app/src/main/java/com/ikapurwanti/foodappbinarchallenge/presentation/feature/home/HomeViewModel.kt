package com.ikapurwanti.foodappbinarchallenge.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.AppPreferenceDataSource
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepository
import com.ikapurwanti.foodappbinarchallenge.model.Category
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val menuRepo: MenuRepository,
    private val appPreferenceDataSource: AppPreferenceDataSource,
): ViewModel(){

    private val _categories = MutableLiveData<ResultWrapper<List<Category>>>()
    val categories: LiveData<ResultWrapper<List<Category>>>
        get() = _categories


    private val _menu = MutableLiveData<ResultWrapper<List<Menu>>>()
    val menu: LiveData<ResultWrapper<List<Menu>>>
        get() = _menu

    val appLayoutGridLiveData = appPreferenceDataSource.getAppLayoutFlow().asLiveData(Dispatchers.IO)


    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            menuRepo.getCategories().collect{
                _categories.postValue(it)
            }
        }
    }

    fun getMenu(category: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            menuRepo.getMenu(if (category == "all") null else category?.lowercase()).collect{
                _menu.postValue(it)
            }
        }
    }

    fun setAppLayoutPref(isGridLayout: Boolean){
        viewModelScope.launch {
            appPreferenceDataSource.setAppLayoutPref(isGridLayout)
        }
    }
}