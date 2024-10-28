package com.amoustakos.rickandmorty.features.episodes.di

import com.amoustakos.rickandmorty.compose.animation.list_enter.AnimatorEnter
import com.amoustakos.rickandmorty.compose.animation.list_enter.SlideInBottomAnimator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier


@Module
@InstallIn(ActivityComponent::class)
interface EpisodesUiModule {

    @Binds
    @EpisodesEnterAnimation
    fun bind(transitions: SlideInBottomAnimator): AnimatorEnter

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EpisodesEnterAnimation