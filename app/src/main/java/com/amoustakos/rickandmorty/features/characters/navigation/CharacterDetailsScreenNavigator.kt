package com.amoustakos.rickandmorty.features.characters.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.amoustakos.rickandmorty.features.characters.CharacterDetailsViewModel
import com.amoustakos.rickandmorty.features.characters.CharacterDetailsViewModelFactory
import com.amoustakos.rickandmorty.features.characters.ui.CharacterDetailsUi
import com.amoustakos.rickandmorty.navigation.Transitions
import com.amoustakos.rickandmorty.navigation.screens.ScreenNavigator
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.serialization.Serializable
import javax.inject.Inject


@Serializable
data class CharacterDetailsScreen(
    val id: Int
)

@ActivityScoped
class CharacterDetailsScreenNavigator @Inject constructor(
    private val ui: CharacterDetailsUi,
    private val transitions: Transitions,
) : ScreenNavigator, Transitions by transitions {

    override fun route() = CharacterDetailsScreen

    override fun destination(graph: NavGraphBuilder, nv: NavHostController) {
        graph.composable<CharacterDetailsScreen>(
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            val args = it.toRoute<CharacterDetailsScreen>()
            val vm = hiltViewModel<CharacterDetailsViewModel, CharacterDetailsViewModelFactory> { factory ->
                factory.create(args.id)
            }
            ui.View(vm.uiState)
        }
    }

}