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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaliez.alma.test.R
import com.chaliez.alma.test.ui.theme.AlMarvelTheme
import com.chaliez.alma.test.data.repository.marvel.fakeMarvelHeroes
import com.chaliez.alma.test.data.model.MarvelHero
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
internal fun MarvelHeroItemRow(
    item: MarvelHero,
    onClickAction: (() -> Unit)? = null
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClickAction?.invoke() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                imageModel = item.thumbnail,
                imageOptions = ImageOptions(
                    alignment = Alignment.Center,
                    contentDescription = "Hero thumbnail",
                    contentScale = ContentScale.Crop // crop the image
                ),
                modifier = Modifier
                    .size(64.dp)
                    .padding(12.dp)
                    .clip(CircleShape) // clip to the circle shape
                    .background(Color.Black)
            )

            Text(
                text = item.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            )

            Spacer(Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.ic_baseline_chevron_right_24),
                tint = Color.White.copy(alpha = .6f),
                contentDescription = "right angle bracket",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}


// Previews
@Preview(showBackground = true, widthDp = 480)
@Composable
private fun MarvelHeroItemPreview() {
    AlMarvelTheme {
        MarvelHeroItemRow(fakeMarvelHeroes[0])
    }
}
