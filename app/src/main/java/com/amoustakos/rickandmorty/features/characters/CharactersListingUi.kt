package com.amoustakos.rickandmorty.features.characters

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.amoustakos.rickandmorty.NavGraph
import com.amoustakos.rickandmorty.features.characters.ui.CharactersListingUi
import com.amoustakos.rickandmorty.ui.theme.AppTheme


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCharactersListingUiNight() = AppTheme {
    CharactersListingUi(NavGraph()).View(
        rememberNavController(),
        CharactersListingUi.CharactersUiState()
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewCharactersListingUi() = AppTheme {
    CharactersListingUi(NavGraph()).View(
        rememberNavController(),
        CharactersListingUi.CharactersUiState()
    )
}


