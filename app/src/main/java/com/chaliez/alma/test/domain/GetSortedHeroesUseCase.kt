package com.chaliez.alma.test.domain

import com.chaliez.alma.test.data.model.MarvelHero
import com.chaliez.alma.test.data.repository.marvel.MarvelHeroRepository

import kotlinx.coroutines.flow.*

import javax.inject.Inject

class GetSortedHeroesUseCase @Inject constructor(
    private val marvelHeroRepository: MarvelHeroRepository
) {
    suspend operator fun invoke(): Flow<List<MarvelHero>> {
        return marvelHeroRepository.getAll().map { heroes ->
            val sortedHeroes = heroes.sortedBy { it.name }
            sortedHeroes
        }
    }
}