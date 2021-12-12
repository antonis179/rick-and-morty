package com.amoustakos.rickandmorty.navigation.screens

import androidx.navigation.NavHostController


interface Screen {

    val route: String

    fun currentRoute(nv: NavHostController) =
        nv.currentBackStackEntry?.destination?.route

}