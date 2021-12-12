package com.amoustakos.rickandmorty.data.network.api.models.request

import com.amoustakos.rickandmorty.data.cache.CacheKeyProvider


class ApiFetchCharactersRequest : CacheKeyProvider<String> {

    override fun cacheKey() = KEY_PREFIX

    companion object {
        private const val KEY_PREFIX = "characters/"
    }
}
