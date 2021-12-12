package com.amoustakos.rickandmorty.utils

import com.amoustakos.rickandmorty.di.annotations.DefaultDispatcher
import com.amoustakos.rickandmorty.di.annotations.IoDispatcher
import com.amoustakos.rickandmorty.di.annotations.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class DispatchersWrapper @Inject constructor(
    @IoDispatcher val io: CoroutineDispatcher,
    @DefaultDispatcher val default: CoroutineDispatcher,
    @MainDispatcher val main: CoroutineDispatcher
)
