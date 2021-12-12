package com.amoustakos.rickandmorty.ui


sealed interface UiState {

    object Init : UiState
    object Idle : UiState
    object Loading : UiState
    object Error : UiState

}
