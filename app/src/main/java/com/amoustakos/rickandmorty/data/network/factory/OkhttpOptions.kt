package com.amoustakos.rickandmorty.data.network.factory

import androidx.annotation.IntDef
import androidx.annotation.Keep
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Data class used to define options for OkHttp client creation
 */
@Keep
data class OkhttpOptions(
        // Debug
    val logEnabled: Boolean, // wether logging is enabled
    val logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY, // Logging level

        // Caching - default or invalid values will result in no caching
    val cacheSizeMb: Long? = null,
    val cacheDir: File? = null,
    val cacheSubDirectory: String? = null,

        // Connection
    val connectTimeout: Long, // A connection attempt will fail if it takes this long to complete
    val readTimeout: Long, // An endpoint request will fail if the server does not reply in this time
    val writeTimeout: Long, // An endpoint request will fail if the client does not manage to send the data in this time
    val timeUnit: TimeUnit, // Unit of measurement for all timeout values

        // Interceptors
    val interceptors: List<InterceptorWithType>? = null,

    val cookieJar: CookieJar? = null
)

/**
 * TODO: convert to sealed class
 * Interceptor with type.
 *
 * The type is used to define the way an interceptor is added
 */
@Keep
data class InterceptorWithType(
    @InterceptorType val type: Int,
    val interceptor: Interceptor
) {
    fun isInterceptor() = type == INTERCEPTOR
    fun isNetworkInterceptor() = type == NETWORK_INTERCEPTOR

    @IntDef(INTERCEPTOR, NETWORK_INTERCEPTOR)
    @Retention(AnnotationRetention.SOURCE)
    annotation class InterceptorType

    companion object {
        const val INTERCEPTOR = 0
        const val NETWORK_INTERCEPTOR = 1
    }

}