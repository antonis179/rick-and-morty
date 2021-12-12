package com.amoustakos.rickandmorty.navigation.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.amoustakos.rickandmorty.features.characters.CharacterDetailsUi
import com.amoustakos.rickandmorty.navigation.Transitions
import com.google.accompanist.navigation.animation.composable


object CharacterDetails : Screen, Transitions {

    private const val TAG_ID = "character_id"
    override val route = "character?$TAG_ID={$TAG_ID}"

    data class CharacterDetailsArgs(
        val id: Int
    )

    fun getDestination(id: Int): String = "character?$TAG_ID=$id"

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
    fun NavGraphBuilder.characterDetailsDestination(nv: NavHostController, ui: CharacterDetailsUi) {
        composable(
            CharacterDetails.route,
            arguments = argumentList,
            enterTransition = enterTransition(currentRoute(nv), CharacterDetails.route),
            exitTransition = exitTransition(currentRoute(nv), CharacterDetails.route),
            popEnterTransition = popEnterTransition(currentRoute(nv), CharacterDetails.route),
            popExitTransition = popExitTransition(currentRoute(nv), CharacterDetails.route)
        ) {
            val args = parseArguments(it)
            val vm = CharacterDetailsUi.makeModel()
            vm.setup(args)
            ui.View(nv, vm.uiState)
        }
    }
}