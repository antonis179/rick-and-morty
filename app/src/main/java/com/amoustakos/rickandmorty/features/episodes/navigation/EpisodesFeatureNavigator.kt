package com.amoustakos.rickandmorty.features.episodes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.amoustakos.rickandmorty.navigation.FeatureNavigator
import com.amoustakos.rickandmorty.navigation.screens.ScreenNavigator
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.serialization.Serializable
import javax.inject.Inject


@ActivityScoped
class EpisodesFeatureNavigator @Inject constructor(
    private val episodesScreenNavigator: EpisodesScreenNavigator
) : FeatureNavigator {

    override val screens: List<ScreenNavigator> = listOf(
        episodesScreenNavigator
    )

    override fun navigation(graph: NavGraphBuilder, nv: NavHostController) {
        graph.navigation<EpisodesFeature>(startDestination = episodesScreenNavigator.route()) {
            screens.forEach {
                it.destination(this, nv)
            }
        }
    }

    @Serializable
    data object EpisodesFeature
}