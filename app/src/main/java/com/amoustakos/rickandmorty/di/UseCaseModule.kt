package com.amoustakos.rickandmorty.di

import com.amoustakos.rickandmorty.data.domain.DomainTransformer
import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.data.domain.models.episodes.EpisodesResponse
import com.amoustakos.rickandmorty.data.network.api.ApiErrorHandler
import com.amoustakos.rickandmorty.data.network.api.RickAndMortyDataProvider
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharacter
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharactersResponse
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiEpisodesResponse
import com.amoustakos.rickandmorty.features.characters.usecases.FetchCharacterUseCase
import com.amoustakos.rickandmorty.features.characters.usecases.FetchCharacterUseCaseImpl
import com.amoustakos.rickandmorty.features.episodes.usecases.FetchEpisodesUseCase
import com.amoustakos.rickandmorty.features.episodes.usecases.FetchEpisodesUseCaseImpl
import com.amoustakos.rickandmorty.utils.DispatchersWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    companion object {

        @Provides
        @ViewModelScoped
        fun provideFetchEpisodesUseCase(
            dataProvider: RickAndMortyDataProvider,
            errorHandler: ApiErrorHandler<ApiEpisodesResponse, EpisodesResponse>,
            domainTransformer: DomainTransformer<ApiEpisodesResponse, EpisodesResponse>,
            dispatchers: DispatchersWrapper
        ): FetchEpisodesUseCase = FetchEpisodesUseCaseImpl(
            dataProvider,
            errorHandler,
            domainTransformer,
            dispatchers
        )

        @Provides
        @ViewModelScoped
        fun provideFetchCharacterUseCase(
            dataProvider: RickAndMortyDataProvider,
            errorHandler: ApiErrorHandler<ApiCharacter, DomainCharacter>,
            charactersErrorHandler: ApiErrorHandler<ApiCharactersResponse, CharactersResponse>,
            domainTransformer: DomainTransformer<ApiCharacter, DomainCharacter>,
            charactersDomainTransformer: DomainTransformer<ApiCharactersResponse, CharactersResponse>,
            dispatchers: DispatchersWrapper
        ): FetchCharacterUseCase = FetchCharacterUseCaseImpl(
            dataProvider,
            errorHandler,
            charactersErrorHandler,
            domainTransformer,
            charactersDomainTransformer,
            dispatchers
        )

    }

}