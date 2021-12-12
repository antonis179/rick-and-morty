package com.amoustakos.rickandmorty.features.characters.domain

import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.DomainTransformer
import com.amoustakos.rickandmorty.data.domain.models.characters.CharacterStatus
import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.data.domain.models.characters.Location
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharactersResponse
import javax.inject.Inject


//TODO: use character transformer in loop
class CharactersDomainTransformer @Inject constructor() :
    DomainTransformer<ApiCharactersResponse, CharactersResponse, Unit?> {


    override fun transform(
        response: ApiCharactersResponse,
        data: Unit?
    ): DomainResponse<CharactersResponse> {
        return runCatching {
            DomainResponse.Success(
                CharactersResponse(
                    response.results?.mapNotNull {
                        DomainCharacter(
                            id = it!!.id!!,
                            type = it.type!!,
                            url = it.url!!,
                            status = CharacterStatus.fromApi(it.status!!),
                            image = it.image!!,
                            gender = it.gender!!,
                            species = it.species!!,
                            created = it.created!!,
                            origin = Location(
                                name = it.origin!!.name!!,
                                url = it.origin.url!!
                            ),
                            name = it.name!!,
                            location = Location(
                                name = it.location!!.name!!,
                                url = it.location.url!!
                            ),
                            episode = it.episode?.filterNotNull() ?: listOf()
                        )
                    } ?: emptyList()
                )
            )
        }.getOrElse {
            DomainResponse.Error.UnknownError(it)
        }
    }

}