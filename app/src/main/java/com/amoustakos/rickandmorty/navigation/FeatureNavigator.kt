package com.amoustakos.rickandmorty.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController


interface FeatureNavigator {

    val route: Any

    fun navigation(graph: NavGraphBuilder, nv: NavHostController)

}
