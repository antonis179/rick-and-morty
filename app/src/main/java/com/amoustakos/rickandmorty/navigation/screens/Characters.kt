package com.amoustakos.rickandmorty.navigation.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.amoustakos.rickandmorty.features.characters.CharactersListingUi
import com.amoustakos.rickandmorty.navigation.Transitions
import com.google.accompanist.navigation.animation.composable


object Characters : Screen, Transitions {

    private const val TAG_IDS = "character_ids"
    override val route = "characters?$TAG_IDS={$TAG_IDS}"

    data class CharacterArgs(
        val ids: List<String>
    )

    fun getDestination(ids: List<String>): String = "characters?$TAG_IDS=$ids"

    private fun parseArguments(backStackEntry: NavBackStackEntry) = CharacterArgs(
        ids = backStackEntry.arguments?.getStringArray(TAG_IDS)?.toList() ?: emptyList()
    )

    private val argumentList: MutableList<NamedNavArgument>
        get() = mutableListOf(
            navArgument(TAG_IDS) {
                type = NavType.StringArrayType
            }
        )

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.charactersDestination(nv: NavHostController, ui: CharactersListingUi) {
        composable(
            Characters.route,
            arguments = argumentList,
            enterTransition = enterTransition(currentRoute(nv), Characters.route),
            exitTransition = exitTransition(currentRoute(nv), Characters.route),
            popEnterTransition = popEnterTransition(currentRoute(nv), Characters.route),
            popExitTransition = popExitTransition(currentRoute(nv), Characters.route)
        ) {
            val args = parseArguments(it)
            val vm = CharactersListingUi.makeModel()
            vm.setup(args)
            ui.View(nv, vm.uiState)
        }
    }
}