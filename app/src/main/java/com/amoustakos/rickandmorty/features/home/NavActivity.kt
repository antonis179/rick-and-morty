package com.amoustakos.rickandmorty.features.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.amoustakos.rickandmorty.NavGraph
import com.amoustakos.rickandmorty.features.characters.CharacterDetailsUi
import com.amoustakos.rickandmorty.features.characters.CharactersListingUi
import com.amoustakos.rickandmorty.features.episodes.ui.EpisodesUi
import com.amoustakos.rickandmorty.navigation.screens.CharacterDetails.characterDetailsDestination
import com.amoustakos.rickandmorty.navigation.screens.Characters.charactersDestination
import com.amoustakos.rickandmorty.navigation.screens.Episodes.episodesDestination
import com.amoustakos.rickandmorty.ui.BaseActivity
import com.amoustakos.rickandmorty.ui.theme.AppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class NavActivity : BaseActivity() {

    @Inject
    lateinit var navGraph: NavGraph

    @Inject
    lateinit var episodesUi: Lazy<EpisodesUi>

    @Inject
    lateinit var charactersListingUi: Lazy<CharactersListingUi>

    @Inject
    lateinit var characterDetailsUi: Lazy<CharacterDetailsUi>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val nv = rememberAnimatedNavController()

                AnimatedNavHost(
                    nv,
                    startDestination = navGraph.home.route
                ) {
                    episodesDestination(nv, episodesUi.get())
                    charactersDestination(nv, charactersListingUi.get())
                    characterDetailsDestination(nv, characterDetailsUi.get())
                }
            }
        }

    }

}