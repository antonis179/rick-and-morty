package com.amoustakos.rickandmorty

import androidx.navigation.NavHostController
import com.amoustakos.rickandmorty.navigation.screens.CharacterDetails
import com.amoustakos.rickandmorty.navigation.screens.Characters
import com.amoustakos.rickandmorty.navigation.screens.Episodes
import com.amoustakos.rickandmorty.navigation.screens.Screen
import javax.inject.Inject
import javax.inject.Singleton


data class PopRouteData(
    val dest: String,
    val inclusive: Boolean = true
)


fun NavHostController.navigateSingleTop(dest: String, popTo: PopRouteData? = null) {
    navigate(dest) {
        launchSingleTop = true
        popTo?.let {
            popUpTo(it.dest) { inclusive = it.inclusive }
        }
    }
}


@Singleton
class NavGraph @Inject constructor() {

    val home: Screen = Episodes

    fun navigateToCharacters(nv: NavHostController, ids: List<String>, popTo: PopRouteData? = null) {
        nv.navigateSingleTop(Characters.getDestination(ids), popTo)
    }

    fun navigateToCharacterDetails(nv: NavHostController, id: Int, popTo: PopRouteData? = null) {
        nv.navigateSingleTop(CharacterDetails.getDestination(id), popTo)
    }


}