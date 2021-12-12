package com.amoustakos.rickandmorty.data.cache


interface CacheProvider {

    fun <T> get(keyProvider: CacheKeyProvider<*>): T

    fun store(keyProvider: CacheKeyProvider<*>, data: Any?): Boolean
}

