package com.amoustakos.rickandmorty.data.cache

import com.amoustakos.rickandmorty.utils.DispatcherProvider
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RamCacheProvider @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : CacheProvider {

    @Suppress("UNCHECKED_CAST")
    @Throws
    override fun <T> get(keyProvider: CacheKeyProvider<*>): T? {
        val key = keyProvider.cacheKey() ?: throw IllegalArgumentException("Key cannot be null")
        val result = cache[key]

        return runCatching {
            result as T
        }.getOrNull()
    }

    override fun store(keyProvider: CacheKeyProvider<*>, data: Any?): Boolean {
        val key = keyProvider.cacheKey() ?: return false
        val obj = TimedData(
            dispatcherProvider,
            System.currentTimeMillis(),
            data
        )
        cache[key] = obj
        obj.initTimer(keyProvider.cacheTime()) { cache.remove(key) }
        return true
    }

    companion object {
        private val cache: MutableMap<Any, TimedData<Any?>> = mutableMapOf()
    }
}