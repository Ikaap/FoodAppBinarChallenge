package com.ikapurwanti.foodappbinarchallenge.presentation.feature.detailmenu

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepository
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.launch

class DetailMenuViewModel
    (
    private val extras: Bundle?,
    private val cartRepo: CartRepository
) : ViewModel() {

    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRA_MENU)

    val priceLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }

    val menuCountLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }

    private val _addToCartResult = MutableLiveData<ResultWrapper<Boolean>>()

    val addToCartResult: LiveData<ResultWrapper<Boolean>>
        get() = _addToCartResult

    fun plusOrder() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0)
    }

    fun minusOrder() {
        val count = (menuCountLiveData.value ?: 0) - 1
        if ((menuCountLiveData.value ?: 0) <= 0) return
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0)
    }

    fun addToCart() {
        viewModelScope.launch {
            val menuQty =
                if ((menuCountLiveData.value ?: 0) <= 0) {
                    return@launch
                } else {
                    menuCountLiveData.value ?: 0
                }
            menu?.let {
                cartRepo.createCart(it, menuQty).collect() { result ->
                    _addToCartResult.postValue(result)
                }
            }
        }

    }

}
