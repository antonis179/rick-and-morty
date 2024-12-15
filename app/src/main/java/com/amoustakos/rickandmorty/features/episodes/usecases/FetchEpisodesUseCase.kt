package com.amoustakos.rickandmorty.features.episodes.usecases

import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.DomainResponse.Error
import com.amoustakos.rickandmorty.data.domain.DomainTransformer
import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.data.network.api.ApiErrorHandler
import com.amoustakos.rickandmorty.data.network.api.RickAndMortyDataProvider
import com.amoustakos.rickandmorty.data.network.api.models.request.ApiFetchEpisodesRequest
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiEpisodesResponse
import com.amoustakos.rickandmorty.utils.DispatcherProvider
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface FetchEpisodesUseCase {
    suspend fun fetch(page: Int): DomainResponse<EpisodesResponse>
}

class FetchEpisodesUseCaseImpl @Inject constructor(
    private val dataProvider: RickAndMortyDataProvider,
    private val errorHandler: ApiErrorHandler<ApiEpisodesResponse, EpisodesResponse>,
    private val domainTransformer: DomainTransformer<ApiEpisodesResponse, EpisodesResponse>,
    private val dispatchers: DispatcherProvider
): FetchEpisodesUseCase {

    override suspend fun fetch(page: Int): DomainResponse<EpisodesResponse> {
        return withContext(dispatchers.io) {
            runCatching {
                val response = dataProvider.fetchEpisodes(ApiFetchEpisodesRequest(page))
                if (errorHandler.hasError(response)) {
                    errorHandler.handleError(response)
                } else {
                    withContext(dispatchers.default) {
                        response.body()?.let {
                            domainTransformer.transform(it) ?: Error.TransformationError()
                        } ?: Error.UnknownError()
                    }
                }
            }.getOrElse {
                ensureActive()
                errorHandler.handleException(it)
            }
        }
    }

}