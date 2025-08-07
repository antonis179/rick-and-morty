package com.amoustakos.rickandmorty.data.network.factory

import androidx.annotation.Keep
import okhttp3.CookieJar
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

@Keep
data class OkhttpOptions(
    // Debug
    val logEnabled: Boolean,
    val logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,

    // Caching - default or invalid values will result in no caching
    val cacheSizeMb: Long? = null,
    val cacheDir: File? = null,
    val cacheSubDirectory: String? = null,

    // Connection
    val connectTimeout: Long,
    val readTimeout: Long,
    val writeTimeout: Long,
    val timeUnit: TimeUnit,

    // Interceptors
    val interceptors: List<InterceptorWithType>? = null,

    val cookieJar: CookieJar? = null
)

@Keep
sealed class InterceptorWithType(
    val interceptor: okhttp3.Interceptor
) {
    data class Interceptor(val value: okhttp3.Interceptor) : InterceptorWithType(value)
    data class NetworkInterceptor(val value: okhttp3.Interceptor) : InterceptorWithType(value)
}