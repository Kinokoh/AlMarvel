package com.chaliez.alma.test.data.repository.marvel

import com.chaliez.alma.test.data.local.database.MarvelHeroDao
import com.chaliez.alma.test.data.local.database.asDatabaseModel
import com.chaliez.alma.test.data.local.database.asDomainModel
import com.chaliez.alma.test.data.model.MarvelHero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseMarvelHeroRepository @Inject constructor(
    private val marvelHeroDao: MarvelHeroDao
) : MarvelHeroRepository {

    override suspend fun getAll(): Flow<List<MarvelHero>> =
        marvelHeroDao.getMarvelHeroes().map { it.asDomainModel() }

    override suspend fun getSquad(): Flow<List<MarvelHero>> =
        marvelHeroDao.getMarvelHeroesSquad().map { it.asDomainModel() }

    override suspend fun get(id: Int): Flow<MarvelHero> =
        marvelHeroDao.getMarvelHero(id).map {
            it.asDomainModel()
        }

    override suspend fun updateSquad(id: Int, isPartOfSquad: Boolean) =
        marvelHeroDao.updateIsPartOfSquad(id, isPartOfSquad)

    override suspend fun addAll(newHeroes: List<MarvelHero>) =
        newHeroes.asDatabaseModel().forEach {
            marvelHeroDao.insertMarvelHero(it)
        }

}
