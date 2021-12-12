package com.amoustakos.rickandmorty.features.episodes.di

import com.amoustakos.rickandmorty.data.domain.DomainTransformer
import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.data.network.api.ApiErrorHandler
import com.amoustakos.rickandmorty.data.network.api.DefaultApiErrorHandlerImpl
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiEpisodesResponse
import com.amoustakos.rickandmorty.features.episodes.domain.EpisodesDomainTransformer
import com.amoustakos.rickandmorty.features.episodes.ui.transformer.EpisodesViewDataTransformer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
interface EpisodesViewModelModule {

    @Binds
    @ViewModelScoped
    fun bindErrorHandler(
        impl: DefaultApiErrorHandlerImpl<ApiEpisodesResponse, EpisodesResponse>
    ): ApiErrorHandler<ApiEpisodesResponse, EpisodesResponse>

    @Binds
    @ViewModelScoped
    fun bindDomainTransformer(
        impl: EpisodesDomainTransformer
    ): DomainTransformer<ApiEpisodesResponse, EpisodesResponse, Int>

    companion object {

        @Provides
        @ViewModelScoped
        fun providesEpisodesViewDataTransformer(): EpisodesViewDataTransformer =
            EpisodesViewDataTransformer()

    }

}