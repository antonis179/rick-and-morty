package com.amoustakos.rickandmorty.features.characters.ui.transformer

import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.features.characters.ui.views.CharacterListingViewData
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import javax.inject.Inject


class CharacterListingViewDataTransformer @Inject constructor() {

    fun transform(model: CharactersResponse): List<UiViewData> {
        return model.characters.map {
            CharacterListingViewData(
                it.id,
                it.image,
                it.name,
                it.type
            )
        }
    }

}