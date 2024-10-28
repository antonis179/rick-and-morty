package com.amoustakos.rickandmorty.features.characters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.amoustakos.rickandmorty.navigation.FeatureNavigator
import com.amoustakos.rickandmorty.navigation.screens.ScreenNavigator
import com.amoustakos.rickandmorty.navigation.workaround.ScreenDummyNavigator
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.serialization.Serializable
import javax.inject.Inject
import kotlin.collections.forEach


@ActivityScoped
class CharacterFeatureNavigator @Inject constructor(
    characterListingScreenNavigator: CharacterListingScreenNavigator,
    characterDetailsScreenNavigator: CharacterDetailsScreenNavigator,
    private val screenDummyNavigator: ScreenDummyNavigator,
) : FeatureNavigator {

    override val screens: List<ScreenNavigator> = listOf(
        screenDummyNavigator,
        characterListingScreenNavigator,
        characterDetailsScreenNavigator
    )

    override fun navigation(graph: NavGraphBuilder, nv: NavHostController) {
        graph.navigation<EpisodesFeature>(startDestination = screenDummyNavigator.route()) {
            screens.forEach {
                it.destination(this, nv)
            }
        }
    }

    @Serializable
    data object EpisodesFeature
}