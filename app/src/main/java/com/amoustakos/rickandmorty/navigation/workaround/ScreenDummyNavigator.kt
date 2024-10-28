package com.amoustakos.rickandmorty.navigation.workaround

import androidx.compose.foundation.layout.Box
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.amoustakos.rickandmorty.navigation.Transitions
import com.amoustakos.rickandmorty.navigation.screens.ScreenNavigator
import kotlinx.serialization.Serializable
import javax.inject.Inject


@Serializable
object ScreenDummy

class ScreenDummyNavigator @Inject constructor(
    private val transitions: Transitions
) : ScreenNavigator, Transitions by transitions {

    override fun route() = ScreenDummy

    override fun destination(graph: NavGraphBuilder, nv: NavHostController) {
        graph.composable<ScreenDummy>(
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            Box {

            }
        }
    }

}