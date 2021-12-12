package com.amoustakos.rickandmorty

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RaMApplication : Application() {

    @Inject
    lateinit var environment: Environment

    override fun onCreate() {
        super.onCreate()
        environment.initialize()
    }

}
