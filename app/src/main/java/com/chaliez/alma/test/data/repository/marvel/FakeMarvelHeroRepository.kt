package com.chaliez.alma.test.data.repository.marvel

import com.chaliez.alma.test.data.model.MarvelHero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeMarvelHeroRepository @Inject constructor() : MarvelHeroRepository {
    private var _marvelHeroes: MutableList<MarvelHero> = fakeMarvelHeroes.toMutableList()

    override suspend fun getAll(): Flow<List<MarvelHero>> = flowOf(_marvelHeroes)

    override suspend fun getSquad(): Flow<List<MarvelHero>> = flowOf(
        _marvelHeroes.filter { it.isPartOfSquad }
    )

    override suspend fun get(id: Int): Flow<MarvelHero> = flowOf(_marvelHeroes.first { it.id == id } )

    override suspend fun updateSquad(id: Int, isPartOfSquad: Boolean) {
        _marvelHeroes = _marvelHeroes.map { if (it.id == id) it.copy(isPartOfSquad = isPartOfSquad) else it }.toMutableList()
    }

    override suspend fun addAll(newHeroes: List<MarvelHero>) {
        _marvelHeroes.addAll(newHeroes)
    }

}

val fakeMarvelHeroes = listOf(
    MarvelHero(1, "A-Bomb (HAS)", "Rick Jones has been Hulk's best and only friend for years, ever since he accidentally parked his vehicle near where a gamma bomb was about to go off, Bruce was able to save Rick by hiding him in the trenches but this resulted in Bruce turning into the Hulk. Over the years Rick would help the Hulk in any way he could from those who would try to obtain and/or destroy him.", "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16/standard_incredible.jpg", false),
    MarvelHero(2, "IronMan", "", "", false),
    MarvelHero(3, "Spider-Man", "", "", true),
    MarvelHero(4, "Captain America", "", "", false),
    MarvelHero(5, "Doctor Strange", "", "", false),
    MarvelHero(6, "Daredevil", "", "", true)
)
