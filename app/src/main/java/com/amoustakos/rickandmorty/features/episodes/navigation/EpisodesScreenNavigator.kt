package com.amoustakos.rickandmorty.features.episodes.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.amoustakos.rickandmorty.features.episodes.EpisodesViewModel
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUi
import com.amoustakos.rickandmorty.navigation.Transitions
import com.amoustakos.rickandmorty.navigation.screens.ScreenNavigator
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.serialization.Serializable
import javax.inject.Inject


@Serializable
object EpisodesScreen

@ActivityScoped
class EpisodesScreenNavigator @Inject constructor(
    private val ui: EpisodesUi,
    private val transitions: Transitions,
) : ScreenNavigator, Transitions by transitions {

    override fun route() = EpisodesScreen

    override fun destination(graph: NavGraphBuilder, nv: NavHostController) {
        graph.composable<EpisodesScreen>(
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            val vm: EpisodesViewModel = hiltViewModel()
            ui.View(nv, vm.uiState, vm::handleEvent)
        }
    }

}