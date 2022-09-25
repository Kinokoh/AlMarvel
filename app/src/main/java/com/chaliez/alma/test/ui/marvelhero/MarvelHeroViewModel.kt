/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chaliez.alma.test.ui.marvelhero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaliez.alma.test.data.model.MarvelHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.chaliez.alma.test.data.repository.marvel.MarvelHeroRepository
import com.chaliez.alma.test.domain.GetSortedHeroesUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MarvelHeroViewModel @Inject constructor(
    private val marvelHeroRepository: MarvelHeroRepository,
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _heroesUiState = MutableStateFlow(MarvelHeroesUiState.Success(emptyList()))
    private val _squadUiState = MutableStateFlow(MarvelHeroesSquadUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val heroesUiState: StateFlow<MarvelHeroesUiState> = _heroesUiState
    val squadUiState: StateFlow<MarvelHeroesSquadUiState> = _squadUiState

    fun updateList() {
        viewModelScope.launch {
            GetSortedHeroesUseCase(marvelHeroRepository).invoke().collect { heroes ->
                _heroesUiState.value = MarvelHeroesUiState.Success(heroes)
            }
        }
    }

    fun updateSquad() {
        viewModelScope.launch {
            marvelHeroRepository.getSquad()
                .collect { heroes ->
                    _squadUiState.value = MarvelHeroesSquadUiState.Success(heroes)
                }
        }
    }

}

sealed interface MarvelHeroesUiState {
    object Loading : MarvelHeroesUiState
    data class Error(val throwable: Throwable) : MarvelHeroesUiState
    data class Success(val data: List<MarvelHero>) : MarvelHeroesUiState
}

sealed interface MarvelHeroesSquadUiState {
    object Loading : MarvelHeroesSquadUiState
    data class Error(val throwable: Throwable) : MarvelHeroesSquadUiState
    data class Success(val data: List<MarvelHero>) : MarvelHeroesSquadUiState
}
