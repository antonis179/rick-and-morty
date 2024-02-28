package com.amoustakos.rickandmorty.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.features.characters.navigation.CharacterDetailsScreen
import com.amoustakos.rickandmorty.features.characters.ui.CharacterDetailsUi.CharacterDetailsUiState
import com.amoustakos.rickandmorty.features.characters.ui.transformer.CharacterDetailsViewDataTransformer
import com.amoustakos.rickandmorty.features.characters.usecases.FetchCharacterUseCase
import com.amoustakos.rickandmorty.ui.UiState
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import com.amoustakos.rickandmorty.utils.DispatchersWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val fetchCharacterUseCase: FetchCharacterUseCase,
    private val transformer: CharacterDetailsViewDataTransformer,
    private val dispatchersWrapper: DispatchersWrapper
) : ViewModel() {

    val uiState = CharacterDetailsUiState(this::fetch)
    private var id: Int = -1

    fun setup(args: CharacterDetailsScreen.CharacterDetailsArgs) {
        id = args.id
    }

    private fun fetch() {
        uiState.state = UiState.Loading
        viewModelScope.launch(dispatchersWrapper.io) {
            if (id == -1) {
                //TODO: error
                return@launch
            }
            val response = fetchCharacterUseCase.fetch(id)
            if (response !is DomainResponse.Success) {
                response.handleErrorResponse()
                return@launch
            }
            val data = response.body

            val viewData: UiViewData
            withContext(dispatchersWrapper.default) {
                viewData = transformer.transform(data)
            }

            withContext(dispatchersWrapper.main) {
                uiState.viewData = viewData
                uiState.state = UiState.Idle
            }
        }
    }


    private fun DomainResponse<DomainCharacter>.handleErrorResponse() {
        uiState.state = UiState.Error
        //TODO
    }

}