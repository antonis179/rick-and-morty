package com.amoustakos.rickandmorty.features

import com.amoustakos.rickandmorty.features.characters.navigation.CharactersNavigator
import com.amoustakos.rickandmorty.features.episodes.navigation.EpisodesNavigator
import com.amoustakos.rickandmorty.navigation.FeatureScreens
import com.amoustakos.rickandmorty.navigation.screens.Screen
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton


@ActivityScoped
class FeatureNavigator @Inject constructor(
    episodes: EpisodesNavigator,
    characters: CharactersNavigator
) : FeatureScreens {

    override val start: String = episodes.start

    override val screens: List<Screen> = episodes.screens + characters.screens

}