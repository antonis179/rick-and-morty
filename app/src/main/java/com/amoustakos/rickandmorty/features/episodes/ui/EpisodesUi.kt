package com.amoustakos.rickandmorty.features.episodes.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.amoustakos.rickandmorty.features.episodes.EpisodesViewModel
import com.amoustakos.rickandmorty.features.episodes.ui.views.EpisodeListingView
import com.amoustakos.rickandmorty.features.episodes.ui.views.EpisodeListingViewData
import com.amoustakos.rickandmorty.navigation.screens.CharacterDetails
import com.amoustakos.rickandmorty.ui.UiState
import com.amoustakos.rickandmorty.ui.bars.TitleOnly
import com.amoustakos.rickandmorty.ui.lazy.SlideInBottomAnimator
import com.amoustakos.rickandmorty.ui.lazy.UiView
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import com.amoustakos.rickandmorty.ui.loaders.DefaultListLoadingIndicator
import com.amoustakos.rickandmorty.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber
import javax.inject.Inject


class EpisodesUi @Inject constructor(
    private val navGraph: NavGraph,
    private val enterAnimator: SlideInBottomAnimator
) {

    // Todo: Add swipe to refresh

    @Composable
    fun View(
        nv: NavHostController,
        model: EpisodesUiState
    ) {
        val onEpisodeClick = { ids: List<String> ->
            navGraph.navigateToCharacters(
                nv,
                ids,
                PopRouteData(CharacterDetails.route, true)
            )
        }

        val views: ImmutableList<UiView> = listOf(
            EpisodeListingView(enterAnimator, onEpisodeClick)
        ).toImmutableList()

        val uiState = model.state
        val listState = rememberLazyListState()

        val shouldStartPaginate = remember {
            derivedStateOf {
                model.canPaginate
                        && uiState == UiState.Idle
                        && lastItemsVisible(listState, model)
            }
        }

        LaunchedEffect(key1 = shouldStartPaginate.value) {
            if (shouldStartPaginate.value)
                model.onPaginate()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            TitleOnly(stringResource(id = R.string.title_episodes))
                .View(modifier = Modifier.fillMaxWidth())

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                itemsIndexed(
                    model.viewData,
                    contentType = { _, item -> item.getType() }
                ) { index, item ->
                    Item(index, item, views)
                }
                if (model.canPaginate || uiState == UiState.Loading) {
                    item { DefaultListLoadingIndicator() }
                }
            }
        }


    }

    private fun lastItemsVisible(listState: LazyListState, state: EpisodesUiState) =
        (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            ?: 0) >= (listState.layoutInfo.totalItemsCount - state.paginateOn)

    @Composable
    private fun Item(
        index: Int,
        item: UiViewData,
        views: List<UiView>
    ) {
        views.find { it.isValidForView(item) }?.View(index, item)
            ?: Timber.e(
                IllegalArgumentException("Tried to parse invalid UiViewData: $item")
            )
    }

    @Stable
    class EpisodesUiState(
        val paginateOn: Int = 6, // Start paginating on last X items shown
        val onPaginate: () -> Unit = {}
    ) {
        var state by mutableStateOf<UiState>(UiState.Idle)
        var canPaginate by mutableStateOf(true)
        var nextPage by mutableStateOf(0)
        var viewData by mutableStateOf(listOf<UiViewData>())
    }

    companion object {
        @Composable
        fun makeModel(): EpisodesViewModel = hiltViewModel()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun EpisodesUiPreview() = AppTheme {
    EpisodesUi(NavGraph(), SlideInBottomAnimator()).View(
        rememberNavController(),
        makeState()
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EpisodesUiPreviewNight() = AppTheme {
    EpisodesUi(NavGraph(), SlideInBottomAnimator()).View(
        rememberNavController(),
        makeState()
    )
}

private fun makeState() = EpisodesUi.EpisodesUiState().apply {
    viewData = listOf(
        EpisodeListingViewData(
            0,
            "http://clipart-library.com/data_images/6103.png",
            "Title",
            "S01E01",
            emptyList()
        )
    )
}

