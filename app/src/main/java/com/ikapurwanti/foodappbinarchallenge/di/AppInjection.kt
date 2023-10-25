package com.ikapurwanti.foodappbinarchallenge.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ikapurwanti.foodappbinarchallenge.App
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDatabaseDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.AppPreferenceDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.appDataStore
import com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource.RestaurantApiDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.service.RestaurantService
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.cart.CartViewModel
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.HomeViewModel
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory
import com.ikapurwanti.foodappbinarchallenge.utils.PreferenceDataStoreHelperImpl

object AppInjection {

    // data store
    val dataStore = App.applicationContext().appDataStore
    val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
    val appPreferenceDataSource = AppPreferenceDataSourceImpl(dataStoreHelper)
    // database
    val database = AppDatabase.getInstance(App.applicationContext())
    val cartDao = database.cartDao()
    val cartDataSource = CartDatabaseDataSource(cartDao)

    val chuckerInterceptor = ChuckerInterceptor(App.applicationContext())
    val service = RestaurantService.invoke(chuckerInterceptor)
    val restaurantDataSource = RestaurantApiDataSource(service)
    val menuRepo : MenuRepository = MenuRepositoryImpl(restaurantDataSource)
    val cartRepo : CartRepository = CartRepositoryImpl(cartDataSource, restaurantDataSource)


    fun getHomeViewModel() = GenericViewModelFactory.create(
        HomeViewModel(menuRepo, appPreferenceDataSource)
    )

    fun getCartViewModel() = GenericViewModelFactory.create(
        CartViewModel(cartRepo)
    )

}