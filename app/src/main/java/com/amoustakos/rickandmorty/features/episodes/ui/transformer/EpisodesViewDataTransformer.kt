package com.amoustakos.rickandmorty.features.episodes.ui.transformer

import androidx.compose.ui.unit.dp
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerPadding
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.features.common.views.ImageListingViewData
import com.amoustakos.rickandmorty.ui.transformers.ViewDataTransformer
import javax.inject.Inject

class EpisodesViewDataTransformer @Inject constructor() : ViewDataTransformer<EpisodesResponse> {

    override fun transform(model: EpisodesResponse): List<ComposeViewData> {
        return model.episodes.map {
            ImageListingViewData(
                containerParams = ContainerParams(
                    outerPadding = ContainerPadding(horizontal = 16.dp, vertical = 8.dp)
                ),
                id = it.id,
                icon = it.image ?: "",
                title = it.name,
                description = it.episode
            )
        }
    }

}