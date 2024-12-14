package com.amoustakos.rickandmorty.features.episodes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.amoustakos.rickandmorty.navigation.FeatureNavigator
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class EpisodesFeatureNavigator @Inject constructor(
    private val episodesScreenNavigator: EpisodesScreenNavigator
) : FeatureNavigator {

    override val route: Any = episodesScreenNavigator.route()

    override fun navigation(graph: NavGraphBuilder, nv: NavHostController) {
        episodesScreenNavigator.destination(graph, nv)
    }
}