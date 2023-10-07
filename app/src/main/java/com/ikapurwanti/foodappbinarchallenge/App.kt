package com.ikapurwanti.foodappbinarchallenge

import android.app.Application
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}