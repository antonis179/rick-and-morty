package com.amoustakos.rickandmorty.data.network.api.models.request

import com.amoustakos.rickandmorty.data.cache.CacheKeyProvider


data class ApiFetchCharactersByIdsRequest(
    val ids: String
) : CacheKeyProvider<String> {

    override fun cacheKey() ="${this::class.simpleName}/${ids}"

}
