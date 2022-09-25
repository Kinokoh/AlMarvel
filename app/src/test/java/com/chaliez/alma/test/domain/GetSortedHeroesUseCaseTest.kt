package com.chaliez.alma.test.domain

import com.chaliez.alma.test.data.repository.marvel.FakeMarvelHeroRepository
import com.chaliez.alma.test.data.repository.marvel.fakeMarvelHeroes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for [GetSortedHeroesUseCase].
 */
class GetSortedHeroesUseCaseTest {

    private val repository = FakeMarvelHeroRepository()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun useCase_getSortedMarvelHeroes_sort() = runTest {
        val fakeSorted = fakeMarvelHeroes.sortedBy { it.name }

        val resultToList = GetSortedHeroesUseCase(repository).invoke().toList().first()

        assertEquals(resultToList, fakeSorted)
    }

}
