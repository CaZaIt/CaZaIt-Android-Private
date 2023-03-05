package org.cazait

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class CazaitApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}