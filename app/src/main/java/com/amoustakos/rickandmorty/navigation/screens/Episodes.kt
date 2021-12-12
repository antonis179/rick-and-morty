package com.amoustakos.rickandmorty.navigation.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUi
import com.amoustakos.rickandmorty.navigation.Transitions
import com.google.accompanist.navigation.animation.composable


object Episodes : Screen, Transitions {
    override val route: String = "episodes"

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.episodesDestination(nv: NavHostController, ui: EpisodesUi) {
        composable(
            Episodes.route,
            enterTransition = enterTransition(currentRoute(nv), Episodes.route),
            exitTransition = exitTransition(currentRoute(nv), Episodes.route),
            popEnterTransition = popEnterTransition(currentRoute(nv), Episodes.route),
            popExitTransition = popExitTransition(currentRoute(nv), Episodes.route)
        ) {
            ui.View(nv, EpisodesUi.makeModel().uiState)
        }
    }



}