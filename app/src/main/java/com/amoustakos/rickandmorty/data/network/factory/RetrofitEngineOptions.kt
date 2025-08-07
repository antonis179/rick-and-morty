package com.amoustakos.rickandmorty.data.network.factory

import androidx.annotation.Keep
import retrofit2.CallAdapter
import retrofit2.Converter

@Keep
data class RetrofitEngineOptions(
    val baseUrl: String,
    val converterFactories: List<Converter.Factory>,
    val adapterFactories: List<CallAdapter.Factory>
)