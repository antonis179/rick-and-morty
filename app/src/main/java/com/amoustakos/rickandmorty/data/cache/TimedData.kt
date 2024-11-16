package com.amoustakos.rickandmorty.data.cache

import com.amoustakos.rickandmorty.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


data class TimedData<Data>(
    private val dispatcherProvider: DispatcherProvider,
    val storedTime: Long,
    val data: Data
): CoroutineScope {

    override val coroutineContext: CoroutineContext = dispatcherProvider.io

    fun initTimer(time: Int, onDelete: () -> Unit) {
        timer(time.seconds).onCompletion {
            onDelete()
        }.launchIn(this)
    }

    private fun timer(
        period: Duration
    ) = flow {
        delay(period)
        emit(Unit)
    }

}