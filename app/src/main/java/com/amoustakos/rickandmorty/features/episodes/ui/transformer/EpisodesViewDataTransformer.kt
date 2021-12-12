package com.amoustakos.rickandmorty.features.episodes.ui.transformer

import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.features.episodes.ui.views.EpisodeListingViewData
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import javax.inject.Inject

class EpisodesViewDataTransformer @Inject constructor() {

    fun transform(model: EpisodesResponse): List<UiViewData> {
        return model.episodes.map {
            EpisodeListingViewData(
                it.id,
                it.image ?: "",
                it.name,
                it.episode,
                it.characters.mapNotNull { id ->
                    runCatching {
                        id.substring(id.lastIndexOf("/") + 1).trim()
                    }.getOrNull()
                }
            )
        }
    }

}