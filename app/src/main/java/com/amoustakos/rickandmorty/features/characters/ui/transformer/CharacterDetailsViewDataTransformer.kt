package com.amoustakos.rickandmorty.features.characters.ui.transformer

import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.features.characters.ui.views.CharacterDetailsViewData
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import javax.inject.Inject


class CharacterDetailsViewDataTransformer @Inject constructor() {

    fun transform(model: DomainCharacter): UiViewData {
        return with (model) {
            CharacterDetailsViewData(
                name = name,
                status = status,
                image = image,
                gender = gender,
                species = species,
                created = created,
                origin = origin,
                location = location
            )
        }
    }

}