package com.amoustakos.rickandmorty.di

import android.content.Context
import android.content.pm.ApplicationInfo
import com.amoustakos.rickandmorty.di.annotations.DebugFlag
import com.amoustakos.rickandmorty.di.annotations.DefaultDispatcher
import com.amoustakos.rickandmorty.di.annotations.IoDispatcher
import com.amoustakos.rickandmorty.di.annotations.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


/**
 * Basic Hilt structure can be found here: [https://dagger.dev/hilt/components]
 */
@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @DebugFlag
    @Singleton
    @Provides
    fun provideDebugFlag(
        @ApplicationContext ctx: Context
    ): Boolean {
        return ctx.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Singleton
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Singleton
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}
