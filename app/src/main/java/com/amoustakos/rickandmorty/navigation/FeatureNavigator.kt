package com.amoustakos.rickandmorty.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.amoustakos.rickandmorty.navigation.screens.ScreenNavigator


interface FeatureNavigator {

    val screens: List<ScreenNavigator>

    fun navigation(graph: NavGraphBuilder, nv: NavHostController)

}
