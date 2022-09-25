package com.chaliez.alma.test.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.chaliez.alma.test.data.local.database.MarvelHeroDao
import com.chaliez.alma.test.data.local.database.MarvelHeroRow
import com.chaliez.alma.test.data.repository.marvel.DatabaseMarvelHeroRepository
import com.chaliez.alma.test.data.repository.marvel.fakeMarvelHeroes
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before

/**
 * Unit tests for [DatabaseMarvelHeroRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DatabaseMarvelHeroRepositoryTest {

    private val fakeDao = FakeMarvelHeroDao()

    @Test
    fun databaseRepository_addItems_itemsAdded() = runTest {
        val repository = DatabaseMarvelHeroRepository(FakeMarvelHeroDao()) //emptyDao

        assertEquals(repository.getAll().toList().first().size, 0)

        repository.addAll(fakeMarvelHeroes)

        assertEquals(repository.getAll().toList().first().size, fakeMarvelHeroes.size)
    }

    @Test
    fun databaseRepository_squad_squadUpdated() = runTest {
        val repository = DatabaseMarvelHeroRepository(fakeDao)

        assertEquals(1, 0)

    }

    @Before
    private fun fillDao() = runBlocking {
        fakeDao.deleteAll()
        fakeDao.insertMarvelHero(
            MarvelHeroRow(
                1,
                "A-Bomb (HAS)",
                "Rick Jones has been Hulk's best and only friend for years, ever since he accidentally parked his vehicle near where a gamma bomb was about to go off, Bruce was able to save Rick by hiding him in the trenches but this resulted in Bruce turning into the Hulk. Over the years Rick would help the Hulk in any way he could from those who would try to obtain and/or destroy him.",
                "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16/standard_incredible.jpg",
                false
            )
        )
        fakeDao.insertMarvelHero(
            MarvelHeroRow(
                2, "IronMan", "", "", false
            )
        )
        fakeDao.insertMarvelHero(
            MarvelHeroRow(
                3, "Spider-Man", "", "", false
            )
        )
    }

}

private class FakeMarvelHeroDao : MarvelHeroDao {

    private val _data = mutableListOf<MarvelHeroRow>()

    override fun getMarvelHeroes(): Flow<List<MarvelHeroRow>> = flow {
        emit(_data)
    }

    override fun getMarvelHero(uid: Int): Flow<MarvelHeroRow> = flow {
        emit(_data.first { it.uid == uid })
    }

    override fun getMarvelHeroesSquad(): Flow<List<MarvelHeroRow>> = flow {
        emit(_data.filter { it.isPartOfSquad })
    }

    override suspend fun insertMarvelHero(item: MarvelHeroRow) {
        _data.add(0, item)
    }

    override suspend fun updateIsPartOfSquad(uid: Int, isPartOfSquad: Boolean) {
    }

    override suspend fun delete(item: MarvelHeroRow) {
        _data.remove(item)
    }

    override suspend fun deleteAll() {
        _data.clear()
    }
}
