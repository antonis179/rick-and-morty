package com.amoustakos.rickandmorty

import androidx.navigation.NavHostController


data class PopRouteData(
    val dest: String,
    val inclusive: Boolean = true
)


fun NavHostController.navigateSingleTop(dest: String, popTo: PopRouteData? = null) {
    navigate(dest) {
        launchSingleTop = true
        popTo?.let {
            popUpTo(it.dest) { inclusive = it.inclusive }
        }
    }
}