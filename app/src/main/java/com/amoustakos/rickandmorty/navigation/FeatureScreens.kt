package com.amoustakos.rickandmorty.navigation

import com.amoustakos.rickandmorty.navigation.screens.Screen


interface FeatureScreens {

    val start: String

    val screens: List<Screen>

}