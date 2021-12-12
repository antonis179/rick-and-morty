package com.amoustakos.rickandmorty.features.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.amoustakos.rickandmorty.NavGraph
import com.amoustakos.rickandmorty.R
import com.amoustakos.rickandmorty.features.characters.ui.views.CharacterDetailsView
import com.amoustakos.rickandmorty.features.characters.ui.views.CharacterDetailsViewData
import com.amoustakos.rickandmorty.ui.UiState
import com.amoustakos.rickandmorty.ui.bars.TitleOnly
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import com.amoustakos.rickandmorty.ui.loaders.DefaultFullPageLoadingIndicator
import javax.inject.Inject

class CharacterDetailsUi @Inject constructor(
    private val navGraph: NavGraph
) {

    val characterView = CharacterDetailsView()

    @Composable
    fun View(
        nv: NavHostController,
        state: CharacterDetailsUiState
    ) {

        if (state.state == UiState.Init) {
            state.fetch()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            when (state.state) {
                UiState.Loading -> {
                    TitleOnly(stringResource(id = R.string.title_character))
                        .View(modifier = Modifier.fillMaxWidth())

                    DefaultFullPageLoadingIndicator()
                }

                UiState.Error -> TODO()
                UiState.Idle ->  {
                    val data = state.viewData ?: run {
                        //TODO: Error
                    }

                    if (data !is CharacterDetailsViewData) {
                        //TODO: error
                        return@Column
                    }

                    TitleOnly(data.name).View(modifier = Modifier.fillMaxWidth())

                    characterView.View(position = 0, data = data)
                }
                else -> {
                    TitleOnly(stringResource(id = R.string.title_character))
                        .View(modifier = Modifier.fillMaxWidth())
                }
            }
        }

    }

    @Stable
    class CharacterDetailsUiState(
        val fetch: () -> Unit = {}
    ) {
        var state by mutableStateOf<UiState>(UiState.Init)
        var viewData: UiViewData? by mutableStateOf(null)
    }

    companion object {
        @Composable
        fun makeModel(): CharacterDetailsViewModel = hiltViewModel()
    }
}

