package com.amoustakos.rickandmorty.navigation.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController


interface ScreenNavigator {

    fun route(): Any

    fun destination(graph: NavGraphBuilder, nv: NavHostController)

}
