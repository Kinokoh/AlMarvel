package com.chaliez.alma.test.ui.marvelhero

import com.chaliez.alma.test.data.repository.marvel.FakeMarvelHeroRepository
import com.chaliez.alma.test.data.repository.marvel.fakeMarvelHeroes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class MarvelHeroViewModelTest {

    private val repository = FakeMarvelHeroRepository()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_heroes_initiallyLoading() = runTest {
        val viewModel = MarvelHeroViewModel(repository)
        assertEquals(viewModel.heroesUiState.first(), MarvelHeroesUiState.Loading)
    }

    @Test
    fun uiState_squad_initiallyLoading() = runTest {
        val viewModel = MarvelHeroViewModel(repository)
        assertEquals(viewModel.squadUiState.first(), MarvelHeroesSquadUiState.Loading)
    }

//    @Test //TODO
//    fun uiState_squad_Success() = runBlocking {
//        val viewModel = MarvelHeroViewModel(repository)
//        viewModel.updateSquad()
//        assertEquals(
//            viewModel.squadUiState.first(),
//            MarvelHeroesSquadUiState.Success(fakeMarvelHeroes.filter { it.isPartOfSquad })
//        )
//    }
}
