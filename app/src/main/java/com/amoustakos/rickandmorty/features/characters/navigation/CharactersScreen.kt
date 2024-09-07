package com.amoustakos.rickandmorty.features.characters.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.amoustakos.rickandmorty.PopRouteData
import com.amoustakos.rickandmorty.features.characters.ui.CharactersListingUi
import com.amoustakos.rickandmorty.navigateSingleTop
import com.amoustakos.rickandmorty.navigation.Transitions
import com.amoustakos.rickandmorty.navigation.screens.Screen
import javax.inject.Inject


class CharactersScreen @Inject constructor(
    private val ui: CharactersListingUi,
    private val transitions: Transitions
) : Screen, Transitions by transitions {

    override val route = ROUTE

    data class CharacterArgs(
        val ids: List<String>
    )

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
    override fun destination(graph: NavGraphBuilder, nv: NavHostController) {
        graph.composable(
            ROUTE,
            arguments = argumentList,
            enterTransition = enterTransition(currentRoute(nv), ROUTE),
            exitTransition = exitTransition(currentRoute(nv), ROUTE),
            popEnterTransition = popEnterTransition(currentRoute(nv), ROUTE),
            popExitTransition = popExitTransition(currentRoute(nv), ROUTE)
        ) {
            val args = parseArguments(it)
            val vm = CharactersListingUi.makeModel()
            vm.setup(args)
            ui.View(nv, vm.uiState)
        }
    }

    companion object {
        private const val TAG_IDS = "character_ids"
        const val ROUTE: String = "characters?$TAG_IDS={$TAG_IDS}"

        private fun getDestinationPath(ids: List<String>): String = "characters?$TAG_IDS=$ids"

        fun navigate(nv: NavHostController, ids: List<String>, popTo: PopRouteData? = null) {
            nv.navigateSingleTop(getDestinationPath(ids), popTo)
        }
    }
}