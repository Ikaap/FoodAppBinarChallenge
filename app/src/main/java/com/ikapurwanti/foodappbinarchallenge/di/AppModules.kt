package com.ikapurwanti.foodappbinarchallenge.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDatabaseDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.AppPreferenceDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.AppPreferenceDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.appDataStore
import com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource.RestaurantApiDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource.RestaurantDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.service.RestaurantService
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.cart.CartViewModel
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.HomeViewModel
import com.ikapurwanti.foodappbinarchallenge.utils.AssetWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.PreferenceDataStoreHelper
import com.ikapurwanti.foodappbinarchallenge.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single <PreferenceDataStoreHelper> {PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { RestaurantService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
    }

    private val dataSourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<AppPreferenceDataSource> { AppPreferenceDataSourceImpl(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
        single<RestaurantDataSource> { RestaurantApiDataSource(get())  }
    }

    private val repositoryModule = module {
        single<CartRepository> { CartRepositoryImpl(get(), get())  }
        single<UserRepository> { UserRepositoryImpl(get())  }
        single<MenuRepository> { MenuRepositoryImpl(get())  }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
    }

    private val utilsModule = module {
        single { AssetWrapper(androidContext()) }
    }

    val modules: List<Module> = listOf(
        localModule, networkModule, dataSourceModule, repositoryModule, viewModelModule, utilsModule
    )
}