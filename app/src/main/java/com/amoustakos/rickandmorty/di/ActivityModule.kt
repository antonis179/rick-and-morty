package com.amoustakos.rickandmorty.di

import com.amoustakos.rickandmorty.navigation.DefaultTransitions
import com.amoustakos.rickandmorty.navigation.Transitions
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
interface ActivityModule {

    @Binds
    @ActivityScoped
    fun bindTransitions(transitions: DefaultTransitions): Transitions

}