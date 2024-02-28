package com.amoustakos.rickandmorty.features.episodes.navigation

import com.amoustakos.rickandmorty.navigation.FeatureScreens
import com.amoustakos.rickandmorty.navigation.screens.Screen
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EpisodesNavigator @Inject constructor(
    episodes: EpisodesScreen
) : FeatureScreens {

    override val start: String = episodes.route

    override val screens: List<Screen> = listOf(
        episodes
    )

}