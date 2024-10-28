package com.amoustakos.rickandmorty.features.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.features.characters.navigation.CharacterListingScreen
import com.amoustakos.rickandmorty.features.characters.usecases.FetchCharacterUseCase
import com.amoustakos.rickandmorty.features.episodes.navigation.EpisodesScreen
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUiEvent
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUiState
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUiState.State
import com.amoustakos.rickandmorty.features.episodes.usecases.FetchEpisodesUseCase
import com.amoustakos.rickandmorty.navigation.PopRouteData
import com.amoustakos.rickandmorty.navigation.navigateSingleTop
import com.amoustakos.rickandmorty.ui.transformers.ViewDataTransformer
import com.amoustakos.rickandmorty.utils.DispatchersWrapper
import com.amoustakos.rickandmorty.utils.updateInMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random


typealias EpisodeId = Int

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val fetchEpisodesUseCase: FetchEpisodesUseCase,
    private val fetchCharacterUseCase: FetchCharacterUseCase,
    private val transformer: ViewDataTransformer<EpisodesResponse>,
    private val dispatchers: DispatchersWrapper
) : ViewModel() {

    val uiState = EpisodesUiState(onPaginate = this::fetch)
    val characterMappings = mutableMapOf<EpisodeId, List<String>>()

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(dispatchers.io) {
            val nextPage = nextPage()
            setLoadingState()

            val response = fetchEpisodesUseCase.fetch(nextPage)
            if (response !is DomainResponse.Success) {
                response.handleErrorResponse()
                return@launch
            }
            val data = response.body
            storeCharacterMappings(data)

            fetchCharacterImages(data)

            val viewData: List<ComposeViewData>
            withContext(dispatchers.default) {
                viewData = transformer.transform(data)
            }

            updateInMain(dispatchers) {
                if (!uiState.state.isDataState()) {
                    uiState.state = State.Data()
                }
                var dataState = uiState.state as State.Data

                dataState.apply {
                    this.nextPage = nextPage
                    canPaginate = data.page.availablePages > nextPage
                    this.viewData += viewData
                    isLoadingNextPage = false
                }
            }
        }
    }

    fun storeCharacterMappings(model: EpisodesResponse) {
        model.episodes.forEach { episode ->
            characterMappings[episode.id] = episode.characters.mapNotNull { id ->
                runCatching {
                    id.substring(id.lastIndexOf("/") + 1).trim()
                }.getOrNull()
            }
        }
    }

    fun handleEvent(event: EpisodesUiEvent) {
        when (event) {
            is EpisodesUiEvent.OnEpisodeClick -> {
                val id = event.id
                val characters = characterMappings[id] ?: return

                event.nv.navigateSingleTop(
                    CharacterListingScreen(characters),
                    PopRouteData(
                        dest = EpisodesScreen,
                        inclusive = false,
                        saveState = true
                    )
                )
            }
        }
    }

    private fun State.isDataState() = this is State.Data

    private suspend fun setLoadingState() = updateInMain(dispatchers) {
        if (uiState.state.isDataState()) {
            (uiState.state as State.Data).isLoadingNextPage = true
        } else {
            uiState.state = State.Loading
        }
    }

    private fun nextPage() = if (uiState.state.isDataState()) {
        (uiState.state as State.Data).nextPage + 1
    } else {
        1
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

    private suspend fun DomainResponse<EpisodesResponse>.handleErrorResponse() {
        updateInMain(dispatchers) { uiState.state = State.Error }
    }

}