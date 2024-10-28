package com.amoustakos.rickandmorty.features.characters.di

import com.amoustakos.rickandmorty.data.domain.DomainTransformer
import com.amoustakos.rickandmorty.data.domain.models.characters.CharactersResponse
import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.data.network.api.ApiErrorHandler
import com.amoustakos.rickandmorty.data.network.api.DefaultApiErrorHandlerImpl
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharacter
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharactersResponse
import com.amoustakos.rickandmorty.features.characters.domain.CharacterDomainTransformer
import com.amoustakos.rickandmorty.features.characters.domain.CharactersDomainTransformer
import com.amoustakos.rickandmorty.features.characters.ui.transformer.CharacterDetailsViewDataTransformer
import com.amoustakos.rickandmorty.features.characters.ui.transformer.CharacterListingViewDataTransformer
import com.amoustakos.rickandmorty.ui.transformers.ViewDataTransformer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
interface CharactersViewModelModule {

    @Binds
    @ViewModelScoped
    fun bindErrorHandler(
        impl: DefaultApiErrorHandlerImpl<ApiCharacter, DomainCharacter>
    ): ApiErrorHandler<ApiCharacter, DomainCharacter>

    @Binds
    @ViewModelScoped
    fun bindCharactersErrorHandler(
        impl: DefaultApiErrorHandlerImpl<ApiCharactersResponse, CharactersResponse>
    ): ApiErrorHandler<ApiCharactersResponse, CharactersResponse>

    @Binds
    @ViewModelScoped
    fun bindDomainTransformer(
        impl: CharacterDomainTransformer
    ): DomainTransformer<ApiCharacter, DomainCharacter>

    @Binds
    @ViewModelScoped
    fun bindCharactersDomainTransformer(
        impl: CharactersDomainTransformer
    ): DomainTransformer<ApiCharactersResponse, CharactersResponse>

    @Binds
    @ViewModelScoped
    fun bindCharacterDetailsViewDataTransformer(
        impl: CharacterDetailsViewDataTransformer
    ): ViewDataTransformer<DomainCharacter>

    @Binds
    @ViewModelScoped
    fun bindCharacterListingViewDataTransformer(
        impl: CharacterListingViewDataTransformer
    ): ViewDataTransformer<CharactersResponse>

}