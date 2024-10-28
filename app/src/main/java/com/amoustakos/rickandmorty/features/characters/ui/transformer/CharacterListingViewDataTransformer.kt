package com.amoustakos.rickandmorty.features.characters.ui.transformer

import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.features.common.views.ImageListingViewData
import com.amoustakos.rickandmorty.ui.transformers.ViewDataTransformer
import javax.inject.Inject


class CharacterListingViewDataTransformer @Inject constructor() : ViewDataTransformer<CharactersResponse> {

    override fun transform(model: CharactersResponse): List<ComposeViewData> {
        return model.characters.map {
            ImageListingViewData(
                id = it.id,
                icon = it.image,
                title = it.name,
                description = it.type
            )
        }
    }

}