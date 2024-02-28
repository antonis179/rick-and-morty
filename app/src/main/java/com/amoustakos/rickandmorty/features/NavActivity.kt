package com.amoustakos.rickandmorty.features

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.amoustakos.rickandmorty.ui.BaseActivity
import com.amoustakos.rickandmorty.ui.theme.AppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class NavActivity : BaseActivity() {

    @Inject
    lateinit var navigator: FeatureNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val nv = rememberAnimatedNavController()

                AnimatedNavHost(
                    nv,
                    startDestination = navigator.start
                ) {
                    navigator.screens.forEach { screen ->
                        screen.destination(this, nv)
                    }
                }
            }
        }
    }


}