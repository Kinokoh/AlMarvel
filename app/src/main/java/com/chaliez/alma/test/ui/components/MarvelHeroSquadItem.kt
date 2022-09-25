package com.chaliez.alma.test.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaliez.alma.test.ui.theme.AlMarvelTheme
import com.chaliez.alma.test.data.repository.marvel.fakeMarvelHeroes
import com.chaliez.alma.test.data.model.MarvelHero
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
internal fun MarvelHeroSquadItem(
    item: MarvelHero,
    onClickAction: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(84.dp)
            .padding(6.dp)
            .clickable { onClickAction?.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            imageModel = item.thumbnail,
            imageOptions = ImageOptions(
                alignment = Alignment.Center,
                contentDescription = "Hero thumbnail",
                contentScale = ContentScale.Crop // crop the image
            ),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(4.dp)
                .clip(CircleShape) // clip to the circle shape
                .background(Color.Black)
        )

        Text(
            text = item.name,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }

}


// Previews
@Preview(showBackground = true)
@Composable
private fun MarvelHeroItemPreview() {
    AlMarvelTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            MarvelHeroSquadItem(fakeMarvelHeroes[0])
        }
    }
}
