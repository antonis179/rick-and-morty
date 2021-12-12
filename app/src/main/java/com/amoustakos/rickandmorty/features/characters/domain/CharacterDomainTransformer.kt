package com.amoustakos.rickandmorty.features.characters.domain

import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.DomainTransformer
import com.amoustakos.rickandmorty.data.domain.models.characters.CharacterStatus
import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.data.domain.models.characters.Location
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharacter
import javax.inject.Inject


class CharacterDomainTransformer @Inject constructor() :
    DomainTransformer<ApiCharacter, DomainCharacter, Unit?> {


    override fun transform(response: ApiCharacter, data: Unit?): DomainResponse<DomainCharacter> {
        try {
            return DomainResponse.Success(
                DomainCharacter(
                    id = response.id!!,
                    type = response.type!!,
                    url = response.url!!,
                    status = CharacterStatus.fromApi(response.status!!),
                    image = response.image!!,
                    gender = response.gender!!,
                    species = response.species!!,
                    created = response.created!!,
                    origin = Location(
                        name = response.origin!!.name!!,
                        url = response.origin.url!!
                    ),
                    name = response.name!!,
                    location = Location(
                        name = response.location!!.name!!,
                        url = response.location.url!!
                    ),
                    episode = response.episode?.filterNotNull() ?: listOf()
                )
            )
        } catch (ex: NullPointerException) {
            return DomainResponse.Error.UnknownError(ex)
        }
    }

}