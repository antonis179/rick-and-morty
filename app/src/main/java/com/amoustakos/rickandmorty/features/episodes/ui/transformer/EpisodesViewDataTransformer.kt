package com.amoustakos.rickandmorty.features.episodes.ui.transformer

import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.features.common.views.ImageListingViewData
import com.amoustakos.rickandmorty.ui.transformers.ViewDataTransformer
import javax.inject.Inject

class EpisodesViewDataTransformer @Inject constructor() : ViewDataTransformer<EpisodesResponse> {

    override fun transform(model: EpisodesResponse): List<ComposeViewData> {
        return model.episodes.map {
            ImageListingViewData(
                id = it.id,
                icon = it.image ?: "",
                title = it.name,
                description = it.episode
            )
        }
    }

}