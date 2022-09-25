package com.chaliez.alma.test.data.local.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.chaliez.alma.test.data.local.database.AppDatabase
import com.chaliez.alma.test.data.local.database.MarvelHeroDao
import com.chaliez.alma.test.data.local.database.getDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideMarvelHeroDao(appDatabase: AppDatabase): MarvelHeroDao {
        return appDatabase.marvelHeroDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return getDatabase(appContext)
    }
}
