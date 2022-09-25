@file:OptIn(ExperimentalFoundationApi::class)

package com.chaliez.alma.test.ui.marvelhero

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import com.chaliez.alma.test.ui.theme.AlMarvelTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chaliez.alma.test.R
import com.chaliez.alma.test.data.repository.marvel.fakeMarvelHeroes
import com.chaliez.alma.test.data.model.MarvelHero
import com.chaliez.alma.test.ui.components.MarvelHeroItemRow
import com.chaliez.alma.test.ui.components.MarvelHeroSquadItem
import com.chaliez.alma.test.ui.components.ShowProgressBar

@Composable
fun MarvelHeroScreen(
    modifier: Modifier = Modifier,
    viewModel: MarvelHeroViewModel = hiltViewModel(),
    onSelectHero: (id: Int) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val heroes by produceState<MarvelHeroesUiState>(
        initialValue = MarvelHeroesUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.updateList()
            viewModel.heroesUiState.collect { value = it }
        }
    }
    val squad by produceState<MarvelHeroesSquadUiState>(
        initialValue = MarvelHeroesSquadUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.updateSquad()
            viewModel.squadUiState.collect { value = it }
        }
    }

    MarvelHeroScreen(heroes, squad, modifier) { onSelectHero(it) }
}

@Composable
fun MarvelHeroScreen(
    heroesState: MarvelHeroesUiState,
    squadState: MarvelHeroesSquadUiState,
    modifier: Modifier = Modifier,
    onSelectHero: (id: Int) -> Unit,
) {
    Column(modifier) {
        Header()

        when (heroesState) {
            is MarvelHeroesUiState.Success -> {

                LazyColumn {

                    if (squadState is MarvelHeroesSquadUiState.Success && squadState.data.isEmpty().not()) {
                        stickyHeader {
                            Squad(squadState.data, onSelectHero)
                        }
                    }

                    items(heroesState.data, key = { it.id }) { hero -> MarvelHeroItemRow(hero) { onSelectHero(hero.id) } }

                }
            }
            is MarvelHeroesUiState.Loading -> ShowProgressBar()
            else -> {} //TODO error
        }
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_marvel),
            contentDescription = "logo Marvel",
            modifier = Modifier.padding(4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AlMarvelTheme.colors.surfaceVariant)
        )
    }
}

@Composable
private fun Squad(squadMembers: List<MarvelHero>, onSelectSquadHero: (id: Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AlMarvelTheme.colors.background)
    ) {
        Text(
            text = stringResource(id = R.string.squad_title),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            ),
            modifier = Modifier
                .padding(12.dp)
        )

        LazyRow {
            items(squadMembers, key = { it.id }) { hero -> MarvelHeroSquadItem(hero) { onSelectSquadHero(hero.id) } }
        }
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AlMarvelTheme {
        Surface {
            MarvelHeroScreen(MarvelHeroesUiState.Success(fakeMarvelHeroes), MarvelHeroesSquadUiState.Success(fakeMarvelHeroes), onSelectHero = {})
        }
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    AlMarvelTheme {
        Surface {
            MarvelHeroScreen(MarvelHeroesUiState.Success(fakeMarvelHeroes), MarvelHeroesSquadUiState.Success(fakeMarvelHeroes), onSelectHero = {})
        }
    }
}
