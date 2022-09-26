package com.chaliez.alma.test.ui.marvelhero

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chaliez.alma.test.data.repository.marvel.fakeMarvelHeroes
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for [MarvelHeroScreen].
 */
@RunWith(AndroidJUnit4::class)
class MarvelHeroScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            MarvelHeroScreen(MarvelHeroesUiState.Success(fakeMarvelHeroes), MarvelHeroesSquadUiState.Success(emptyList())) { }
        }
    }

    @Test
    fun firstItem_exists() {
        composeTestRule.onNodeWithText(fakeMarvelHeroes.first().name).assertExists().performClick()
    }
}

