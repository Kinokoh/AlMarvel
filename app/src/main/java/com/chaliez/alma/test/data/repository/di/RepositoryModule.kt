package com.chaliez.alma.test.data.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.chaliez.alma.test.data.local.database.MarvelHeroDao
import com.chaliez.alma.test.data.network.retrofit.MarvelService
import com.chaliez.alma.test.data.repository.marvel.DatabaseMarvelHeroRepository
import com.chaliez.alma.test.data.repository.marvel.NetworkMarvelHeroRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideNetworkRepository(marvelService: MarvelService): NetworkMarvelHeroRepository {
        return NetworkMarvelHeroRepository(marvelService)
    }

    @Provides
    fun provideDatabaseRepository(marvelHeroDao: MarvelHeroDao): DatabaseMarvelHeroRepository {
        return DatabaseMarvelHeroRepository(marvelHeroDao)
    }
}
