package com.chaliez.alma.test.ui.marvelhero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import com.chaliez.alma.test.R
import com.chaliez.alma.test.ui.theme.AlMarvelTheme
import com.chaliez.alma.test.data.repository.marvel.fakeMarvelHeroes
import com.chaliez.alma.test.data.model.MarvelHero
import com.chaliez.alma.test.ui.components.FireButton
import com.chaliez.alma.test.ui.components.RecruitButton
import com.chaliez.alma.test.ui.components.ShowProgressBar
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class) //Scaffold
@Composable
fun MarvelHeroDetailScreen(
    onBackClick: () -> Unit,
    statusBarSize: Dp = 0.dp,
    viewModel: MarvelHeroDetailViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val heroState by produceState<MarvelHeroUiState>(
        initialValue = MarvelHeroUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CustomAppBar(Modifier.padding(top = statusBarSize)) { onBackClick.invoke() } //TODO find a better way (TopAppBar cannot have Transparent background in Material3 for now)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = AlMarvelTheme.colors.background
    ) {
        when (heroState) {
            is MarvelHeroUiState.Success ->
                MarvelHeroDetailScreen(
                    hero = (heroState as MarvelHeroUiState.Success).data,
                    MarvelHeroCallbacks(
                        viewModel::onRecruit,
                        onFireClick = {
                            scope.launch {
                                if (snackbarHostState.showSnackbar(context.getString(R.string.confirm_fire_from_squad_message), context.getString(R.string.confirm_fire_from_squad_action_label)) == SnackbarResult.ActionPerformed) //TODO ActionDialog ?
                                    viewModel.onFire()
                            }
                        }
                    )
                )
            is MarvelHeroUiState.Loading -> ShowProgressBar()
            else -> { //TODO }
            }
        }
    }
}

data class MarvelHeroCallbacks(
    val onRecruitClick: () -> Unit,
    val onFireClick: () -> Unit
)

@Composable
internal fun CustomAppBar(modifier: Modifier = Modifier, onActionClick: () -> Unit = {}) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(12.dp)
        ) {
            IconButton({ onActionClick.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back navigation",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
internal fun MarvelHeroDetailScreen(
    hero: MarvelHero,
    callbacks: MarvelHeroCallbacks = MarvelHeroCallbacks({}, {}),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AlMarvelTheme.colors.background)
    ) {

        Box {
            CoilImage(
                imageModel = hero.thumbnail,
                imageOptions = ImageOptions(
                    contentDescription = "Hero thumbnail",
                    contentScale = ContentScale.Crop // crop the image
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.Black)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = hero.name,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            ),
            modifier = Modifier
                .padding(12.dp)
        )

        if (hero.isPartOfSquad) {
            FireButton { callbacks.onFireClick.invoke() }
        } else {
            RecruitButton { callbacks.onRecruitClick.invoke() }
        }

        Text(
            text = hero.description,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = Color.White
            ),
            modifier = Modifier
                .padding(12.dp)
        )
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AlMarvelTheme {
        Surface {
            MarvelHeroDetailScreen(fakeMarvelHeroes[0])
        }
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    AlMarvelTheme {
        Surface {
            MarvelHeroDetailScreen(fakeMarvelHeroes[0])
        }
    }
}
