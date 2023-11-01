package com.ikapurwanti.foodappbinarchallenge

import android.app.Application
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase
import com.ikapurwanti.foodappbinarchallenge.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
        initKoin()
    }

    private fun initKoin(){
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }
}