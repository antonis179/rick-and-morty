package com.amoustakos.rickandmorty.features.characters.ui.transformer

import androidx.compose.ui.unit.dp
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerPadding
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.features.common.views.ImageListingViewData
import com.amoustakos.rickandmorty.ui.transformers.ViewDataTransformer
import javax.inject.Inject


class CharacterListingViewDataTransformer @Inject constructor() : ViewDataTransformer<CharactersResponse> {

    override fun transform(model: CharactersResponse): List<ComposeViewData> {
        return model.characters.map {
            ImageListingViewData(
                containerParams = ContainerParams(
                    outerPadding = ContainerPadding(horizontal = 16.dp, vertical = 8.dp)
                ),
                id = it.id,
                icon = it.image,
                title = it.name,
                description = it.type
            )
        }
    }

}