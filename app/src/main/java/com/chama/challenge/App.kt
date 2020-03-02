package com.chama.challenge

import android.app.Application
import com.chama.challenge.common.di.AppModule
import com.chama.challenge.heritages.di.HeritagesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                AppModule.create(),
                HeritagesModule.create()
            )
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}