package com.amoustakos.rickandmorty.data.network.di

import com.amoustakos.rickandmorty.data.network.api.RickAndMortyApi
import com.amoustakos.rickandmorty.data.network.di.annotations.DefaultRetrofit
import com.amoustakos.rickandmorty.data.network.factory.DefaultNetworkConfigFactory
import com.amoustakos.rickandmorty.data.network.factory.RetrofitFactory
import com.amoustakos.rickandmorty.di.annotations.DebugFlag
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    @DefaultRetrofit
    fun provideDefaultRetrofitEngine(
        @DebugFlag isDebug: Boolean
    ) = RetrofitFactory.makeRetrofitEngine(
        RetrofitFactory.makeHttpClient(DefaultNetworkConfigFactory.defaultOkhttpOptions(isDebug)),
        DefaultNetworkConfigFactory.defaultRetrofitOptions("https://rickandmortyapi.com/api/")
    )


    @Provides
    fun provideRickAndMortyApi(
        @DefaultRetrofit engine: Retrofit
    ): RickAndMortyApi = engine.create(RickAndMortyApi::class.java)

}