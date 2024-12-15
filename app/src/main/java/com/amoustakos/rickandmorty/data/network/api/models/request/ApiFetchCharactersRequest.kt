package com.amoustakos.rickandmorty.data.network.api.models.request

import com.amoustakos.rickandmorty.data.cache.CacheKeyProvider


class ApiFetchCharactersRequest : CacheKeyProvider<String> {

    override fun cacheKey() = this::class.simpleName ?: "ApiFetchCharactersRequest"

}
