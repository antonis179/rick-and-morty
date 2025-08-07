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


@ActivityScoped
class CharacterFeatureNavigator @Inject constructor(
    characterListingScreenNavigator: CharacterListingScreenNavigator,
    characterDetailsScreenNavigator: CharacterDetailsScreenNavigator,
    private val screenDummyNavigator: ScreenDummyNavigator
) : FeatureNavigator {

    private val screens: List<ScreenNavigator> = listOf(
        screenDummyNavigator,
        characterListingScreenNavigator,
        characterDetailsScreenNavigator
    )
    override val route: Any = CharacterFeature

    override fun navigation(graph: NavGraphBuilder, nv: NavHostController) {
        graph.navigation<CharacterFeature>(startDestination = screenDummyNavigator.route()) {
            screens.forEach {
                it.destination(this, nv)
            }
        }
    }

    @Serializable
    data object CharacterFeature
}