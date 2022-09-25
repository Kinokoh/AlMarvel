package com.chaliez.alma.test.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chaliez.alma.test.R
import com.chaliez.alma.test.ui.theme.AlMarvelTheme
import com.chaliez.alma.test.ui.theme.Button

@Composable
internal fun BaseButton(@StringRes buttonText: Int, containerColor: Color = Color.Red, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = AlMarvelTheme.shapes.Button,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
            .border(2.dp, Color.Red, AlMarvelTheme.shapes.Button)
    ) {
        Box(modifier = Modifier.padding(6.dp)) {
            Text(stringResource(id = buttonText))
        }
    }
}


@Composable
fun RecruitButton(onClick: () -> Unit) {
    BaseButton(R.string.button_recruit) { onClick.invoke() }
}

@Composable
fun FireButton(onClick: () -> Unit) {
    BaseButton(R.string.button_fire, AlMarvelTheme.colors.onSurfaceVariant) { onClick.invoke() }
}

//Previews

@Preview(
    group = "Buttons"
)
@Composable
private fun RecruitButtonPreview() {
    AlMarvelTheme {
        RecruitButton {}
    }
}

@Preview(
    group = "Buttons"
)
@Composable
private fun FireButtonPreview() {
    AlMarvelTheme {
        FireButton {}
    }
}