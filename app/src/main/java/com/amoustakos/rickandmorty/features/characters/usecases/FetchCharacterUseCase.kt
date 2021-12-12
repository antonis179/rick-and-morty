package com.amoustakos.rickandmorty.features.characters.usecases

import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.DomainTransformer
import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.data.network.api.ApiErrorHandler
import com.amoustakos.rickandmorty.data.network.api.RickAndMortyDataProvider
import com.amoustakos.rickandmorty.data.network.api.models.request.ApiFetchCharacterRequest
import com.amoustakos.rickandmorty.data.network.api.models.request.ApiFetchCharactersByIdsRequest
import com.amoustakos.rickandmorty.data.network.api.models.request.ApiFetchCharactersRequest
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharacter
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharactersResponse
import com.amoustakos.rickandmorty.utils.DispatchersWrapper
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FetchCharacterUseCase @Inject constructor(
    private val dataProvider: RickAndMortyDataProvider,
    private val errorHandler: ApiErrorHandler<ApiCharacter, DomainCharacter>,
    private val charactersErrorHandler: ApiErrorHandler<ApiCharactersResponse, CharactersResponse>,
    private val domainTransformer: DomainTransformer<ApiCharacter, DomainCharacter, Unit?>,
    private val charactersDomainTransformer: DomainTransformer<ApiCharactersResponse, CharactersResponse, Unit?>,
    private val dispatchers: DispatchersWrapper
) {

    suspend fun fetchAll(): DomainResponse<CharactersResponse> {
        return withContext(dispatchers.io) {
            runCatching {
                val response = dataProvider.fetchCharacters(ApiFetchCharactersRequest())
                if (charactersErrorHandler.hasError(response)) {
                    charactersErrorHandler.handleError(response)
                } else {
                    withContext(dispatchers.default) {
                        response.body()?.let {
                            charactersDomainTransformer.transform(it, null)
                                ?: DomainResponse.Error.UnknownError()
                        } ?: DomainResponse.Error.UnknownError()
                    }
                }
            }.getOrElse {
                charactersErrorHandler.handleException(it)
            }
        }
    }

    suspend fun fetch(ids: List<String>): DomainResponse<CharactersResponse> {
        return withContext(dispatchers.io) {
            runCatching {
                val response = dataProvider.fetchCharacters(ApiFetchCharactersByIdsRequest(ids))
                if (charactersErrorHandler.hasError(response)) {
                    charactersErrorHandler.handleError(response)
                } else {
                    withContext(dispatchers.default) {
                        response.body()?.let {
                            charactersDomainTransformer.transform(it, null)
                                ?: DomainResponse.Error.UnknownError()
                        } ?: DomainResponse.Error.UnknownError()
                    }
                }
            }.getOrElse {
                charactersErrorHandler.handleException(it)
            }
        }
    }

    suspend fun fetch(id: Int): DomainResponse<DomainCharacter> {
        return withContext(dispatchers.io) {
            runCatching {
                val response = dataProvider.fetchCharacterById(ApiFetchCharacterRequest(id))
                if (errorHandler.hasError(response)) {
                    errorHandler.handleError(response)
                } else {
                    withContext(dispatchers.default) {
                        response.body()?.let {
                            domainTransformer.transform(it, null)
                                ?: DomainResponse.Error.UnknownError()
                        } ?: DomainResponse.Error.UnknownError()
                    }
                }
            }.getOrElse {
                errorHandler.handleException(it)
            }
        }
    }


}