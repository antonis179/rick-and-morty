package com.amoustakos.rickandmorty.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.features.characters.navigation.CharacterDetailsScreen
import com.amoustakos.rickandmorty.features.characters.navigation.CharacterListingScreen
import com.amoustakos.rickandmorty.features.characters.ui.CharacterListingUiEvent
import com.amoustakos.rickandmorty.features.characters.ui.CharactersUiState
import com.amoustakos.rickandmorty.features.characters.ui.CharactersUiState.State
import com.amoustakos.rickandmorty.features.characters.usecases.FetchCharacterUseCase
import com.amoustakos.rickandmorty.navigation.PopRouteData
import com.amoustakos.rickandmorty.navigation.navigateSingleTop
import com.amoustakos.rickandmorty.ui.transformers.ViewDataTransformer
import com.amoustakos.rickandmorty.utils.DispatcherProvider
import com.amoustakos.rickandmorty.utils.updateInMain
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@HiltViewModel(assistedFactory = CharactersViewModelFactory::class)
class CharacterListingViewModel @AssistedInject constructor(
    private val fetchCharacterUseCase: FetchCharacterUseCase,
    private val transformer: ViewDataTransformer<CharactersResponse>,
    private val dispatchers: DispatcherProvider,
    @Assisted private val characterIds: List<String>
) : ViewModel() {

    val uiState = CharactersUiState()

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(dispatchers.io) {
            updateInMain(dispatchers) { uiState.state = State.Loading }

            val response =
                if (characterIds.isEmpty())
                    fetchCharacterUseCase.fetchAll()
                else
                    fetchCharacterUseCase.fetch(characterIds)

            if (response !is DomainResponse.Success) {
                handleErrorResponse()
                return@launch
            }

            val data = response.body

            val viewData: PersistentList<ComposeViewData>
            withContext(dispatchers.default) {
                viewData = transformer.transform(data).toPersistentList()
            }

            updateInMain(dispatchers) {
                uiState.state = State.Data(viewData)
            }
        }
    }

    fun handleEvent(event: CharacterListingUiEvent) {
        when (event) {
            is CharacterListingUiEvent.OnCharacterClick -> {
                event.nv.navigateSingleTop(
                    CharacterDetailsScreen(event.id),
                    PopRouteData(
                        dest = CharacterListingScreen(ids = characterIds),
                        inclusive = false,
                        saveState = true
                    )
                )
            }
        }
    }


    private suspend fun handleErrorResponse() {
        updateInMain(dispatchers) { uiState.state = State.Error }
    }

}

@AssistedFactory
interface CharactersViewModelFactory {
    fun create(characterIds: List<String>): CharacterListingViewModel
}