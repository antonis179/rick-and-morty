package com.amoustakos.rickandmorty.features.characters.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.amoustakos.rickandmorty.features.characters.CharacterListingViewModel
import com.amoustakos.rickandmorty.features.characters.CharactersViewModelFactory
import com.amoustakos.rickandmorty.features.characters.ui.CharacterListingUi
import com.amoustakos.rickandmorty.navigation.Transitions
import com.amoustakos.rickandmorty.navigation.screens.ScreenNavigator
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.serialization.Serializable
import javax.inject.Inject


@Serializable
data class CharacterListingScreen(
    val ids: List<String>
)

@ActivityScoped
class CharacterListingScreenNavigator @Inject constructor(
    private val ui: CharacterListingUi,
    private val transitions: Transitions,
) : ScreenNavigator, Transitions by transitions {

    override fun route() = CharacterListingScreen

    override fun destination(graph: NavGraphBuilder, nv: NavHostController) {
        graph.composable<CharacterListingScreen>(
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            val args = it.toRoute<CharacterListingScreen>()
            val vm = hiltViewModel<CharacterListingViewModel, CharactersViewModelFactory> { factory ->
                factory.create(args.ids)
            }
            ui.View(nv, vm.uiState, vm::handleEvent)
        }
    }

}