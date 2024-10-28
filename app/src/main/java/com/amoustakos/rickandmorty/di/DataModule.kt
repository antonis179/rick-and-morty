package com.amoustakos.rickandmorty.di

import com.amoustakos.rickandmorty.data.network.api.RickAndMortyDataProvider
import com.amoustakos.rickandmorty.data.network.api.RickAndMortyDataProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindsDataProvider(
        impl: RickAndMortyDataProviderImpl
    ): RickAndMortyDataProvider


}