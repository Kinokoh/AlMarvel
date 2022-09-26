package com.chaliez.alma.test.testdi

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.chaliez.alma.test.data.di.DataModule
import com.chaliez.alma.test.data.repository.marvel.FakeMarvelHeroRepository
import com.chaliez.alma.test.data.repository.marvel.MarvelHeroRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface FakeDataModule {

    @Binds
    abstract fun bindRepository(
        fakeRepository: FakeMarvelHeroRepository
    ): MarvelHeroRepository
}
