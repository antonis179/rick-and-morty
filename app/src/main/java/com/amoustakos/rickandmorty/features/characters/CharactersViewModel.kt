package com.amoustakos.rickandmorty.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoustakos.rickandmorty.data.domain.DomainResponse
import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.features.characters.ui.CharactersListingUi.CharactersUiState
import com.amoustakos.rickandmorty.features.characters.ui.transformer.CharacterListingViewDataTransformer
import com.amoustakos.rickandmorty.features.characters.usecases.FetchCharacterUseCase
import com.amoustakos.rickandmorty.navigation.screens.Characters
import com.amoustakos.rickandmorty.ui.UiState
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import com.amoustakos.rickandmorty.utils.DispatchersWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val fetchCharacterUseCase: FetchCharacterUseCase,
    private val transformer: CharacterListingViewDataTransformer,
    private val dispatchersWrapper: DispatchersWrapper
) : ViewModel() {

    val uiState = CharactersUiState(this::fetch)
    private val characterIds: MutableList<String> = mutableListOf()

    fun setup(args: Characters.CharacterArgs) {
        characterIds.clear()
        characterIds += args.ids
    }

    private fun fetch() {
        uiState.state = UiState.Loading
        viewModelScope.launch(dispatchersWrapper.io) {
            val response =
                if (characterIds.isEmpty())
                    fetchCharacterUseCase.fetchAll()
                else
                    fetchCharacterUseCase.fetch(characterIds)
            if (response !is DomainResponse.Success) {
                response.handleErrorResponse()
                return@launch
            }
            val data = response.body

            val viewData: List<UiViewData>
            withContext(dispatchersWrapper.default) {
                viewData = transformer.transform(data)
            }

            withContext(dispatchersWrapper.main) {
                uiState.viewData = viewData
                uiState.state = UiState.Idle
            }
        }
    }


    private fun DomainResponse<CharactersResponse>.handleErrorResponse() {
        uiState.state = UiState.Error
        //TODO
    }

}