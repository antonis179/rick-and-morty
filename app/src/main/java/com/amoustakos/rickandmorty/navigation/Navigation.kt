package com.amoustakos.rickandmorty.navigation

import androidx.navigation.NavHostController


data class PopRouteData(
    val dest: Any,
    val inclusive: Boolean = true,
    val saveState: Boolean = true
)


fun NavHostController.navigateSingleTop(dest: Any, popTo: PopRouteData? = null) {
    navigate(dest) {
        launchSingleTop = true
        popTo?.let {
            popUpTo(it.dest) {
                inclusive = it.inclusive
                saveState = it.saveState
            }
        }
    }
}