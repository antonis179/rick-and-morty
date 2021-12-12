package com.amoustakos.rickandmorty.features.episodes.domain

import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.DomainTransformer
import com.amoustakos.rickandmorty.data.domain.models.episodes.Episode
import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.data.domain.models.episodes.Page
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiEpisodesResponse
import javax.inject.Inject


class EpisodesDomainTransformer @Inject constructor() :
    DomainTransformer<ApiEpisodesResponse, EpisodesResponse, Int> {


    override fun transform(response: ApiEpisodesResponse, data: Int): DomainResponse<EpisodesResponse>? {
        response.info ?: return null
        response.results ?: return null

        val episodes: MutableList<Episode> = mutableListOf()
        val availablePages = response.info.pages ?: 0

        response.results.filterNotNull().forEach {
            episodes += Episode(
                it.airDate ?: "",
                it.characters?.filterNotNull() ?: listOf(),
                it.created ?: "",
                it.name ?: "",
                it.episode ?: "",
                it.id ?: return@forEach,
                it.url ?: return@forEach
            )
        }

        val domainData = EpisodesResponse(Page(data, availablePages), episodes)
        return DomainResponse.Success(domainData)
    }

}