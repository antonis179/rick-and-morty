package com.amoustakos.rickandmorty.data.network.factory

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object DefaultNetworkConfigFactory {

    fun defaultRetrofitOptions(
        baseUrl: String
    ): RetrofitEngineOptions {

        return RetrofitEngineOptions(
            baseUrl = baseUrl,
            adapterFactories = listOf(),
            /*
             * The order is important!
             * Make sure scalars is first or gson will throw an exception
             * before scalars converter is used.
             */
            converterFactories = listOf(
                GsonConverterFactory.create(makeDefaultGson())
            )
        )
    }

    /**
     * Provides the default [OkhttpOptions] for the network client
     */
    @JvmStatic
    fun defaultOkhttpOptions(
        logEnabled: Boolean
    ): OkhttpOptions {
        return OkhttpOptions(
            logEnabled = logEnabled,
            logLevel = HttpLoggingInterceptor.Level.BODY,

            connectTimeout = 15,
            readTimeout = 60,
            writeTimeout = 60,
            timeUnit = TimeUnit.SECONDS,

            cacheDir = null,
            cacheSizeMb = null,
            cacheSubDirectory = null,

            interceptors = makeDefaultInterceptors()
        )
    }

    /**
     * Provides the default [Gson] implementation used for network in the app
     */
    fun makeDefaultGson(): Gson {
        return GsonBuilder()
            .create()
    }

    fun makeDefaultInterceptors(): List<InterceptorWithType> = listOf()

}