package com.chaliez.alma.test.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chaliez.alma.test.navigation.AppDestination.heroIdArg
import com.chaliez.alma.test.ui.marvelhero.MarvelHeroDetailScreen
import com.chaliez.alma.test.ui.marvelhero.MarvelHeroScreen

object AppDestination {
    const val heroIdArg = "heroId"
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MarvelHeroScreen(modifier = Modifier.padding(top = systemBarsPadding.calculateTopPadding())) { heroId ->
                navController.navigate(
                    "detail/$heroId"
                )
            }
        }
        composable(
            "detail/{$heroIdArg}",
            arguments = listOf(navArgument(heroIdArg) { type = NavType.IntType })
        ) {
            MarvelHeroDetailScreen({ navController.popBackStack() }, systemBarsPadding.calculateTopPadding())
        }
    }
}
