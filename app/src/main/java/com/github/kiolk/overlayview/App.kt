package com.github.kiolk.overlayview

import android.app.Application
import com.github.kiolk.overlayview.di.modules.dataModule
import com.github.kiolk.overlayview.di.modules.networkModule
import com.github.kiolk.overlayview.di.modules.useCaseModule
import com.github.kiolk.overlayview.di.modules.viewModelModule
import logcat.AndroidLogcatLogger
import logcat.LogPriority.VERBOSE
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = VERBOSE)
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(viewModelModule, networkModule, dataModule, useCaseModule)
        }
    }
}