package com.ikapurwanti.foodappbinarchallenge

import android.app.Application
import android.content.Context
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase
import com.ikapurwanti.foodappbinarchallenge.di.AppInjection
import com.ikapurwanti.foodappbinarchallenge.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App: Application() {
//    init {
//        instance = this
//    }
//
//    companion object {
//        private var instance: App? = null
//
//        fun applicationContext(): Context{
//            return instance!!.applicationContext
//        }
//    }

    override fun onCreate() {
        super.onCreate()
        //AppDatabase.getInstance(this)
//        val context: Context = App.applicationContext()
        initKoin()
    }

    private fun initKoin(){
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }
}