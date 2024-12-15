package com.amoustakos.rickandmorty.data.network.api.models.request

import com.amoustakos.rickandmorty.data.cache.CacheKeyProvider


data class ApiFetchEpisodesRequest(
    val page: Int
) : CacheKeyProvider<String> {

    override fun cacheKey() = "${this::class.simpleName}/$page"

}
