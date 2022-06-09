package com.atmtransaction

import android.app.Application
import com.atmtransaction.di.mainViewModel
import com.atmtransaction.di.trasactDB
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppClass)
            modules(
                trasactDB,
                mainViewModel,
                )
        }
    }
}