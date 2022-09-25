package com.chaliez.alma.test.data.repository.marvel

import com.chaliez.alma.test.data.model.MarvelHero
import com.chaliez.alma.test.data.network.model.asDomainModel
import com.chaliez.alma.test.data.network.retrofit.MarvelService
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NetworkMarvelHeroRepository @Inject constructor(
    private val marvelService: MarvelService
) : RemoteMarvelHeroRepository {

    override suspend fun getAll(): Flow<List<MarvelHero>> =
        flow { emit(marvelService.getCharacters().data.results.map { hero -> hero.asDomainModel() }) }

    override suspend fun getSquad(): Flow<List<MarvelHero>> = emptyFlow() //no squad from API

    override suspend fun get(id: Int): Flow<MarvelHero> =
        flow { emit(marvelService.getCharacter(id).data.results[0].asDomainModel()) }

}