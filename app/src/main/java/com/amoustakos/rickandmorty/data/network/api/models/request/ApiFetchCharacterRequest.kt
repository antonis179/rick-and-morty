package com.amoustakos.rickandmorty.data.network.api.models.request

import com.amoustakos.rickandmorty.data.cache.CacheKeyProvider


data class ApiFetchCharacterRequest(
    val id: Int
) : CacheKeyProvider<String> {

    override fun cacheKey() = "$KEY_PREFIX$id"

    companion object {
        private const val KEY_PREFIX = "character/"
    }
}
