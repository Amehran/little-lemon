package com.arminmehran.little_lemmon_app_capstone

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class LittleLemonApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}