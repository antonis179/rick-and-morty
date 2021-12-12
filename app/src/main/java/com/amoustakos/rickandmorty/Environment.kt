package com.amoustakos.rickandmorty

import com.amoustakos.rickandmorty.di.annotations.DebugFlag
import timber.log.Timber
import javax.inject.Inject


class Environment @Inject constructor(
    @DebugFlag val isDebug: Boolean
) {

    fun initialize() {
        initLog()
    }

    private fun initLog() {
        if (isDebug)
            Timber.plant(Timber.DebugTree())
    }

}
