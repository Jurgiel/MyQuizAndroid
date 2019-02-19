package com.jurgielewicz.myquizandroid

import android.app.Application
import com.jurgielewicz.myquizandroid.di.AppModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, AppModule().app)

    }
}