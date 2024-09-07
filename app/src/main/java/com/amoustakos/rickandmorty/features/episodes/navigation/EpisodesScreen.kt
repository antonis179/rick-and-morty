package com.amoustakos.rickandmorty.features.episodes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.amoustakos.rickandmorty.PopRouteData
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUi
import com.amoustakos.rickandmorty.navigateSingleTop
import com.amoustakos.rickandmorty.navigation.Transitions
import com.amoustakos.rickandmorty.navigation.screens.Screen
import javax.inject.Inject


class EpisodesScreen @Inject constructor(
    private val ui: EpisodesUi,
    private val transitions: Transitions
) : Screen, Transitions by transitions {

    override val route: String = ROUTE

    @OptIn(ExperimentalAnimationApi::class)
    override fun destination(graph: NavGraphBuilder, nv: NavHostController) {
        graph.composable(
            ROUTE,
            enterTransition = enterTransition(currentRoute(nv), ROUTE),
            exitTransition = exitTransition(currentRoute(nv), ROUTE),
            popEnterTransition = popEnterTransition(currentRoute(nv), ROUTE),
            popExitTransition = popExitTransition(currentRoute(nv), ROUTE)
        ) {
            ui.View(nv, EpisodesUi.makeModel().uiState)
        }
    }

    companion object {
        const val ROUTE: String = "episodes"

        fun navigate(nv: NavHostController, popTo: PopRouteData? = null) {
            nv.navigateSingleTop(ROUTE, popTo)
        }
    }

}