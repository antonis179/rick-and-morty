package com.amoustakos.rickandmorty.features.characters.navigation

import com.amoustakos.rickandmorty.navigation.FeatureScreens
import com.amoustakos.rickandmorty.navigation.screens.Screen
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
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