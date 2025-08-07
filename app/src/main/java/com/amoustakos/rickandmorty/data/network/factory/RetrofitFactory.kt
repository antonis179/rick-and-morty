package com.amoustakos.rickandmorty.data.network.factory

import com.amoustakos.rickandmorty.data.network.factory.InterceptorWithType.Interceptor
import com.amoustakos.rickandmorty.data.network.factory.InterceptorWithType.NetworkInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File


object RetrofitFactory {

    fun makeRetrofitEngine(okHttpClient: OkHttpClient, opts: RetrofitEngineOptions): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(opts.baseUrl)
            .client(okHttpClient)

        opts.adapterFactories
            .forEach { builder.addCallAdapterFactory(it) }
        opts.converterFactories
            .forEach { builder.addConverterFactory(it) }

        return builder.build()
    }

    @Throws(IllegalArgumentException::class)
    fun makeHttpClient(
        opts: OkhttpOptions,
        client: OkHttpClient.Builder = OkHttpClient.Builder()
    ): OkHttpClient {

        // Cache
        client.setupCache(opts)

        // Connection
        client.setupTimeouts(opts)

        // Logging
        client.setupLogging(opts)

        // Interceptors
        client.setupInterceptors(opts)

        opts.cookieJar?.let { client.cookieJar(it) }

        return client.build()
    }

    private fun OkHttpClient.Builder.setupInterceptors(opts: OkhttpOptions) {
        opts.interceptors?.forEach {
            when(it) {
                is Interceptor -> addInterceptor(it.interceptor)
                is NetworkInterceptor -> addNetworkInterceptor(it.interceptor)
            }
        }
    }

    private fun OkHttpClient.Builder.setupLogging(opts: OkhttpOptions) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = when {
                opts.logEnabled -> opts.logLevel
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

        addInterceptor(loggingInterceptor)
    }

    private fun OkHttpClient.Builder.setupTimeouts(opts: OkhttpOptions) {
        connectTimeout(opts.connectTimeout, opts.timeUnit)
        readTimeout(opts.readTimeout, opts.timeUnit)
        writeTimeout(opts.writeTimeout, opts.timeUnit)
    }

    private fun OkHttpClient.Builder.setupCache(opts: OkhttpOptions) {
        val cacheOptionsValid = opts.cacheSizeMb?.let { it > 0L } == true &&
                opts.cacheDir != null &&
                opts.cacheSubDirectory != null

        if (!cacheOptionsValid) return

        val cacheSize: Long = opts.cacheSizeMb ?: 0L
        cache(
            Cache(
                File(opts.cacheDir, opts.cacheSubDirectory ?: ""),
                cacheSize
            )
        )
    }

}