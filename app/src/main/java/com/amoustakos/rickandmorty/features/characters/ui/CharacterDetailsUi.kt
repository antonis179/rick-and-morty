package com.amoustakos.rickandmorty.features.characters.ui

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
import com.amoustakos.rickandmorty.R
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.ui.views.GenericTextView
import com.amoustakos.rickandmorty.compose.ui.views.RemoteImageView
import com.amoustakos.rickandmorty.compose.ui.views.lists.LazyItem
import com.amoustakos.rickandmorty.features.characters.ui.CharacterDetailsUiState.State
import com.amoustakos.rickandmorty.features.common.views.bars.TitleOnly
import com.amoustakos.rickandmorty.features.common.views.errors.DefaultErrorView
import com.amoustakos.rickandmorty.features.common.views.loaders.DefaultFullPageLoader
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

class CharacterDetailsUi @Inject constructor() {

    @Composable
    fun View(uiState: CharacterDetailsUiState) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            when (val state = uiState.state) {
                State.None, State.Loading -> {
                    TitleOnly(stringResource(id = R.string.title_character))
                        .View(modifier = Modifier.fillMaxWidth())

                    DefaultFullPageLoader(Modifier.fillMaxSize())
                }

                State.Error -> {
                    TitleOnly(stringResource(id = R.string.title_character))
                        .View(modifier = Modifier.fillMaxWidth())

                    DefaultErrorView(Modifier.fillMaxSize())
                }

                is State.Data -> {

                    TitleOnly(state.name).View(modifier = Modifier.fillMaxWidth())

                    val views = persistentListOf(
                        GenericTextView(),
                        RemoteImageView()
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
class CharacterDetailsUiState() {
    var state by mutableStateOf<State>(State.None)

    sealed interface State {
        data object None : State
        data object Loading : State
        data object Error : State
        data class Data(
            val name: String,
            val viewData: PersistentList<ComposeViewData>
        ) : State
    }
}