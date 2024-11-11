package com.amoustakos.rickandmorty.features.characters.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.amoustakos.rickandmorty.R
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import com.amoustakos.rickandmorty.compose.ui.views.lists.LazyItem
import com.amoustakos.rickandmorty.features.characters.ui.CharacterListingUiEvent.OnCharacterClick
import com.amoustakos.rickandmorty.features.characters.ui.CharactersUiState.State
import com.amoustakos.rickandmorty.features.common.views.ImageListingView
import com.amoustakos.rickandmorty.features.common.views.bars.TitleOnly
import com.amoustakos.rickandmorty.features.common.views.errors.DefaultErrorView
import com.amoustakos.rickandmorty.features.common.views.loaders.DefaultFullPageLoader
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

class CharacterListingUi @Inject constructor() {

    @Composable
    fun View(
        nv: NavHostController,
        uiState: CharactersUiState,
        eventHandler: (CharacterListingUiEvent) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            TitleOnly(stringResource(id = R.string.title_characters))
                .View(modifier = Modifier.fillMaxWidth())

            when (val state = uiState.state) {
                State.None, State.Loading -> {
                    DefaultFullPageLoader(Modifier.fillMaxSize())
                }

                State.Error -> {
                    DefaultErrorView(Modifier.fillMaxSize())
                }

                is State.Data -> {
                    val views = persistentListOf(
                        ImageListingView(null) { id ->
                            eventHandler.invoke(
                                OnCharacterClick(
                                    nv,
                                    id
                                )
                            )
                        }
                    )

                    LazyColumn(Modifier.fillMaxSize()) {
                        items(
                            items = state.viewData,
                            contentType = { item -> item.getType() }
                        ) { item ->
                            LazyItem(views, item)
                        }
                    }
                }
            }
        }

    }
}

@Stable
class CharactersUiState() {
    var state by mutableStateOf<State>(State.None)

    sealed interface State {
        data object None : State
        data object Loading : State
        data object Error : State
        data class Data(val viewData: PersistentList<ComposeViewData>) : State
    }
}

sealed interface CharacterListingUiEvent {
    data class OnCharacterClick(
        val nv: NavHostController,
        val id: Int
    ) : CharacterListingUiEvent
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCharactersListingUiNight() = AppTheme {
    CharacterListingUi().View(
        rememberNavController(),
        CharactersUiState()
    ) {}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewCharactersListingUi() = AppTheme {
    CharacterListingUi().View(
        rememberNavController(),
        CharactersUiState()
    ) {}
}