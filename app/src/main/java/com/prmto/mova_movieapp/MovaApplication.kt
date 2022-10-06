package com.prmto.mova_movieapp

import android.app.Application
import androidx.core.os.ConfigurationCompat
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MovaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Timber.d(ConfigurationCompat.getLocales(resources.configuration)[0]?.country.toString())
    }
}