package com.amoustakos.rickandmorty.data.cache


interface CacheKeyProvider<Key> {

    fun cacheKey(): Key

    fun cacheTime(): Int = 5 * 60

}