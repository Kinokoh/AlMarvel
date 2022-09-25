package com.chaliez.alma.test.data.di

import com.chaliez.alma.test.data.repository.preference.AppPreferencesRepository
import com.chaliez.alma.test.data.repository.marvel.DefaultMarvelHeroRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.chaliez.alma.test.data.repository.marvel.MarvelHeroRepository
import com.chaliez.alma.test.data.repository.preference.DefaultAppPreferencesRepository

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsMarvelHeroRepository(
        marvelHeroRepository: DefaultMarvelHeroRepository
    ): MarvelHeroRepository

    @Singleton
    @Binds
    fun bindsPreferencesRepository(
        preferenceRepository: DefaultAppPreferencesRepository
    ): AppPreferencesRepository

}
