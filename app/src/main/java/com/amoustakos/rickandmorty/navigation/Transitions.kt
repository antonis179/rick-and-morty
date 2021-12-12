package com.amoustakos.rickandmorty.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry


interface Transitions {

    companion object {
        private const val OFFSET_ANIMATION = 1100 //TODO: Use screen size
    }

    fun enterTransition(
        from: String?,
        to: String?
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? {
        return { slideInHorizontally(initialOffsetX = { OFFSET_ANIMATION }) }
    }

    fun exitTransition(
        from: String?,
        to: String?
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? {
        return { slideOutHorizontally(targetOffsetX = { -OFFSET_ANIMATION }) }
    }

    fun popEnterTransition(
        from: String?,
        to: String?
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? {
        return { slideInHorizontally(initialOffsetX = { -OFFSET_ANIMATION }) }
    }

    fun popExitTransition(
        from: String?,
        to: String?
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? {
        return { slideOutHorizontally(targetOffsetX = { OFFSET_ANIMATION }) }
    }


}