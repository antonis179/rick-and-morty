package com.amoustakos.rickandmorty.features.characters

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.amoustakos.rickandmorty.NavGraph
import com.amoustakos.rickandmorty.PopRouteData
import com.amoustakos.rickandmorty.R
import com.amoustakos.rickandmorty.features.characters.ui.views.CharacterListingView
import com.amoustakos.rickandmorty.navigation.screens.Characters
import com.amoustakos.rickandmorty.ui.UiState
import com.amoustakos.rickandmorty.ui.bars.TitleOnly
import com.amoustakos.rickandmorty.ui.lazy.UiView
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import com.amoustakos.rickandmorty.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber
import javax.inject.Inject


class CharactersListingUi @Inject constructor(
    private val navGraph: NavGraph
) {

    @Composable
    fun View(
        nv: NavHostController,
        state: CharactersUiState
    ) {
        val onCharacterClick = { id: Int ->
            navGraph.navigateToCharacterDetails(nv, id, PopRouteData(Characters.route, false))
        }

        val views: ImmutableList<UiView> = listOf(
            CharacterListingView(onCharacterClick)
        ).toImmutableList()

        if (state.state == UiState.Init) {
            state.fetch()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            TitleOnly(stringResource(id = R.string.title_characters))
                .View(modifier = Modifier.fillMaxWidth())

            LazyColumn(
                Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(
                    state.viewData,
                    contentType = { _, item -> item.getType() }
                ) { index, item ->
                    Item(index, views, item)
                }
            }
        }

    }

    @Composable
    private fun Item(
        index: Int,
        views: List<UiView>,
        item: UiViewData
    ) {
        views.find { it.isValidForView(item) }?.View(index, item)
            ?: Timber.e(
                IllegalArgumentException("Tried to parse invalid UiViewData: $item")
            )
    }

    @Stable
    class CharactersUiState(
        val fetch: () -> Unit = {}
    ) {
        var state by mutableStateOf<UiState>(UiState.Init)
        var viewData: List<UiViewData> by mutableStateOf(listOf())
    }

    companion object {
        @Composable
        fun makeModel(): CharactersViewModel = hiltViewModel()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCharactersListingUiNight() = AppTheme {
    CharactersListingUi(NavGraph()).View(
        rememberNavController(),
        CharactersListingUi.CharactersUiState()
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewCharactersListingUi() = AppTheme {
    CharactersListingUi(NavGraph()).View(
        rememberNavController(),
        CharactersListingUi.CharactersUiState()
    )
}


