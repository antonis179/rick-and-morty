package com.amoustakos.rickandmorty.compose.lists.models


sealed interface ElementPosition {

    data object Center : ElementPosition
    data object Start : ElementPosition
    data object End : ElementPosition
    data object Top : ElementPosition
    data object Bottom : ElementPosition

}
