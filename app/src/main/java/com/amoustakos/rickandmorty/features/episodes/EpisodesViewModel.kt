package com.amoustakos.rickandmorty.features.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.features.characters.usecases.FetchCharacterUseCase
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUi.EpisodesUiState
import com.amoustakos.rickandmorty.features.episodes.ui.transformer.EpisodesViewDataTransformer
import com.amoustakos.rickandmorty.features.episodes.usecases.FetchEpisodesUseCase
import com.amoustakos.rickandmorty.ui.UiState
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import com.amoustakos.rickandmorty.utils.DispatchersWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val fetchEpisodesUseCase: FetchEpisodesUseCase,
    private val fetchCharacterUseCase: FetchCharacterUseCase,
    private val transformer: EpisodesViewDataTransformer,
    private val dispatchersWrapper: DispatchersWrapper
) : ViewModel() {

    val uiState = EpisodesUiState(onPaginate = this::fetch)

    private fun fetch() {
        //TODO: Add error
        uiState.state = UiState.Loading
        val nextPage = uiState.nextPage + 1

        viewModelScope.launch(dispatchersWrapper.io) {
            val response = fetchEpisodesUseCase.fetch(nextPage)
            if (response !is DomainResponse.Success) {
                response.handleErrorResponse()
                return@launch
            }
            val data = response.body

            fetchCharacterImages(data)

            val viewData: List<UiViewData>
            withContext(dispatchersWrapper.default) {
                viewData = transformer.transform(data)
            }

            withContext(dispatchersWrapper.main) {
                uiState.nextPage = nextPage
                uiState.state = UiState.Idle
                uiState.canPaginate = data.page.availablePages > nextPage
                uiState.viewData += viewData
            }
        }
    }

    private suspend fun fetchCharacterImages(data: EpisodesResponse) {
        data.episodes.filter { it.characters.isNotEmpty() }.forEach { episode ->
            val index = Random.nextInt(0, episode.characters.size - 1)
            var character = episode.characters[index]
            character = character.substring(character.lastIndexOf("/") + 1)
            val characterResponse = fetchCharacterUseCase.fetch(character.toIntOrNull() ?: return)
            if (characterResponse is DomainResponse.Success) {
                episode.image = characterResponse.body.image
            }
        }
    }

    private fun DomainResponse<EpisodesResponse>.handleErrorResponse() {
        uiState.state = UiState.Error
        //TODO
    }

}