package com.amoustakos.rickandmorty.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.features.characters.ui.CharacterDetailsUiState
import com.amoustakos.rickandmorty.features.characters.ui.CharacterDetailsUiState.State
import com.amoustakos.rickandmorty.features.characters.usecases.FetchCharacterUseCase
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


@HiltViewModel(assistedFactory = CharacterDetailsViewModelFactory::class)
class CharacterDetailsViewModel @AssistedInject constructor(
    private val fetchCharacterUseCase: FetchCharacterUseCase,
    private val transformer: ViewDataTransformer<DomainCharacter>,
    private val dispatchers: DispatcherProvider,
    @Assisted private val id: Int
) : ViewModel() {

    val uiState = CharacterDetailsUiState()

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(dispatchers.io) {
            updateInMain(dispatchers) { uiState.state = State.Loading }

            if (id == -1) {
                handleErrorResponse()
                return@launch
            }

            val response = fetchCharacterUseCase.fetch(id)
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
                uiState.state = State.Data(
                    name = data.name,
                    viewData = viewData
                )
            }
        }
    }

    private suspend fun handleErrorResponse() {
        updateInMain(dispatchers) { uiState.state = State.Error }
    }

}

@AssistedFactory
interface CharacterDetailsViewModelFactory {
    fun create(id: Int): CharacterDetailsViewModel
}