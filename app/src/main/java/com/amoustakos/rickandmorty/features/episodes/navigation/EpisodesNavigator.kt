package com.amoustakos.rickandmorty.features.episodes.navigation

import com.amoustakos.rickandmorty.navigation.FeatureScreens
import com.amoustakos.rickandmorty.navigation.screens.Screen
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class EpisodesNavigator @Inject constructor(
    episodes: EpisodesScreen
) : FeatureScreens {

    override val start: String = episodes.route

    override val screens: List<Screen> = listOf(
        episodes
    )

}