package com.amoustakos.rickandmorty.data.network.api.models.request

import com.amoustakos.rickandmorty.data.cache.CacheKeyProvider


data class ApiFetchCharactersByIdsRequest(
    val ids: List<String>
) : CacheKeyProvider<String> {

    override fun cacheKey() ="$KEY_PREFIX/${ids.joinToString(",")}"

    companion object {
        private const val KEY_PREFIX = "characters_by_ids/"
    }
}
