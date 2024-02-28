package com.amoustakos.rickandmorty.features.characters.navigation

import com.amoustakos.rickandmorty.navigation.FeatureScreens
import com.amoustakos.rickandmorty.navigation.screens.Screen
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CharactersNavigator @Inject constructor(
    characters: CharactersScreen,
    characterDetails: CharacterDetailsScreen
) : FeatureScreens {

    override val start: String = characters.route

    override val screens: List<Screen> = listOf(
        characters,
        characterDetails
    )

}