package com.ikapurwanti.foodappbinarchallenge.presentation.detailmenu

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikapurwanti.foodappbinarchallenge.model.Menu

class DetailMenuViewModel(private val extras : Bundle?) : ViewModel(){

    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRA_MENU)

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }

    val menuCountLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }

    fun plusOrder(){
        val count =(menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
    }

    fun minOrder(){
        val count = (menuCountLiveData.value ?: 0) - 1
        if ((menuCountLiveData.value ?: 0) <= 0) return
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
    }


}
