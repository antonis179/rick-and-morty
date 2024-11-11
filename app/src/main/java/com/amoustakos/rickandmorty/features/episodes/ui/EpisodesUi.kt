package com.amoustakos.rickandmorty.features.episodes.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.amoustakos.rickandmorty.R
import com.amoustakos.rickandmorty.compose.animation.list_enter.AnimatorEnter
import com.amoustakos.rickandmorty.compose.animation.list_enter.NoopEnterAnimator
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerPadding
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import com.amoustakos.rickandmorty.compose.ui.views.lists.PaginatedLazyColumn
import com.amoustakos.rickandmorty.features.common.views.ImageListingView
import com.amoustakos.rickandmorty.features.common.views.ImageListingViewData
import com.amoustakos.rickandmorty.features.common.views.bars.TitleOnly
import com.amoustakos.rickandmorty.features.common.views.errors.DefaultErrorView
import com.amoustakos.rickandmorty.features.common.views.loaders.DefaultFullPageLoader
import com.amoustakos.rickandmorty.features.common.views.loaders.DefaultListLoader
import com.amoustakos.rickandmorty.features.episodes.di.EpisodesEnterAnimation
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUiEvent.OnEpisodeClick
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUiState.State
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject


class EpisodesUi @Inject constructor(
    @EpisodesEnterAnimation private val enterAnimator: AnimatorEnter
) {

    @Composable
    fun View(
        nv: NavHostController,
        model: EpisodesUiState,
        eventHandler: (event: EpisodesUiEvent) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            TitleOnly(stringResource(id = R.string.title_episodes))
                .View(modifier = Modifier.fillMaxWidth())

            when (model.state) {
                State.Error -> {
                    DefaultErrorView(Modifier.fillMaxSize())
                }

                State.None, State.Loading -> {
                    DefaultFullPageLoader(Modifier.fillMaxSize())
                }

                is State.Data -> {
                    MainView(nv, model, eventHandler)
                }
            }
        }
    }

    @Composable
    private fun MainView(
        nv: NavHostController,
        model: EpisodesUiState,
        eventHandler: (event: EpisodesUiEvent) -> Unit
    ) {
        val uiState = model.state as State.Data

        val views = persistentListOf(
            ImageListingView(enterAnimator) { id -> eventHandler.invoke(OnEpisodeClick(nv, id)) }
        )

        PaginatedLazyColumn(
            modifier = Modifier.fillMaxSize(),
            loading = uiState.isLoadingNextPage,
            useItemKeys = false,
            paginateOffset = model.paginateOffset,
            views = views,
            items = uiState.viewData,
            loadingItem = {
                DefaultListLoader(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp,
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 24.dp
                        )
                )
            },
            loadMore = {
                if (uiState.canPaginate && !uiState.isLoadingNextPage)
                    model.onPaginate()
            },
            endSpacer = { Spacer(modifier = Modifier.height(16.dp)) }
        )
    }
}

@Stable
class EpisodesUiState(
    val paginateOffset: Int = 6,
    val onPaginate: () -> Unit = {}
) {
    var state by mutableStateOf<State>(State.None)

    sealed interface State {
        data object None : State
        data object Loading : State
        data object Error : State
        class Data : State {
            var nextPage = 0

            var viewData by mutableStateOf(listOf<ComposeViewData>())
            var isLoadingNextPage: Boolean by mutableStateOf(false)
            var canPaginate: Boolean by mutableStateOf(true)
        }
    }
}

sealed interface EpisodesUiEvent {
    data class OnEpisodeClick(
        val nv: NavHostController,
        val id: Int
    ) : EpisodesUiEvent
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun EpisodesUiPreview() = AppTheme {
    EpisodesUi(NoopEnterAnimator()).View(
        rememberNavController(),
        makeState()
    ) {}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EpisodesUiPreviewNight() = AppTheme {
    EpisodesUi(NoopEnterAnimator()).View(
        rememberNavController(),
        makeState()
    ) {}
}

private fun makeState() = EpisodesUiState().apply {
    state = State.Data().apply {
        viewData = listOf(
            dummyItem(0),
            dummyItem(1),
            dummyItem(2),
            dummyItem(3),
            dummyItem(4),
            dummyItem(5),
            dummyItem(6),
            dummyItem(7),
        )
    }
}

private fun dummyItem(id: Int): ImageListingViewData = ImageListingViewData(
    containerParams = ContainerParams(
        outerPadding = ContainerPadding(horizontal = 16.dp, vertical = 8.dp)
    ),
    id = id,
    icon = "http://clipart-library.com/data_images/6103.png",
    title = "Title",
    description = "Description"
)



