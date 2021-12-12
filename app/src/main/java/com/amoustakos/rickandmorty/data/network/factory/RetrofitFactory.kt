package com.amoustakos.rickandmorty.data.network.factory

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File


/**
 * Factory class that provides [Retrofit], [OkHttpClient] instances.
 *
 * All options are routed through [RetrofitEngineOptions] and [OkhttpOptions] respectively.
 */
object RetrofitFactory {

    /**
     * Creates a [Retrofit] instance based on the provided [RetrofitEngineOptions].
     */
    @JvmStatic
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

    /**
     * Creates an [OkHttpClient] based on the provided [OkhttpOptions].
     * Also see: [setupCache], [setupInterceptors], [setupLogging], [setupTimeouts]
     */
    @JvmStatic
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


    /**
     * Sets up the interceptors
     */
    @Throws(IllegalArgumentException::class)
    private fun OkHttpClient.Builder.setupInterceptors(opts: OkhttpOptions) {
        opts.interceptors?.forEach {
            when {
                it.isInterceptor() -> addInterceptor(it.interceptor)
                it.isNetworkInterceptor() -> addNetworkInterceptor(it.interceptor)
                else -> throw IllegalArgumentException("Interceptor invalid: $it")
            }
        }
    }

    /**
     * Sets up the logging behaviour
     */
    private fun OkHttpClient.Builder.setupLogging(opts: OkhttpOptions) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = when {
                opts.logEnabled -> opts.logLevel
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

        addInterceptor(loggingInterceptor)
    }

    /**
     * Sets up the timeouts (read, write, connect)
     */
    private fun OkHttpClient.Builder.setupTimeouts(opts: OkhttpOptions) {
        connectTimeout(opts.connectTimeout, opts.timeUnit)
        readTimeout(opts.readTimeout, opts.timeUnit)
        writeTimeout(opts.writeTimeout, opts.timeUnit)
    }

    /**
     * Sets up the caching behaviour
     */
    private fun OkHttpClient.Builder.setupCache(opts: OkhttpOptions) {
        val cacheOptionsValid = opts.cacheSizeMb?.let { it > 0L } == true &&
                opts.cacheDir != null &&
                opts.cacheSubDirectory != null

        if (!cacheOptionsValid) return

        opts.cacheSubDirectory?.let { subDir ->
            val cacheSize: Long =
                opts.cacheSizeMb ?: throw NullPointerException("Cache size not specified")
            cache(
                Cache(
                    File(opts.cacheDir, subDir),
                    cacheSize
                )
            )
        }
    }

}