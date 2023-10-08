package com.ikapurwanti.foodappbinarchallenge.presentation.feature.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.AppPreferenceDataSource
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepository
import com.ikapurwanti.foodappbinarchallenge.model.Cart
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepo : CartRepository
) : ViewModel(){
    val cartListOrder = cartRepo.getCartData().asLiveData(Dispatchers.IO)

    fun deleteAllCart(){
        viewModelScope.launch {
            cartRepo.deleteAllCart()
        }
    }
}