package com.amoustakos.rickandmorty.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import javax.inject.Inject


class DefaultTransitions @Inject constructor() : Transitions

interface Transitions {

    fun enterTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? {
        return { slideInHorizontally(initialOffsetX = { it }) }
    }

    fun exitTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? {
        return { slideOutHorizontally(targetOffsetX = { -it }) }
    }

    fun popEnterTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? {
        return { slideInHorizontally(initialOffsetX = { -it }) }
    }

    fun popExitTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? {
        return { slideOutHorizontally(targetOffsetX = { it }) }
    }


}
