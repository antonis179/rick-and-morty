package com.amoustakos.rickandmorty.features

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import com.amoustakos.rickandmorty.features.characters.navigation.CharacterFeatureNavigator
import com.amoustakos.rickandmorty.features.episodes.navigation.EpisodesFeatureNavigator
import com.amoustakos.rickandmorty.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavActivity : BaseActivity() {

    @Inject
    lateinit var episodes: EpisodesFeatureNavigator

    @Inject
    lateinit var characters: CharacterFeatureNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val nv = rememberNavController()
                NavHost(
                    navController = nv,
                    startDestination = episodes.route
                ) {
                    episodes.navigation(this, nv)
                    characters.navigation(this, nv)
                }
            }
        }
    }


}