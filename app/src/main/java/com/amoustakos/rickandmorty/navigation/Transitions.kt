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

    fun enterTransition(
        from: String?,
        to: String?
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? {
        return { slideInHorizontally(initialOffsetX = { it*2 }) }
    }

    fun exitTransition(
        from: String?,
        to: String?
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? {
        return { slideOutHorizontally(targetOffsetX = { -it*2 }) }
    }

    fun popEnterTransition(
        from: String?,
        to: String?
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? {
        return { slideInHorizontally(initialOffsetX = { -it*2 }) }
    }

    fun popExitTransition(
        from: String?,
        to: String?
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? {
        return { slideOutHorizontally(targetOffsetX = { it*2 }) }
    }


}