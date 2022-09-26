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
    private val _heroesUiState: MutableStateFlow<MarvelHeroesUiState> = MutableStateFlow(MarvelHeroesUiState.Loading)
    private val _squadUiState: MutableStateFlow<MarvelHeroesSquadUiState> = MutableStateFlow(MarvelHeroesSquadUiState.Loading)

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
