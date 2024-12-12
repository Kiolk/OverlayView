package com.github.kiolk.overlayview

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority.VERBOSE

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = VERBOSE)
    }
}