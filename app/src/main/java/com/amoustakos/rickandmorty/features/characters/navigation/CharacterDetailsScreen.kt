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
import com.amoustakos.rickandmorty.features.characters.ui.CharacterDetailsUi
import com.amoustakos.rickandmorty.navigateSingleTop
import com.amoustakos.rickandmorty.navigation.Transitions
import com.amoustakos.rickandmorty.navigation.screens.Screen
import javax.inject.Inject


class CharacterDetailsScreen @Inject constructor(
    private val ui: CharacterDetailsUi,
    private val transitions: Transitions
) : Screen, Transitions by transitions {

    override val route = ROUTE

    data class CharacterDetailsArgs(
        val id: Int
    )

    private fun parseArguments(backStackEntry: NavBackStackEntry) = CharacterDetailsArgs(
        id = backStackEntry.arguments?.getInt(TAG_ID, -1) ?: -1
    )

    private val argumentList: MutableList<NamedNavArgument>
        get() = mutableListOf(
            navArgument(TAG_ID) {
                type = NavType.IntType
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
            val vm = CharacterDetailsUi.makeModel()
            vm.setup(args)
            ui.View(vm.uiState)
        }
    }

    companion object {
        private const val TAG_ID = "character_id"
        const val ROUTE: String = "character?$TAG_ID={$TAG_ID}"

        private fun getDestinationPath(id: Int): String = "character?$TAG_ID=$id"

        fun navigate(nv: NavHostController, id: Int, popTo: PopRouteData? = null) {
            nv.navigateSingleTop(getDestinationPath(id), popTo)
        }
    }
}