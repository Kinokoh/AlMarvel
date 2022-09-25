package com.chaliez.alma.test.data.repository.marvel

import com.chaliez.alma.test.data.model.MarvelHero
import kotlinx.coroutines.flow.*

interface RemoteMarvelHeroRepository {
    suspend fun getAll(): Flow<List<MarvelHero>>
    suspend fun getSquad(): Flow<List<MarvelHero>>
    suspend fun get(id: Int): Flow<MarvelHero>
}

interface MarvelHeroRepository: RemoteMarvelHeroRepository {
    suspend fun updateSquad(id: Int, isPartOfSquad: Boolean)
    suspend fun addAll(newHeroes :List<MarvelHero>)
}