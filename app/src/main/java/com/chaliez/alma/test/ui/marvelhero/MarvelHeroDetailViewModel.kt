package com.chaliez.alma.test.ui.marvelhero

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaliez.alma.test.data.model.MarvelHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.chaliez.alma.test.data.repository.marvel.MarvelHeroRepository
import com.chaliez.alma.test.navigation.AppDestination
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MarvelHeroDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val marvelHeroRepository: MarvelHeroRepository
) : ViewModel() {

    private val _marvelHeroId: Int = checkNotNull(savedStateHandle[AppDestination.heroIdArg])

    // Backing property to avoid state updates from other classes
    private var _uiState = MutableStateFlow<MarvelHeroUiState>(MarvelHeroUiState.Loading)

    // The UI collects from this StateFlow to get its state updates
    var uiState: StateFlow<MarvelHeroUiState> = _uiState

    init {
        viewModelScope.launch {
            displayHero()
        }
    }

    private suspend fun displayHero() {
        marvelHeroRepository.get(_marvelHeroId)
            .collect { hero ->
                _uiState.value = MarvelHeroUiState.Success(hero)
            }
    }

    fun onRecruit() {
        viewModelScope.launch {
            marvelHeroRepository.updateSquad(_marvelHeroId, true)
            displayHero()
        }
    }

    fun onFire() {
        viewModelScope.launch {
            marvelHeroRepository.updateSquad(_marvelHeroId, false)
            displayHero()
        }
    }
}

sealed interface MarvelHeroUiState {
    object Loading : MarvelHeroUiState
    data class Error(val throwable: Throwable) : MarvelHeroUiState
    data class Success(val data: MarvelHero) : MarvelHeroUiState
}
