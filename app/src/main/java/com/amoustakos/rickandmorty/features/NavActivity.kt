package com.amoustakos.rickandmorty.features

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.amoustakos.rickandmorty.ui.BaseActivity
import com.amoustakos.rickandmorty.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class NavActivity : BaseActivity() {

    @Inject
    lateinit var navigator: FeatureNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val nv = rememberNavController()

                NavHost(
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